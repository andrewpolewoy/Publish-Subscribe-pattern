package com.saber.pub_sub.flow_api;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;

// Until buffer is full i.e. 16 values, the publisher was not blocked & publishing of values continued in non-blocking mode.
// But after buffer is full, publisher started getting blocked until previous values are consumed by subscriber.
public class FlowMain1 {
    public static void main(String[] args) throws InterruptedException {

        // Choose value in power of 2. Else SubmissionPublisher will round up to nearest
        // value of power of 2.
        final int BUFFER = 16;

        // Create publisher with defined buffer size
        SubmissionPublisher<Integer> publisherBkp = new SubmissionPublisher<>(ForkJoinPool.commonPool(), BUFFER);

        // Create Subscriber
        MySubscriber subscriberBkp = new MySubscriber();

        // Subscriber subscribing to publisher
        publisherBkp.subscribe(subscriberBkp);

        // Publish 100 numbers
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
            publisherBkp.submit(i);
        }

        // Close publisher once publishing done
        publisherBkp.close();

        // Since subscriber run on different thread than main thread, keep
        // main thread active for 100 seconds.
        Thread.sleep(100000);
    }
}
