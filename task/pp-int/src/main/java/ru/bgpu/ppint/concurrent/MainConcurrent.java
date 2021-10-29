package ru.bgpu.ppint.concurrent;

import ru.bgpu.ppint.Function;
import ru.bgpu.ppint.Integral;
import ru.bgpu.ppint.IntegralThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainConcurrent {

    private static Map<Double,  List<Future<Double>>> results = new TreeMap<>();

    public static void main(String[] args) {
        Double a = 0D, b = Math.PI, width = Math.pow(10,-8);
        Function fun = Math::sin;
        for (int i = 1; i <= 10; i++) {
            createIntegralThreads(i, a, b, width, fun);
        }
        System.out.println("\nResults:");
        for (Map.Entry<Double, List<Future<Double>>> entry : results.entrySet()) {
            int threadCount = entry.getValue().size();
            Double res = entry.getValue().stream().mapToDouble(future -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                return 0;
            }).sum();
            Double timeExec = entry.getKey();
            System.out.printf("Потоки [%2d] : %.4f - %.3f%n", threadCount, res,timeExec);
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
        results.put((System.currentTimeMillis()-time)/1000D, futures);
        System.out.println("Working " + threadsCount + " threads complete");
    }
}
