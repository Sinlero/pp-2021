package ru.bgpu.ppint.concurrent;

import ru.bgpu.ppint.Function;
import ru.bgpu.ppint.Integral;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainConcurrent {
    public static void main(String[] args) {
        Double a = 0D, b = Math.PI, width = Math.pow(10,-8);
        Function fun = Math::sin;
        for (int i = 1; i <= 10; i++) {
            createIntegralThreads(i, a, b, width, fun);
        }
    }

    public static void createIntegralThreads(int threadsCount, Double a, Double b, Double width, Function fun) {
        long time = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        List<CallableIntegral> list = new ArrayList<>();
        Double interval = b / threadsCount;
        b = interval;
        for (int i = 0; i < threadsCount; i++) {
            list.add(new CallableIntegral(new Integral(a, b, width, fun)));
            a = b;
            b += interval;
        }
        List<Future<Double>> futures = null;
        try {
            futures = executorService.invokeAll(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        Double result = futures.stream().mapToDouble(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return 0;
        }).sum();
        System.out.printf("Потоки [%d] : %.4f - %.3f%n",threadsCount, result, (System.currentTimeMillis()-time)/1000D);
    }
}
