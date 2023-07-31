package pl.pollub.projektandroid;

import com.google.gson.annotations.SerializedName;

public class LoginInfo
{
    @SerializedName("login")
    private String login;

    public String getLogin() { return login; }

    public void setLogin(String profil_uzytkownik_id) { this.login = login; }
}
