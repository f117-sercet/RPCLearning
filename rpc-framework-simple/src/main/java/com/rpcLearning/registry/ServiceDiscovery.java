package com.rpcLearning.registry;

import com.rpcLearning.extension.SPI;
import com.rpcLearning.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * 服务发现
 *
 * @author: 段世超
 * @aate: Created in 2023/3/7 9:45
 */
@SPI
public interface ServiceDiscovery {

    /**
     * lookup service by rpcServiceName
     *
     * @param rpcRequest rpc service pojo
     * @return service address
     */

    InetSocketAddress lookupService(RpcRequest rpcRequest);
}
