package com.nettryTest.fileChannelTest01.nioFileChannel02;

import com.sun.org.apache.xpath.internal.operations.String;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/5/23 16:57
 */
public class fileChannel {
    public static void main(String[] args) throws Exception {

        // 创建文件的输入流
        File file = new File("d:\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        // 获取channel
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建缓冲区
        int length = (int) file.length();
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);

        //将通道的数据 读入到buffer
        int read = fileChannel.read(byteBuffer);

        // 将字节信息转成字符串
        System.out.println(new java.lang.String(byteBuffer.array()));
        fileInputStream.close();


    }
}
