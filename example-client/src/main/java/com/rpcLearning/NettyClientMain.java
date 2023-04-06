package com.rpcLearning;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/4/6 8:55
 */

import com.rpcLearning.annotationce.RpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan(basePackage = {"com.rpcLearning"})
public class NettyClientMain {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
        HelloController helloController = (HelloController) applicationContext.getBean("helloController");
        helloController.test();
    }
}
