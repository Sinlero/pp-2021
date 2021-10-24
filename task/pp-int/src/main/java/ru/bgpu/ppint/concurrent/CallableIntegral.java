package ru.bgpu.ppint.concurrent;

import ru.bgpu.ppint.Integral;

import java.util.concurrent.Callable;

public class CallableIntegral implements Callable<Double> {

    private Integral integral;

    public CallableIntegral(Integral integral) {
        this.integral = integral;
    }

    @Override
    public Double call() throws Exception {
        return integral.calc();
    }
}
