package com.rpcLearning.remoting.transport.socket;

import com.rpcLearning.enums.SerializationTypeEnum;
import com.rpcLearning.enums.ServiceDiscoveryEnum;
import com.rpcLearning.enums.ServiceRegistryEnum;
import com.rpcLearning.exception.RpcException;
import com.rpcLearning.extension.ExtensionLoader;
import com.rpcLearning.registry.ServiceDiscovery;
import com.rpcLearning.remoting.dto.RpcRequest;
import com.rpcLearning.remoting.transport.RpcRequestTransport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 基于Socket 传输 RpcRequest
 *
 * @author: 段世超
 * @create: Created in 2023/3/29 17:55
 */
@AllArgsConstructor
@Slf4j
public class SocketRpcClient implements RpcRequestTransport {

    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension(ServiceDiscoveryEnum.ZK.getName());
    }



    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {

        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest);
        try( Socket socket = new Socket()){
            socket.connect(inetSocketAddress);
            ObjectOutput objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // send data to the server through the output stream

            objectOutputStream.writeObject(rpcRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            return objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){

            throw new RpcException("调用服务失败",e);
        }
    }
}
