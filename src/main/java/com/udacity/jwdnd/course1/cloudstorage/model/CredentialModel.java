package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialModel {

    private final Integer credentialId;
    private final String url;
    private final String username;
    private final String key;
    private final String password;

    public CredentialModel(Integer credentialId, String url, String username, String key, String password) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public String getUrl() {
        return url;
    }

    public String getKey() {
        return key;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
