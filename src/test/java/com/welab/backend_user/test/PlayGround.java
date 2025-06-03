package com.welab.backend_user.test;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PlayGround {
    @Test
    public void test() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        // for each
        numbers.stream().forEach(number -> System.out.println(number));

        // map
        List<Integer> numbers2 = numbers.stream()
                .map(number -> number * 2)
                .toList();

        // filter
        List<Integer> evenNumbers = numbers.stream()
                .filter(number -> number % 2 == 0)
                .toList();

        // reduce
        Optional<Integer> sum = numbers.stream().reduce((x, y) -> x + y);
    }
}
