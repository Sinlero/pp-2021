package ru.bgpu.ppint;

import java.util.*;

public class Main {

    private static Map<Double, List<IntegralThread>> results = new TreeMap<>();

    public static void main(String[] args) {
        // One thread
//        long time = System.currentTimeMillis();
//        Double result = new Integral(
//                0D,Math.PI,
//                Math.pow(10,-8),
//                Math::sin
//        ).calc();
//        System.out.printf("Потоки [%d] : %.4f - %.3f%n",1, result, (System.currentTimeMillis()-time)/1000D);

        // 1..10 threads
        for (int i = 1; i <= 10 ; i++) {
            createIntegralThreads(i, 0D, Math.PI, Math.pow(10,-8), Math::sin);
        }
        System.out.println("Results:");
        for (Map.Entry<Double, List<IntegralThread>> entry : results.entrySet()) {
            int threadCount = entry.getValue().size();
            Double res = entry.getValue().stream().mapToDouble(IntegralThread::getResult).sum();
            Double timeExec = entry.getKey();
            System.out.printf("Потоки [%2d] : %.4f - %.3f%n", threadCount, res,timeExec);
        }
    }

    public static void createIntegralThreads(Integer n, Double a, Double b, Double width, Function fun) {
        long time = System.currentTimeMillis();
        List<IntegralThread> list = new ArrayList<>();
        Double interval = b / n;
        b = interval;
        for (int i = 0; i < n; i++) {
            list.add(new IntegralThread(new Integral(a, b, width, fun)));
            list.get(i).getThread().start();
            a = b;
            b += interval;
        }
        for (IntegralThread integralThread : list) {
            try {
                integralThread.getThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        results.put((System.currentTimeMillis()-time)/1000D, list);
        System.out.println("Working " + n + " threads complete");
    }
}
