package com.android.ccq.easynet.callback;

import com.lzy.okgo.model.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Description : 网络请求实体类回调
 * Author :      Gaozhanzhong
 * Create :      2017/10/18 20:24
 * Update :      2017/10/18 20:24
 * Version :     V1.1.1
 */

public abstract class ModelCallback<T> extends BaseNetCallback {
    /**
     * 获取Model的type，用以解析数据
     * @return ，Model的Class
     */
    public Class<T> getGenericClass(){
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<T>) params[0];
    }

}
