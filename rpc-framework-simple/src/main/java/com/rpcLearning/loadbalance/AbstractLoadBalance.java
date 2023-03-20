package com.rpcLearning.loadbalance;

import com.rpcLearning.utils.CollectionUtil;

import java.util.List;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/3/6 15:47
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest) {
        if (CollectionUtil.isEmpty(serviceAddresses)) {
            return null;
        }
        if (serviceAddresses.size() == 1) {
            return serviceAddresses.get(0);
        }
        return doSelect(serviceAddresses,rpcRequest);
    }

    protected abstract String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest);

}
