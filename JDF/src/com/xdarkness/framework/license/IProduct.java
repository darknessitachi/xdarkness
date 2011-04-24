package com.xdarkness.framework.license;

/**
 * 
 * @author Darkness Create on 2010-5-19 下午04:32:08
 * @version 1.0
 */
public abstract interface IProduct {

    /**
     * 产品代码
     * @return
     */
    String getAppCode();

    /**
     * 产品名称
     * @return
     */
    String getAppName();

    /**
     * 产品主版本
     * @return
     */
    float getMainVersion();

    /**
     * 产品内部版本
     * @return
     */
    float getMinorVersion();
}
