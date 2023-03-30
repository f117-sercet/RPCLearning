package com.rpcLearning.remoting.transport.socket;

import com.rpcLearning.factory.SingletonFactory;
import com.rpcLearning.remoting.dto.RpcRequest;
import com.rpcLearning.remoting.dto.RpcResponse;
import com.rpcLearning.remoting.handler.RpcRequestHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @create:  Created in 2023/3/30 9:51
 */
@Slf4j
public class SocketRpcRequestHandlerRunnable implements Runnable {

    private final Socket socket;
    private final RpcRequestHandler rpcRequestHandler;

    public SocketRpcRequestHandlerRunnable(Socket socket) {
        this.socket = socket;
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }
        @Override
        public void run () {

            log.info("server handle message from client by thread:[{}]", Thread.currentThread().getName());
            try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {

                RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
                Object result = rpcRequestHandler.handle(rpcRequest);
                objectOutputStream.writeObject(RpcResponse.success(result, rpcRequest.getRequestId()));
                objectOutputStream.flush();

            } catch (IOException | ClassNotFoundException e) {
                log.error("occur exception:", e);
            }
        }
    }
