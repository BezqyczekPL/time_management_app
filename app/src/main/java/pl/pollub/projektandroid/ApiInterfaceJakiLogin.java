package pl.pollub.projektandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceJakiLogin {
    @GET("jakiLogin.php")
    Call<List<LoginInfo>> getLogin(@Query("email") String email);
}
