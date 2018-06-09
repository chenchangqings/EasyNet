package com.android.ccq.easynet;

import com.android.ccq.easynet.callback.IMethod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class EasyNetFactory {

    public <T> T create(final Class<T> service,final IMethod iMethod) throws IllegalAccessException, InstantiationException {

        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
                new InvocationHandler() {


                    @Override public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
                        DoRequst annotation = method.getAnnotation(DoRequst.class);
                        iMethod.doMethod(annotation.code(), annotation.url(), args[0],method.getReturnType(),args[1]);
                        return  null;
                    }
                });
    }



}
