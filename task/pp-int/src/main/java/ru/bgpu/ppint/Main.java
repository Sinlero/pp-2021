package ru.bgpu.ppint;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        Double result = new Integral(
                0D,Math.PI,
                Math.pow(10,-8),
                Math::sin
        ).calc();
        System.out.printf("Потоки [%d] : %.4f - %.3f%n",1, result, (System.currentTimeMillis()-time)/1000D);
        for (int i = 2; i <= 10 ; i++) {
            createIntegralThreads(i, 0D, Math.PI, Math.pow(10,-8), Math::sin);
        }
    }

    public static void createIntegralThreads(Integer n, Double a, Double b, Double width, Function fun) {
        long time = System.currentTimeMillis();
        List<IntegralThread> list = new ArrayList<>();
        Double interval = b / n;
        b = interval;
        for (int i = 0; i < n; i++) {
            list.add(new IntegralThread(new Integral(a, b, width, fun)));
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
        System.out.printf("Потоки [%d] : %.4f - %.3f%n",n,
                list.stream()
                        .mapToDouble(IntegralThread::getResult)
                        .sum(),
                (System.currentTimeMillis()-time)/1000D);
    }
}
