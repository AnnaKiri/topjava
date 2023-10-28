package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TimingRule extends Stopwatch {
    private static final Logger log = LoggerFactory.getLogger(TimingRule.class);
    private static final int OFFSET_TIME_CHAR = 30;

    private static Map<String, Long> report = new LinkedHashMap<>();

    @Override
    protected void succeeded(long nanos, Description description) {
        long durationMillis = TimeUnit.NANOSECONDS.toMillis(nanos);
        report.put(description.getMethodName(), durationMillis);
        log.info("The test {} took {} ms", description.getMethodName(), durationMillis);
    }

    public static void printSummary() {
        StringBuilder stringBuilder = new StringBuilder("Test results summary:\n");
        report.forEach((key, value) -> stringBuilder.append(String.format("%-" + OFFSET_TIME_CHAR + "s %s ms\n", key, value)));
        log.info("\n{}\n", stringBuilder);
    }
}
