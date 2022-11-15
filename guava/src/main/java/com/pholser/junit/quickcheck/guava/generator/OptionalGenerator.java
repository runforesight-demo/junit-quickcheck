/*
 The MIT License

 Copyright (c) 2010-2021 Paul R. Holser, Jr.

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.pholser.junit.quickcheck.guava.generator;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

import com.google.common.base.Optional;
import com.pholser.junit.quickcheck.generator.ComponentizedGenerator;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

/**
 * Produces values of type {@link Optional}.
 */
@SuppressWarnings("Guava")
public class OptionalGenerator extends ComponentizedGenerator<Optional> {
    public OptionalGenerator() {
        super(Optional.class);
    }

    @Override public Optional<?> generate(
        SourceOfRandomness random,
        GenerationStatus status) {

        double trial = random.nextDouble();
        return trial < 0.25
            ? Optional.absent()
            : Optional.fromNullable(
                componentGenerators().get(0).generate(random, status));
    }

    @Override public List<Optional> doShrink(
        SourceOfRandomness random,
        Optional larger) {

        if (!larger.isPresent())
            return new ArrayList<>();

        List<Optional> shrinks = new ArrayList<>();
        shrinks.add(Optional.absent());
        shrinks.addAll(
            componentGenerators().get(0)
                .shrink(random, larger.get())
                .stream()
                .map(Optional::fromNullable)
                .collect(toList()));
        return shrinks;
    }

    @Override public int numberOfNeededComponents() {
        return 1;
    }

    @Override public BigDecimal magnitude(Object value) {
        Optional<?> narrowed = narrow(value);

        return narrowed.toJavaUtil()
            .map(componentGenerators().get(0)::magnitude)
            .orElse(ZERO);
    }
    private void sum() {
        int a[] = {2, 6, 1, 4};
        int b[] = {2, 1, 4, 4};

        int result[] = new int[a.length];
        Arrays.setAll(result, i -> a[i] + b[i]);
    }
}
