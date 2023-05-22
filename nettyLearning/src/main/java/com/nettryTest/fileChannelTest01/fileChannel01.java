package com.nettryTest.fileChannelTest01;

import lombok.SneakyThrows;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/5/22 15:00
 */

public class fileChannel01 {
    @SneakyThrows
    public static void main(String[] args) {
        String  str  = "hello world";

        // 创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\file01\\channel01");

        //获取filechannel
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建一个缓冲区 byteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

       byteBuffer.put(str.getBytes());

        // 对byte进行反转
        byteBuffer.flip();
        // 将数据写入到缓冲区
        fileChannel.write(byteBuffer);

        // 关闭流
        fileOutputStream.close();

    }
}
