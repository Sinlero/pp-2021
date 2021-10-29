package ru.bgpu.threadtest;

public class RunnableExample implements Runnable{
    @Override
    public void run() {
        System.out.println("Hello from Runnable!");
    }
}
