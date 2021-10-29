package ru.bgpu.ppqueue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue<Task> queue;
    private final HashMap<String, Integer> map = new HashMap<>();

    public Consumer(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                work(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void work(Task task) {
        switch (task.getCommand()) {
            case Task.COMMAND_LOG -> System.out.println("LOG: " + Arrays.toString(task.getParams()));
            case Task.COMMAND_SUM -> Arrays.stream(task.getParams()).forEach(param -> {
                        Integer value = map.computeIfAbsent(param.getClass().getName(), k -> 0);
                        map.put(param.getClass().getName(), value+1);
                    }
            );
            case Task.COMMAND_EXIT -> {
                System.out.println("EXIT: Consumer");
                map.forEach((s, integer) -> System.out.println(s+" - "+integer));
                Thread.currentThread().interrupt();
            }
            default -> System.out.println(
                    "WARN: unknown command " + task.getCommand() + " : " + Arrays.toString(task.getParams())
            );
        }
    }
}
