package com.rpcLearning.loadbalance.loadBalancer;

import com.rpcLearning.loadbalance.AbstractLoadBalance;
import com.rpcLearning.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/3/31 9:40
 */
public class RandomLoadBalance  extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
