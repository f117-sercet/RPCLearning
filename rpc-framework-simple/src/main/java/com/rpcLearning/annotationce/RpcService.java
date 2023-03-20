package com.rpcLearning.annotationce;

import java.lang.annotation.*;

/**
 * RPC service annotation, marked on the service implementation class
 *
 * @author: 段世超
 * @aate: Created in 2023/3/6 14:49
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RpcService {
    /**
     * Service version, default value is empty string
     */
    String version() default "";

    /**
     * Service group, default value is empty string
     */
    String group() default "";

}
