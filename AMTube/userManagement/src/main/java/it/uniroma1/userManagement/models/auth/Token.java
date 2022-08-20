package it.uniroma1.userManagement.models.auth;

import java.util.Date;

public class Token {
    private String token;
    private Date expirationDate;

    public Token(String token, Date expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }


    public Token() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
