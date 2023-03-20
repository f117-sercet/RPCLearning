package com.rpcLearning;

import lombok.*;

import java.io.Serializable;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/3/4 18:37
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Hello implements Serializable {

    private String message;
    private String description;
}
