package com.rpclearning.remoting.dto;

import lombok.*;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/3/6 16:55
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcMessage {
    /**
     * rpc message type
     */
    private byte messageType;
    /**
     * serialization type
     */
    private byte codec;
    /**
     * compress type
     */
    private byte compress;
    /**
     * request id
     */
    private int requestId;
    /**
     * request data
     */
    private Object data;
}
