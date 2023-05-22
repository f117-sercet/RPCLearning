package com.nettryTest.nioTest;

import java.nio.IntBuffer;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/5/22 14:34
 */
public class nioTest {
    public static void main(String[] args) {

        // 简单说明
        // 创建一个buffer
        IntBuffer intBuffer = IntBuffer.allocate(10);
        intBuffer.put(1);
        intBuffer.put(2);
        intBuffer.put(3);
        intBuffer.put(4);
        intBuffer.put(5);
        intBuffer.put(6);
        intBuffer.put(7);
        intBuffer.put(8);
        intBuffer.put(0);

        // 读取数据
        // 将buffer读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
