package net.yiliufeng.windows_control.dao;


import java.io.Serializable;

public class Command implements Serializable {
    private String type;
    private String command;
    private String args;

    public static String PREDEFINED = "predefined";
    public static String KEYBOARD = "keyboard";
    public static String MOUSE = "mouse";
    public static String SYSTEM = "system";
    public static String INPUT_MSG = "input_msg";

    @Override
    public String toString() {
        return "Command{" +
                "type='" + type + '\'' +
                ", content='" + command + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }


    public Command(String type, String command, String args) {
        this.type = type;
        this.command = command;
        this.args = args;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
}