package com.saber.pub_sub.flow_api;

import java.util.concurrent.SubmissionPublisher;

// Since SubmissionPublisher has default buffer of 256, values were published till 256 values.
// After that values started getting dropped. Towards the end of the output,
// you can see that subscriber only received 256 values & rest of the values were not received because they got dropped.
public class FlowMain2 {
    public static void main(String[] args) throws InterruptedException {
        // Create publisher with defined buffer size
        SubmissionPublisher<Integer> publisherBkpE = new SubmissionPublisher<>();

        // Create Subscriber
        MySubscriber subscriberBkp = new MySubscriber();

        // Subscriber subscribing to publisher
        publisherBkpE.subscribe(subscriberBkp);

        // Publish 500 numbers
        for (int i = 0; i < 500; i++) {
            System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
            publisherBkpE.offer(i, (s, a) -> {
                s.onError(new Exception("Can't handle backpressure any more. Dropping value " + a));
                return true;
            });
        }

        // Close publisher once publishing done
        publisherBkpE.close();

        // Since subscriber run on different thread than main thread, keep
        // main thread active for 100 seconds.
        Thread.sleep(100000);
    }
}
