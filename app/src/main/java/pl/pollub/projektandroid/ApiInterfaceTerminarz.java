package pl.pollub.projektandroid;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceTerminarz {
    @GET("terminarzAPI.php")
    Call<List<Terminarz_Element>> getWydarzenia(@Query("login") String login, @Query("date") String data);
}
