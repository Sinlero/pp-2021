package ru.bgpu.ppint;

public class IntegralThread implements Runnable{

    private Thread thread;
    private Integral integral;
    private Double result;

    public IntegralThread(Integral integral) {
        thread = new Thread(this);
        this.integral = integral;
    }

    @Override
    public void run() {
        result = integral.calc();
    }

    public Double getResult() {
        return result;
    }

    public Thread getThread() {
        return thread;
    }
}
