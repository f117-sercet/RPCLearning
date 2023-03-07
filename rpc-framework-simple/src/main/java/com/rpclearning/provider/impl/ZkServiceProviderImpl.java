package com.rpclearning.provider.impl;

import com.rpcLearning.enums.RpcErrorMessageEnum;
import com.rpcLearning.enums.ServiceRegistryEnum;
import com.rpcLearning.exception.RpcException;
import com.rpcLearning.extension.ExtensionLoader;
import com.rpclearning.config.RpcServiceConfig;
import com.rpclearning.provider.ServiceProvider;
import lombok.extern.slf4j.Slf4j;
import sun.util.locale.provider.LocaleServiceProviderPool;

import javax.imageio.spi.ServiceRegistry;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.spi.LocaleServiceProvider;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/3/7 8:58
 */
@Slf4j
public class ZkServiceProviderImpl implements ServiceProvider {

    /**
     * key: rpc service name(interface name + version + group)
     * value: service object
     */

    private final Map<String, Object> serviceMap;
    private final Set<String> registeredService;
    private final ServiceRegistry serviceRegistry;


    public ZkServiceProviderImpl() {
        serviceMap = new ConcurrentHashMap<>();
        registeredService = ConcurrentHashMap.newKeySet();
        serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension(ServiceRegistryEnum.ZK.getName());
    }

    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {

        String rpcServiceName = rpcServiceConfig.getServiceName();
        if (registeredService.contains(rpcServiceName)) {

            return;
        }

        registeredService.add(rpcServiceName);
        serviceMap.put(rpcServiceName, rpcServiceConfig.getService());
        log.info("Add service: {} and interfaces:{}" + rpcServiceName, rpcServiceConfig.getService().getClass().getInterfaces());

    }

    @Override
    public Object getService(String rpcServiceName) {

        Object service = serviceMap.get(rpcServiceName);
        if (null == service) {
            throw  new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {

        try {
            String host = Arrays.toString(InetAddress.getLocalHost().getAddress());
            this.addService(rpcServiceConfig);
            serviceRegistry.registerService(rpcServiceConfig.getRpcServiceName(), new InetSocketAddress(host, NettyRpcServer.PORT));
        }catch (UnknownHostException e){

            log.error("occur exception when getHostAddress", e);
        }
    }
}
