package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.HashMap;
import java.util.Map;

public class TimingRule implements TestRule {
    private static Map<String, Long> map = new HashMap<>();

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long startTime = System.currentTimeMillis();
                try {
                    base.evaluate();
                } finally {
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;
                    map.put(description.getMethodName(), duration);
                    System.out.println("The test '" + description.getMethodName() + "' was completed during " + duration + " milliseconds");
                }
            }
        };
    }

    public static void printSummary() {
        System.out.println("Test results summary:");
        map.forEach((key, value) -> System.out.println(key + " " + value));
    }
}
