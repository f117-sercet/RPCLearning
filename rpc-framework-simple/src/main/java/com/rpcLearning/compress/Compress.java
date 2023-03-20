package com.rpcLearning.compress;

import com.rpcLearning.extension.SPI;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/3/6 15:02
 */
@SPI
public interface Compress {
    byte[] compress(byte[] bytes);


    byte[] decompress(byte[] bytes);
}
