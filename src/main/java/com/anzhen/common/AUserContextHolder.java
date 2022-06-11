package com.anzhen.common;

public class AUserContextHolder {
    static ThreadLocal<AUserContext> userContextHolderThreadLocal = new ThreadLocal<>();

    public static AUserContext getAUserContext() {
        return userContextHolderThreadLocal.get();
    }


    public static void setAUserContext(AUserContext aUserContext) {
        userContextHolderThreadLocal.set(aUserContext);
    }
}
