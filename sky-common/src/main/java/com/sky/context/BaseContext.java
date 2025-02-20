package com.sky.context;

public class BaseContext {
    //TreadLocal为每个线程提供单独一份存储空间，具有线程隔离的效果
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
