package com.vladsch.flexmark.util.collection;

import java.util.function.BiFunction;

public class MinAggregator implements BiFunction<Integer, Integer, Integer> {
    final public static MinAggregator INSTANCE = new MinAggregator();

    private MinAggregator() {
    }

    @Override
    public Integer apply(final Integer aggregate, final Integer next) {
        return next == null || aggregate != null && aggregate <= next ? aggregate : next;
    }
}
