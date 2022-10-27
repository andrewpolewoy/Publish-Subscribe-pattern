package com.saber.pub_sub.project_reactor;

import reactor.core.publisher.Flux;

// Use the limitRange() operator from Project Reactor.
// It allows setting the number of items to prefetch at once.
// The emitter splits the events into chunks avoiding consuming more than the limit on each request:
public class ReactorMain3 {
    public static void main(String[] args) {
        Flux<Integer> limit = Flux.range(1, 25);

        limit.limitRate(10);
        limit.subscribe(
                System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("Finished!!"),
                subscription -> subscription.request(25)
        );

    }
}
