package com.nettryTest.BioDemo;

import io.netty.util.Recycler;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description： Bio连接测试
 *
 * @author: 段世超
 * @aate: Created in 2023/4/14 17:58
 */
public class BioTest {

    public static void main(String[] args) throws Exception {

        // 1.创建一个线程池
        // 2.如果有客户端连接，就创建一个线程，与之通讯

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        // 创建serverSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("当前线程："+Thread.currentThread().getId()+"\n"
        +"当前线程名称"+Thread.currentThread().getName());
        System.out.println("服务器启动了");

        while (true){
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户");
            System.out.println("当线程"+Thread.currentThread().getName());

            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        handler(socket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }


    private static void handler(Socket socket) throws IOException {

        try{
            byte[] bytes = new byte[1024];
            InputStream inputStream =   socket.getInputStream();

            // 循环读取客户端发送的数据
            while(true){
                System.out.println("线程消息:"+Thread.currentThread().getName());
                int read = inputStream.read(bytes);
                if(read != -1){
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{

            System.out.println("关闭和client的连接");

            try{
                socket.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
