package com.rpclearning.remoting.transport.netty.client;

import com.rpclearning.remoting.dto.RpcResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description： TODO
 *unprocessed requests by the server.
 * @author: 段世超
 * @aate: Created in 2023/3/8 17:31
 */
public class UnprocessedRequests {

    private static final Map<String, CompletableFuture<RpcResponse<Object>>>  UNPROCESSED_RESPONSE_FUTURES = new ConcurrentHashMap<>();

    public  void put(String requestId, CompletableFuture<RpcResponse<Object>> future){

        UNPROCESSED_RESPONSE_FUTURES.put(requestId,future);

    }

    public void complete(RpcResponse<Object> rpcResponse) {
        CompletableFuture<RpcResponse<Object>> future = UNPROCESSED_RESPONSE_FUTURES.remove(rpcResponse.getRequestId());
        if (null != future) {
            future.complete(rpcResponse);
        } else {
            throw new IllegalStateException();
        }
    }
}
