package com.saber.pub_sub.project_reactor;

import reactor.core.publisher.Flux;

// Lets a there is a stream which emits x number of elements,
// but the subscriber only wants to receive n number of elements from the publisher.
public class ReactorMain1 {
    public static void main(String[] args) {
        Flux<Integer> range = Flux.range(1, 50);
        //gets only 10 elements
        range.subscribe(
                System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("==== Completed ===="),
                subscription -> subscription.request(10)
        );
    }
}
