package com.rpcLearning.remoting.transport.netty.client;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Customize the client ChannelHandler to process the data sent by the server
 *如果继承自 SimpleChannelInboundHandler 的话就不要考虑 ByteBuf 的释放 ，{@link SimpleChannelInboundHandler} 内部的
 * channelRead 方法会替你释放 ByteBuf ，避免可能导致的内存泄露问题。详见《Netty进阶之路 跟着案例学 Netty》
 * @author: 段世超
 * @aate: Created in 2023/3/8 17:28
 */
@Slf4j
public class NettyRpcClientHandler extends ChannelInboundHandlerAdapter {

    private final  UnprocessedRequests  unprocessedRequests;
}
