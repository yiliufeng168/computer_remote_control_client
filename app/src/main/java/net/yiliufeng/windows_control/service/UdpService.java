package net.yiliufeng.windows_control.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.yiliufeng.windows_control.MyUtils.UdpUtil;
import net.yiliufeng.windows_control.dao.Command;
import net.yiliufeng.windows_control.dao.DataPackage;
import net.yiliufeng.windows_control.myBeams.Instruction;

import org.xutils.common.util.MD5;

import java.util.ArrayList;
import java.util.List;

public class UdpService {
    private String host;
    private Integer port;
    private String password;
    private UdpUtil udpUtil;

    public UdpService(String host, Integer port, String password) {
        this.host = host;
        this.port = port;
        this.udpUtil = new UdpUtil(host,port);
        this.password = password;
    }

    public void setHost(String host){
        udpUtil.setServerIp(host);
    }
    public void sendMessage(String msg){
//        String encryptMsg = RsaUtil.encryptByPublic(msg);
//        udpUtil.sendUdpMessage(encryptMsg);
        Log.i("TAG", "sendMessage: "+msg);
        udpUtil.sendUdpMessage(msg);
    }
    public void sendMessage(DataPackage dataPackage){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String msg = gson.toJson(dataPackage);
        Log.i("TAG",msg);
        udpUtil.sendUdpMessage(msg);
    }


    public void sendCommand(Command command) {

        ArrayList<Command> commands = new ArrayList<>();
        commands.add(command);
        sendCommands(commands);
    }
    public void sendCommands(List<Command> commands){
        DataPackage dataPackage = new DataPackage("command_list", commands);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String content = gson.toJson(dataPackage.getContent());
        long timeMillis = System.currentTimeMillis();
        String s1 = String.valueOf(timeMillis / 1000 / 120);
        Log.i("TAG",content+s1+password);
        String s = MD5.md5(content+s1+password);
        dataPackage.setCheck_sum(s);
        sendMessage(dataPackage);
    }

    public void sendInstruction(Instruction instruction) {
        sendCommands(instruction.getCommandList());
    }
}
