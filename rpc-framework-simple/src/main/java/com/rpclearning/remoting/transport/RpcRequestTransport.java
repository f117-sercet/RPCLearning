package com.rpclearning.remoting.transport;

import com.rpcLearning.extension.SPI;
import com.rpclearning.remoting.dto.RpcRequest;

/**
 * Description： 发送请求
 *
 * @author: 段世超
 * @aate: Created in 2023/3/7 9:23
 */
@SPI
public interface RpcRequestTransport {

    /**
     * send rpc request to server and get result
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
