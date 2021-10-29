package ru.bgpu.threadtest;

import java.util.Arrays;

public class Main {

    public static BlockedPoint<String> point = new BlockedPoint<>();

    public static void main(String[] args) {

        /// create thread
        new ThreadExample().start();
        new Thread(new RunnableExample()).start();
        new Thread(()-> System.out.println("Hello from main function!")).start();
        new Thread(Main::hello).start();

        // block point example
        new Thread(()->{
           String message;
           do {
               message = Main.point.poll();
               System.out.println(message);
           } while (!message.equals("exit"));
        }).start();
        ThreadGroup group = new ThreadGroup("push");
        new Thread(group, ()->{
            String messages[] = {"1","2","3","4"};
            Arrays.stream(messages).forEach(m -> Main.point.push(m));
            System.out.println("end 1");
        }).start();
        new Thread(group, ()->{
            String messages[] = {"#1","#2","#3","#4"};
            Arrays.stream(messages).parallel().forEach(m -> Main.point.push(m));
            System.out.println("end 2");
        }).start();
        while (group.activeCount() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        point.push("exit");
    }

    public static void hello()  {
        System.out.println("Hello from Main class method!");
    }
}
