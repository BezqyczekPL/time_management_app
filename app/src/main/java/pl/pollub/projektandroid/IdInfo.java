package pl.pollub.projektandroid;

import com.google.gson.annotations.SerializedName;

public class IdInfo
{
    @SerializedName("id")
    private int user_id;

    public int getId() { return user_id; }

    public void setId(int user_id) { this.user_id = user_id; }
}
