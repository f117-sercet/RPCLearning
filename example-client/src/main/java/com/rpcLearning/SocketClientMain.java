package com.rpcLearning;

import com.rpcLearning.config.RpcServiceConfig;
import com.rpcLearning.proxy.RpcClientProxy;
import com.rpcLearning.remoting.transport.RpcRequestTransport;
import com.rpcLearning.remoting.transport.socket.SocketRpcClient;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/4/6 9:00
 */
public class SocketClientMain {
    public static void main(String[] args) {
        RpcRequestTransport rpcRequestTransport = new SocketRpcClient();
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcRequestTransport, rpcServiceConfig);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        String hello = helloService.hello(new Hello("111", "222"));
        System.out.println(hello);
    }
}