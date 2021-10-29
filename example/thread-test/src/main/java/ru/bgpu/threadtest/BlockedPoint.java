package ru.bgpu.threadtest;

public class BlockedPoint<T> {

    private T message;

    public void push(T message) {
        synchronized (this) {
            try {
                while (this.message != null) {
                    this.wait();
                }
                this.message = message;
                this.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public T poll() {
        T result = null;
        synchronized (this){
            try {
                while (message == null) {
                    this.wait();
                }
                result = message;
                message = null;
                this.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
