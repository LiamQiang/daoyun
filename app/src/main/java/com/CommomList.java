package com;

public class CommomList<T> {
    int code;
    String message;
    T object;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getList() {
        return object;
    }

    public void setList(T list) {
        this.object = object;
    }
}
