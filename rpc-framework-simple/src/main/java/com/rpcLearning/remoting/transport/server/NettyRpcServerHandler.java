package com.rpcLearning.remoting.transport.server;

import com.rpcLearning.enums.CompressTypeEnum;
import com.rpcLearning.enums.RpcResponseCodeEnum;
import com.rpcLearning.enums.SerializationTypeEnum;
import com.rpcLearning.factory.SingletonFactory;
import com.rpcLearning.remoting.constant.RpcConstants;
import com.rpcLearning.remoting.dto.RpcMessage;
import com.rpcLearning.remoting.dto.RpcRequest;
import com.rpcLearning.remoting.dto.RpcResponse;
import com.rpcLearning.remoting.handler.RpcRequestHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * Customize the ChannelHandler of the server to process the data sent by the client.<p>
 *如果继承自 SimpleChannelInboundHandler 的话就不要考虑 ByteBuf 的释放 ，{@link SimpleChannelInboundHandler} 内部的
 * channelRead 方法会替你释放 ByteBuf ，避免可能导致的内存泄露问题。详见《Netty进阶之路 跟着案例学 Netty》
 *
 * @author: 段世超
 * @aate: Created in 2023/3/27 17:26
 */
@Slf4j
public class NettyRpcServerHandler extends ChannelInboundHandlerAdapter {

    private final RpcRequestHandler rpcRequestHandler;

    public NettyRpcServerHandler() {
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }

    @Override
    @SneakyThrows
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
        if (msg instanceof RpcMessage) {

            log.info("server received msg[{}]", msg);
            byte messageType = ((RpcMessage) msg).getMessageType();
            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setCodec(SerializationTypeEnum.HESSIAN.getCode());
            rpcMessage.setCompress(CompressTypeEnum.GZIP.getCode());
            if (messageType == RpcConstants.HEARTBEAT_REQUEST_TYPE) {
                rpcMessage.setMessageType(RpcConstants.HEARTBEAT_RESPONSE_TYPE);
                rpcMessage.setData(RpcConstants.PONG);

            } else {
                RpcRequest rpcRequest = (RpcRequest) ((RpcMessage) msg).getData();
                // Execute the target method (the method the client needs to execute) and return the method result
                Object result = rpcRequestHandler.handle(rpcRequest);
                log.info(String.format("server get result: %s", result.toString()));
                rpcMessage.setMessageType(RpcConstants.RESPONSE_TYPE);
                if (ctx.channel().isActive() && ctx.channel().isWritable()) {
                    RpcResponse<Object> rpcResponse = RpcResponse.success(result, rpcRequest.getRequestId());
                    rpcMessage.setData(rpcResponse);
                } else {
                    RpcResponse<Object> rpcResponse = RpcResponse.fail(RpcResponseCodeEnum.FAIL);
                    rpcMessage.setData(rpcResponse);
                    log.error("not writable now,message dropped");
                }
            }
            ctx.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    @SneakyThrows
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){

            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                log.info("服务端读操作超时");
                ctx.close();
            }
            if (state == IdleState.WRITER_IDLE){
                log.info("服务端写超时");
                ctx.close();
            }
            if (state == IdleState.ALL_IDLE){
                log.info("服务端读写都超时");
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("server catch exception");
        cause.printStackTrace();
        ctx.close();
    }
}
