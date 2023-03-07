package com.rpclearning.provider;

import com.rpclearning.config.RpcServiceConfig;

/**
 * store and provide service object.
 *
 * @author: 段世超
 * @aate: Created in 2023/3/7 8:55
 */
public interface ServiceProvider {

    /**
     *
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    Object getService(String rpcServiceName);


    void publishService(RpcServiceConfig rpcServiceConfig);


}
