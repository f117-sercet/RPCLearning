package com.rpcLearning.remoting.transport.socket;

import com.rpcLearning.config.CustomShutdownHook;
import com.rpcLearning.config.RpcServiceConfig;
import com.rpcLearning.factory.SingletonFactory;
import com.rpcLearning.provider.ServiceProvider;
import com.rpcLearning.provider.impl.ZkServiceProviderImpl;
import com.rpcLearning.utils.concurrent.threadpool.ThreadPoolFactoryUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import static com.rpcLearning.remoting.transport.netty.server.NettyRpcServer.PORT;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @create: Created in 2023/3/30 10:19
 */
@Slf4j
public class SocketRpcServer {

    private final ExecutorService threadPool;
    private final ServiceProvider serviceProvider;

    public SocketRpcServer(){
         threadPool = ThreadPoolFactoryUtil.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
        serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }
    public void registerService(RpcServiceConfig rpcServiceConfig){

        serviceProvider.publishService(rpcServiceConfig);
    }

    public void start(){
        try(ServerSocket server = new ServerSocket()) {
            String host = InetAddress.getLocalHost().getHostAddress();
            server.bind(new InetSocketAddress(host,PORT));
            CustomShutdownHook.getCustomShutdownHook().clearAll();
            Socket socket;

            while ((socket = server.accept()) != null) {
                log.info("client connected [{}]", socket.getInetAddress());
                threadPool.execute(new SocketRpcRequestHandlerRunnable(socket));
            }

        }catch (IOException e) {
            log.error("occur IOException:", e);
            e.printStackTrace();
        }
    }
}
