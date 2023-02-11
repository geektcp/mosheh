package com.geektcp.common.core.concurrent.cas;

import sun.misc.Unsafe;
/**
 * @author geektcp on 2023/2/2 0:59.
 */
public class UnSafeBuilder {

    private static Unsafe unsafe = Unsafe.getUnsafe();


    private UnSafeBuilder(){
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }

}
