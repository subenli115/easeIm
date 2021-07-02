package com.benwunet.easeui.interfaces;

public interface OnCallBack<T> {
    void onSuccess(T models);
    void onError(int code, String error);
}
