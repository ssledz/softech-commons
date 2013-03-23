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
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class WorkerManager {

    private final int poolSize;
    private final Server server;
    private final Queue<Request> requestQueue;
    private final Map<SocketChannel, Queue<IResponse>> pendingData;

    public WorkerManager(int poolSize, Server server) {
        this.server = server;
        this.poolSize = poolSize;
        requestQueue = new LinkedList<Request>();
        pendingData = new HashMap<SocketChannel, Queue<IResponse>>();
        spawnWorkers();
    }

    private void spawnWorkers() {
        for (int i = 0; i < poolSize; i++) {
            Worker w = new Worker(this, server.getRequestProcessor());
            Thread t = new Thread(w);
            t.setName(w.getName());
            t.setDaemon(true);
            t.start();
        }
    }

    public Queue<Request> getRequestQueue() {
        return requestQueue;
    }

    public Map<SocketChannel, Queue<IResponse>> getPendingData() {
        return pendingData;
    }

    public void processData(SocketChannel socket, byte[] data, int count) throws BadRequestException, IOException {
        byte[] dataCopy = new byte[count];
        System.arraycopy(data, 0, dataCopy, 0, count);
        synchronized (requestQueue) {
            requestQueue.add(new Request(socket, dataCopy));
            requestQueue.notify();
        }
    }

    public void sendData(SocketChannel socket, IResponse response) {

        synchronized (server.socketCommands) {

            server.socketCommands.add(new SocketCommand(socket, SocketCommand.CHANGEOPS, SelectionKey.OP_WRITE));

            synchronized (pendingData) {

                Queue<IResponse> queue = pendingData.get(socket);

                if (queue == null) {
                    queue = new LinkedList<IResponse>();
                    pendingData.put(socket, queue);
                }
                queue.add(response);
            }
        }
        server.wakeup();
    }
}
