/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.example.quickstart;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * This class demonstrates how to send messages to brokers using provided {@link DefaultMQProducer}.
 */
public class Producer222 {

    /**
     * The number of produced messages.
     */
    public static final int MESSAGE_COUNT = 20;
    public static final String PRODUCER_GROUP = "please_rename_unique_group_name";
    public static final String DEFAULT_NAMESRVADDR = "10.162.85.140:9876";
    public static final String TOPIC = "BCF-Test-Topic-1";

    public static void main(String[] args) throws MQClientException, InterruptedException {

        /*
         * Instantiate with a producer group name.
         */
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
        producer.start();

        send(producer, "TagA");
        send(producer, "TagB");

        producer.shutdown();
    }

    private static void send(DefaultMQProducer producer, String tag) throws InterruptedException {
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            try {
                Message msg = new Message(TOPIC /* Topic */,
                        tag /* Tag */,
                    (tag+" ： " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
//                msg.putUserProperty("age", String.valueOf(i));
                SendResult sendResult = producer.send(msg);

                System.out.printf("%s%n", tag+" ： age=" + i+","+sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
    }
}
