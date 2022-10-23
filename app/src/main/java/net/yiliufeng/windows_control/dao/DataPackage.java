package net.yiliufeng.windows_control.dao;

import java.util.List;

public class DataPackage<T> {
    private String type;
    private List<T> content;
    private String check_sum;
    public static String CONTROL = "control";
    public final static String COMMAND_LIST = "command_list";

    public DataPackage() {
    }

    public DataPackage(String type, List<T> content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public String getCheck_sum() {
        return check_sum;
    }

    public void setCheck_sum(String check_sum) {
        this.check_sum = check_sum;
    }
}
