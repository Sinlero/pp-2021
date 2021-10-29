package ru.bgpu.threadtest;

public class SyncExample {

    private final Object lock = new Object();


    public void method1() {
        synchronized (lock) {

        }
    }

    public void method2() {
        synchronized (this) {

        }
    }

    public synchronized void method3() {

    }

    public static void method4() {
        synchronized (SyncExample.class) {

        }
    }

    public static synchronized void method5() {

    }
}
