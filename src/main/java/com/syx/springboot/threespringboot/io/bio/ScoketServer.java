package com.syx.springboot.threespringboot.io.bio;

import java.io.IOError;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
*
 * @Author Song YuXin
 * @Date 19:09 2021/3/8
 * @Description //TODO
 **/
public class ScoketServer {

    public static void  main(String[] args) throws IOException{
        ServerSocket serverScoket = new ServerSocket(9000);
        while(true){
            System.out.println("等待连接......");
            //阻塞方法
            Socket clinetSocket = serverScoket.accept();
            System.out.println("有客户端连接了.........");
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        //处理方法
                        handler(clinetSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }


    /*
    *
     * @Author Song YuXin
     * @Date 19:09 2021/3/8
     * @Description //TODO
     **/
    private static void handler(Socket clinetSocket) throws IOException{

        byte[] bytes = new byte[1024];
        System.out.println("准备read....");
        //接收客户端的数据，阻塞方法，没有数据可以读的时候，发生堵塞
        int read = clinetSocket.getInputStream().read(bytes);
        System.out.println("read完毕....");
        if(read != -1){
            System.out.println("接收到的客户端数据："+ new String(bytes,0,read));
        }
    }
}
