package com.shriram.microservices.valueObject;

public class CredentialsValueObject {

    private String username, password;

    public CredentialsValueObject() {

    }

    public CredentialsValueObject(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
