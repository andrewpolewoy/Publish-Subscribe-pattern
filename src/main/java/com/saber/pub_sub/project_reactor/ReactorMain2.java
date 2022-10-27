package com.saber.pub_sub.project_reactor;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

// Use BaseSubscriber::hookOnNext to subscribe to the producer.
// The producer emits the next value only when the subscriber sends request(1)
public class ReactorMain2 {
    public static void main(String[] args) {
        Flux<Integer> range = Flux.range(1, 100).log();
        range.doOnCancel(() -> {
            System.out.println("===== Cancel method invoked =======");
        }).doOnComplete(() -> {
            System.out.println("==== Completed ====");
        }).subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                try {
                    Thread.sleep(500);
                    request(1); //request next element
                    System.out.println(value);
                    if (value == 5) {
                        cancel();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
