package pl.pollub.projektandroid;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceJakieId {
    @GET("jakieId.php")
    Call<List<IdInfo>> getId(@Query("login") String login);
}
