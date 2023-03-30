package com.rpcLearning.registry.zk;

import com.rpcLearning.enums.LoadBalanceEnum;
import com.rpcLearning.extension.ExtensionLoader;
import com.rpcLearning.loadbalance.LoadBalance;
import com.rpcLearning.registry.ServiceDiscovery;
import com.rpcLearning.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Description： 服务发现zookeeper
 *
 * @author: 段世超
 * @create: Created in 2023/3/30 10:49
 */
@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {

    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(LoadBalanceEnum.LOADBALANCE.getName());
    }

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {

        String rpcServiceName = rpcRequest.getRpcServiceName();

        return null;
    }
}
