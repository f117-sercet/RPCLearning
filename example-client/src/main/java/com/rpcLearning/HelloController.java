package com.rpcLearning;

import com.rpcLearning.annotationce.RpcReference;
import org.checkerframework.checker.compilermsgs.qual.CompilerMessageKey;
import org.springframework.stereotype.Component;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/4/6 8:50
 */
@Component
public class HelloController {

    @RpcReference(version = "version1,0",group = "test")
    private HelloService helloService;

    public void test() throws InterruptedException {
        String hello = this.helloService.hello(new Hello("111", "222"));
        //如需使用 assert 断言，需要在 VM options 添加参数：-ea
        assert "Hello description is 222".equals(hello);
        Thread.sleep(12000);
        for (int i = 0; i < 10; i++) {
            System.out.println(helloService.hello(new Hello("111", "222")));
        }
    }
}
