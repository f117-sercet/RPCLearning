package com.rpcLearning.remoting.handler;

import com.rpcLearning.exception.RpcException;
import com.rpcLearning.factory.SingletonFactory;
import com.rpcLearning.provider.ServiceProvider;
import com.rpcLearning.provider.impl.ZkServiceProviderImpl;
import com.rpcLearning.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/3/20 11:27
 */
@Slf4j
public class RpcRequestHandler {
    private final ServiceProvider serviceProvider;

    public RpcRequestHandler() {
        serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    public Object handle(RpcRequest rpcRequest){

        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    /**
     *
     * @param rpcRequest
     * @param service
     * @return
     */
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {

        Object result;

        try {

            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("service:[{}] successful invoke method:[{}]", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());

        } catch (Exception e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;

    }
    }
