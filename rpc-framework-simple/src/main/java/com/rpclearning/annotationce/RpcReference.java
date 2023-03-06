package com.rpclearning.annotationce;

import java.lang.annotation.*;

/**
 * Description： RPC reference annotation, autowire the service implementation class
 *
 * @author: 段世超
 * @aate: Created in 2023/3/6 14:37
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {

    /**
     * Service version, default value is empty string
     */
    String version() default "";

    /**
     * Service group, default value is empty string
     */
    String group() default "";
}
