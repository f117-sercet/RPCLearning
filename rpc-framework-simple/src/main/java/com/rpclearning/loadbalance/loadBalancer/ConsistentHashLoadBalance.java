package com.rpclearning.loadbalance.loadBalancer;

import com.rpclearning.loadbalance.AbstractLoadBalance;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/3/6 16:00
 */
@Slf4j
public class ConsistentHashLoadBalance extends AbstractLoadBalance {

    private final ConcurrentHashMap<String, ConsistentHashSelector> selectors = new ConcurrentHashMap<>();
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {

        int identityHashCode = System.identityHashCode(serviceAddresses);

        rpcRequest.getRpcService();

        return null;
    }
}
