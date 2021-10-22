package ru.bgpu.ppint;

public class Main {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        Double result = new Integral(
                0D,Math.PI,
                Math.pow(10,-8),
                Math::sin
        ).calc();
        System.out.printf("Потоки [%d] : %.4f - %.3f%n",1, result, (System.currentTimeMillis()-time)/1000D);
    }
}
