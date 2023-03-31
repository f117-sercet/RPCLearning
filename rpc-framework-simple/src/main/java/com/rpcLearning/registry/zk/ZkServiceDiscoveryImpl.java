package com.rpcLearning.registry.zk;

import com.rpcLearning.enums.LoadBalanceEnum;
import com.rpcLearning.enums.RpcErrorMessageEnum;
import com.rpcLearning.exception.RpcException;
import com.rpcLearning.extension.ExtensionLoader;
import com.rpcLearning.loadbalance.LoadBalance;
import com.rpcLearning.registry.ServiceDiscovery;
import com.rpcLearning.registry.zk.util.CuratorUtils;
import com.rpcLearning.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

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
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (serviceUrlList.isEmpty()) {
            throw  new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        //load balance
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest);
        log.info("Successfully found the service address:[{}], targetServiceUrl",targetServiceUrl);

        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);

    }
}
