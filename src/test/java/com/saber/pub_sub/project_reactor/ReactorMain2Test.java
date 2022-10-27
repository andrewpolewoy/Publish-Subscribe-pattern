package com.saber.pub_sub.project_reactor;

import org.junit.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ReactorMain2Test {

    @Test
    public void whenCancel_thenSubscriptionFinished() {
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

        //Test the code
        StepVerifier.create(range)
                .expectNext(1, 2, 3, 4, 5)
                .thenCancel()
                .verify();
    }
}