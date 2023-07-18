package com.udacity.jwdnd.course1.cloudstorage.models;

public class Credentials {
    private Integer credentialId;
    private String url;
    private String username;
    private String enKey;
    private String password;
    private Integer userId;

    //no argument constructor
    public Credentials() {
    }
    public Credentials(Integer credentialId,String url, String username, String enkey, String password, Integer userId) {
        this.url = url;
        this.username = username;
        this.enKey = enkey;
        this.password = password;
        this.userId = userId;
        this.credentialId= credentialId;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEnKey() {
        return enKey;
    }

    public void setEnKey(String enKey) {
        this.enKey = enKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }
}
