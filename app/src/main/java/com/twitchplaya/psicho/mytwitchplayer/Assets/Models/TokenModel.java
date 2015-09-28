package com.twitchplaya.psicho.mytwitchplayer.Assets.Models;


public class TokenModel {
    private String token;
    private String sig;

    public TokenModel(String token, String sig) {
        this.token = token;
        this.sig = sig;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }
}
