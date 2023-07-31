package pl.pollub.projektandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceToDo
{
    @GET("ToDoAPI.php")
    Call<List<ToDo_Element>> getWydarzenia(@Query("login") String login);
}

