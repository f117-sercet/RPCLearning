package com.rpclearning.remoting.transport.netty.client;

import com.rpclearning.remoting.dto.RpcRequest;
import com.rpclearning.remoting.transport.RpcRequestTransport;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/3/7 9:43
 */
@Slf4j
public final class NettyRpcClient implements RpcRequestTransport {

    private final ServiceDiscovery serviceDiscovery;
    private final UnprocessedRequests unprocessedRequests;
    private final ChannelProvider channelProvider;
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;
    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        return null;
    }
}
