package net.yiliufeng.windows_control.MyUtils;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpUtil {
    private InetAddress serverAddress;
    private DatagramSocket datagramSocket;
    private String serverIp;
    private int serverPort;
    private String TAG="tag_ylf";

    private byte[] msgBytes;

    public UdpUtil(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void setServerIp(String serverIp){
        this.serverIp = serverIp;
    }

    //    private
    public void  sendUdpMessage(final String msg){
        try {
            Log.i(TAG, "sendUdpMessage: serverIP:"+ serverIp);
            datagramSocket = new DatagramSocket();
            serverAddress = InetAddress.getByName(serverIp);
        }catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        ThreadPoolManager.getInstance().startTaskThread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.i(TAG, "run: msg:"+msg);
                    msgBytes = msg.getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(msgBytes, msgBytes.length, serverAddress, serverPort);
                    datagramSocket.send(datagramPacket);
                    datagramSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "sendUDPMessage：thread error  send" );

                }
            }
        },"");
    }
}
