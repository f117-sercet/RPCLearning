package com.rpclearning.proxy;

import com.rpclearning.config.RpcServiceConfig;
import com.rpclearning.remoting.transport.RpcRequestTransport;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/3/7 9:17
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {


    private static final String INTERFACE_NAME = "interfaceName";

    private final RpcRequestTransport rpcRequestTransport;
    private final RpcServiceConfig rpcServiceConfig;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return null;
    }
}
