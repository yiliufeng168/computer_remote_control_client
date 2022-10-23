package net.yiliufeng.windows_control.myBeams;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.yiliufeng.windows_control.dao.Command;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Table(name = "instruction")
public class Instruction implements Serializable {
    @Column(name = "id",isId = true,autoGen = true)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "command_list")
    private String command_list;

    @Column(name="computer_id")
    private Integer computer_id;

    public Instruction() {
    }

    public Instruction(Integer id, String name, String command_list, Integer computer_id) {
        this.id = id;
        this.name = name;
        this.command_list = command_list;
        this.computer_id = computer_id;
    }

    public Integer getComputer_id() {
        return computer_id;
    }

    public void setComputer_id(Integer computer_id) {
        this.computer_id = computer_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommandList(List<Command> commandList){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        this.command_list = gson.toJson(commandList);
    }
    public List<Command> getCommandList() {
        Command[] commands = new GsonBuilder().disableHtmlEscaping().create().fromJson(this.command_list, Command[].class);
        List<Command> commandList = Arrays.asList(commands);
        return commandList;
    }

    public String getCommand_list() {
        return command_list;
    }

    public void setCommand_list(String command_list) {
        this.command_list = command_list;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", command_list='" + command_list + '\'' +
                ", computer_id=" + computer_id +
                '}';
    }
}
