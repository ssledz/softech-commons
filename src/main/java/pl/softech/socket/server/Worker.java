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

import java.nio.charset.Charset;
import java.util.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class Worker implements Runnable {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private static int count = 0;
    private int num;
    private final WorkerManager manager;
    private final IRequestProcessor requestProcessor;

    public Worker(WorkerManager manager, IRequestProcessor requestProcessor) {
        this.manager = manager;
        this.requestProcessor = requestProcessor;
        num = count++;
    }

    public String getName() {
        return "Worker[" + num + "]";
    }

    @Override
    public void run() {
        Queue<Request> queue = manager.getRequestQueue();
        Request request;
        while (true) {

            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        logger.error("", e);
                    }
                }
                request = (Request) queue.poll();
            }

            IResponse response = requestProcessor.processRequest(request);
            logger.debug(Worker.class.getName(), "run", "Response:\n" + new String(response.getRawData()), Charset.defaultCharset());
            manager.sendData(request.getSocket(), response);
        }

    }
}
