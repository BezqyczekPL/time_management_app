package pl.pollub.projektandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceLista {
    @GET("listaAPI.php")
    Call<List<Lista_Element>> getProdukty(@Query("login") String login);
}
