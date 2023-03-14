package com.rpclearning.remoting.transport.netty.client;

import com.rpcLearning.enums.CompressTypeEnum;
import com.rpcLearning.enums.SerializationTypeEnum;
import com.rpcLearning.enums.ServiceDiscoveryEnum;
import com.rpcLearning.extension.ExtensionLoader;
import com.rpcLearning.factory.SingletonFactory;
import com.rpclearning.registry.ServiceDiscovery;
import com.rpclearning.remoting.constant.RpcConstants;
import com.rpclearning.remoting.dto.RpcMessage;
import com.rpclearning.remoting.dto.RpcRequest;
import com.rpclearning.remoting.dto.RpcResponse;
import com.rpclearning.remoting.transport.RpcRequestTransport;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.protostuff.Rpc;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.serializer.support.SerializationDelegate;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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

    public NettyRpcClient(){

        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        // If no data is sent to the server within 15 seconds, a heartbeat request is sent
                        p.addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS));
                        p.addLast(new RpcMessageEncoder());
                        p.addLast(new RpcMessageDecoder());
                        p.addLast(new NettyRpcClientHandler());
                    }
                });



        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension(ServiceDiscoveryEnum.ZK.getName());
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
        this.channelProvider = SingletonFactory.getInstance(ChannelProvider.class);
    }

    @SneakyThrows
    public Channel doConnect(InetSocketAddress inetSocketAddress){
        CompletableFuture<Channel> completableFuture = new CompletableFuture<>();
        bootstrap.connect(inetSocketAddress).addListener((ChannelFutureListener)future -> {
            if (future.isSuccess()){
                log.info("The client has connected [{}] successful!", inetSocketAddress.toString());
            }else {
                throw new IllegalStateException();
            }
        });
        return completableFuture.get();
    }


    @Override
    @SneakyThrows
    public Object sendRpcRequest(RpcRequest rpcRequest) {

        CompletableFuture<RpcResponse<Object>> resultFuture = new CompletableFuture<>();
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest);
        Channel channel = getChannel(inetSocketAddress);
        if (channel.isActive()) {
            unprocessedRequests.put(rpcRequest.getRequestId(), resultFuture);
            RpcMessage rpcMessage = RpcMessage.builder().data(rpcRequest)
                    .codec(SerializationTypeEnum.HESSIAN.getCode())
                    .compress(CompressTypeEnum.GZIP.getCode())
                    .messageType(RpcConstants.REQUEST_TYPE).build();
            channel.writeAndFlush(rpcMessage).addListener((ChannelFutureListener)future ->{

                if (future.isSuccess()){
                    log.info("client send message: [{}]", rpcMessage);
                }else {

                    future.channel().close();
                    resultFuture.completeExceptionally(future.cause());
                    log.error("Send failed",future.cause());
                }
            });

        }else {
            throw new IllegalStateException();
        }

        return resultFuture;
    }

    public Channel getChannel(InetSocketAddress inetSocketAddress) {

        Channel channel = channelProvider.get(inetSocketAddress);

        if (channel == null) {
            channel = doConnect(inetSocketAddress);
            channelProvider.set(inetSocketAddress,channel);
        }
        return channel;
    }
    public void close(){eventLoopGroup.shutdownGracefully();}
}
