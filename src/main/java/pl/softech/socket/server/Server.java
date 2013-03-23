/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.socket.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class Server implements Runnable {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private static final int DEFAULT_WORKER_POOL_SIZE = 100;
    private static final int DEFAULT_BACKLOG_SIZE = 100;
    private static final int DEFAULT_READ_BUFFER_SIZE = 8 * 1024;
    private final int port;
    private final int backlog;
    //socekt on which the incomming connection is accepted
    private ServerSocketChannel serverChannel;
    //selector to checout for incomming connections
    private Selector selector;
    final List<SocketCommand> socketCommands;
    private final WorkerManager workerManager;
    private final IRequestProcessor requestProcessor;
    private ByteBuffer readBuffer;
    
    private boolean running = true;

    public Server(int port, int backlog, int poolSize, IRequestProcessor requestProcessor) throws IOException {
        this.port = port;
        this.backlog = backlog;
        this.requestProcessor = requestProcessor;
        this.workerManager = new WorkerManager(poolSize, this);
        this.socketCommands = new LinkedList<SocketCommand>();
        init();
    }

    public Server(int port, IRequestProcessor requestProcessor) throws IOException {
        this(port, DEFAULT_BACKLOG_SIZE, DEFAULT_WORKER_POOL_SIZE, requestProcessor);
    }

    private void init() throws IOException {
        logger.info("Initialising");
        logger.info("Server listen port: " + port);
        logger.info("Server backlog size: " + backlog);

        serverChannel = ServerSocketChannel.open();
        //Non blocking channel
        serverChannel.configureBlocking(false);

        //Binging channel to the port
        InetSocketAddress isa = new InetSocketAddress(port);
        serverChannel.socket().bind(isa, backlog);

        //creating selector
        selector = SelectorProvider.provider().openSelector();
        //registering selector to accepting connections
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        readBuffer = ByteBuffer.allocate(DEFAULT_READ_BUFFER_SIZE);
    }

    private void processSocketCommands() {
        synchronized (socketCommands) {
            for (SocketCommand command : socketCommands) {
                switch (command.type) {
                    case SocketCommand.CHANGEOPS:
                        SelectionKey key = command.socket.keyFor(selector);
                        key.interestOps(command.ops);
                        break;
                }
            }
            socketCommands.clear();
        }
    }

    @Override
    public void run() {
        logger.info("Starting listening on port: " + port);
        while (running) {
            try {

                processSocketCommands();

                //waiting for channel ready to process
                selector.select();

                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    // sprawdzamy czy kanal jest gotowy do jego obslugi
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }

                }

            } catch (Exception e) {
                logger.error("", e);
            }
        }

    }

    public void shutdown() throws IOException {
        running = false;
        selector.close();
        serverChannel.close();
    }
    
    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        //registering socket to being ready to read
        socketChannel.register(this.selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException, BadRequestException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        readBuffer.clear();
        int count;
        try {
            count = socketChannel.read(readBuffer);
        } catch (Exception e) {
            logger.warn("Client has been dissconnected", e);
            //client could just was disconnected
            key.cancel();
            socketChannel.close();
            return;
        }

        if (count == -1) {
            //client close the connection
            logger.debug("Client closed connection");
            key.channel().close();
            key.cancel();
            return;
        }

        workerManager.processData(socketChannel, this.readBuffer.array(), count);
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        synchronized (workerManager.getPendingData()) {
            Queue<IResponse> queue = workerManager.getPendingData().get(socketChannel);
            IResponse response = null;
            while (!queue.isEmpty()) {
                response = queue.peek();
                ByteBuffer buf = (ByteBuffer) ByteBuffer.wrap(response.getRawData());
                socketChannel.write(buf);
                if (buf.remaining() > 0) {
                    break;
                }
                queue.poll();
            }

            if (queue.isEmpty()) {
                //To finish the connection
                if (response != null && response.closeConnection()) {
                    key.channel().close();
                    key.cancel();
                } else {
                    //If we don't want to finish
                    key.interestOps(SelectionKey.OP_READ);
                }
            }

        }
    }

    public void wakeup() {
        selector.wakeup();
    }

    public IRequestProcessor getRequestProcessor() {
        return requestProcessor;
    }
}
