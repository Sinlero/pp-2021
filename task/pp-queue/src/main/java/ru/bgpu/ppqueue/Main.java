package ru.bgpu.ppqueue;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        final PPQueue<Task> queue = new PPQueue<>(5);
        Consumer consumer = new Consumer(queue);
        new Thread(consumer).start();
        ThreadGroup group = new ThreadGroup("work");
        new Thread(group, () -> {
            for (int i = 0; i < 100; i++) {
                try {
                    queue.put(new Task(Task.COMMAND_LOG, new Object[]{System.currentTimeMillis()}));
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
        new Thread(group, () -> {
            Random random = new Random();
            for (int i = 0; i < 10000; i++) {
                try {
                    Task task;
                    if (random.nextInt(100) < 40) {
                        task = new Task(Task.COMMAND_SUM, new Object[]{"a"});
                    } else {
                        task = new Task(Task.COMMAND_SUM, new Object[]{'a'});
                    }
                    queue.put(task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
        try {
            while (group.activeCount() > 0) {
                Thread.sleep(100);
            }
            queue.put(new Task(Task.COMMAND_EXIT, null));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
