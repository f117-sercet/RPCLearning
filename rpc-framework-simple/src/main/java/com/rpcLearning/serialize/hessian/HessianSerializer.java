package com.rpcLearning.serialize.hessian;

import com.rpcLearning.serialize.Serializer;

/**
 * Description： Hessian is a dynamically-typed, binary serialization and Web Services protocol designed for object-oriented transmission.
 *
 * @author: 段世超
 * @aate: Created in 2023/3/7 8:49
 */
public class HessianSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return null;
    }
}
