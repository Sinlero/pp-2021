package ru.bgpu.ppqueue;

public class Task {

    public static final String COMMAND_LOG = "log";
    public static final String COMMAND_SUM = "sum";
    public static final String COMMAND_EXIT = "exit";

    private String command;
    private Object[] params;

    public Task(String command, Object[] params) {
        this.command = command;
        this.params = params;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
