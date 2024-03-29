package com.nettryTest.fileChannelTest01.nioFileChannel02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/5/23 17:15
 */
public class fileChannelTest03 {
    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");

        FileChannel fileChannel01 = fileOutputStream.getChannel();
        FileChannel fileChannel02 = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(521);

        while (true) {
            byteBuffer.clear();
            int read = fileChannel01.read(byteBuffer);
            if (read == -1) {

                // 表示读完
                break;
            }
            // 将buffer 中的数据写入到fileChannel02
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }

        // 关闭相应的流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
