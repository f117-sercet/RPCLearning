package com.rpcLearning.annotationce;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * scan custom annotations
 *
 * @author: 段世超
 * @aate: Created in 2023/3/6 14:48
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegistrar.class)
@Documented
public @interface RpcScan {

}
