package com.company;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Tester {
    static void start(Class A) throws RuntimeException {
        var methods = new ArrayList<>(Arrays.asList(A.getMethods()));

        var methodBeforeSuite = methods
                .stream()
                .filter(x -> x.getDeclaredAnnotation(BeforeSuit.class) != null)
                .collect(Collectors.toList());
        if (methodBeforeSuite.size() > 1)
            throw new RuntimeException();

        var methodAfterSuite = methods
                .stream()
                .filter(x -> x.getDeclaredAnnotation(AfterSuite.class) != null)
                .collect(Collectors.toList());
        if (methodAfterSuite.size() > 1)
            throw new RuntimeException();

        methodBeforeSuite.forEach(x -> {
            try {
                x.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        var methodTests = methods
                .stream()
                .filter(x -> x.getDeclaredAnnotation(Test.class) != null)
                .sorted((x, y) ->
                        y.getDeclaredAnnotation(Test.class).value() - x.getDeclaredAnnotation(Test.class).value()
                )
                .collect(Collectors.toList());

        methodTests.forEach(x -> {
            try {
                x.invoke(null, null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        methodAfterSuite.forEach(x -> {
            try {
                x.invoke(null, null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
}
