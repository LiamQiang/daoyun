package com.bean;

public class User {

    int uid=0;
    int role=0;
    String tel="0";
    String username="0",nickname="0";
    String token="0";
    boolean isLogin=false;
    boolean isFirst=true;

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }



    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTel() {
        return tel;
    }

    public String getToken() {
        return token;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setToken(String token) {
        this.token = token;
    }

    String account;
    int isOnLine;
    public String getAccount(){
        return account;
    }
    public int getIsOnline(){
        return isOnLine;
    }
    public void setAccount(String account){
        this.account=account;
    }
    public void setIsOnline(int isOnline){
        this.isOnLine=isOnline;
    }
}
