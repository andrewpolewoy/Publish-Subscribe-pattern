package com.saber.pub_sub.flow_api;

import java.util.concurrent.Flow;

public class MySubscriber implements Flow.Subscriber<Integer> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("Subscribed");
        // Store subscription
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        // Process received value.
        System.out.println(Thread.currentThread().getName() + " | Received = " + item);
        // 100 mills delay to simulate slow subscriber
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Processing of item is done so request next value.
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(Thread.currentThread().getName() + " | ERROR = "
                + throwable.getClass().getSimpleName() + " | " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Completed");
    }
}
