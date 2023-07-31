package pl.pollub.projektandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String login,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button wyloguj = findViewById(R.id.wyloguj);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                email = null;
                login = null;
            } else {
                email = extras.getString("email");
                login = extras.getString("login");
            }
        } else {
            email = savedInstanceState.getString("email");
            login = savedInstanceState.getString("login");
        }

        ApiInterfaceJakiLogin apiService2 = ApiClient.getRetrofit().create(ApiInterfaceJakiLogin.class);
        Call<List<LoginInfo>> call2 = apiService2.getLogin(email);

        call2.enqueue(new Callback<List<LoginInfo>>() {
            @Override
            public void onResponse(Call<List<LoginInfo>>  call, Response<List<LoginInfo>> response) {
                login = response.body().get(0).getLogin();
                Log.d("TAG","Response = " + login);
            }

            @Override
            public void onFailure(Call<List<LoginInfo>>  call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }

        });

        wyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencja = new Intent(MainActivity.this, LoginScreen.class);
                startActivity(intencja);
                finish();
            }
        });

        Button toDo = findViewById(R.id.todo);

        toDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencja = new Intent(MainActivity.this, ToDoList.class);
                intencja.putExtra("email", email);
                intencja.putExtra("login", login);
                startActivity(intencja);
                finish();
            }
        });

        Button prognoza = findViewById(R.id.prognoza);

        prognoza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencja = new Intent(MainActivity.this, Pogoda.class);
                intencja.putExtra("email", email);
                intencja.putExtra("login", login);
                startActivity(intencja);
                finish();
            }
        });

        Button lista = findViewById(R.id.lista);

        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencja = new Intent(MainActivity.this, Lista.class);
                intencja.putExtra("email", email);
                intencja.putExtra("login", login);
                startActivity(intencja);
                finish();
            }
        });

        Button kalendarz = findViewById(R.id.kalendarz);

        kalendarz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencja = new Intent(MainActivity.this, Kalendarz.class);
                intencja.putExtra("email", email);
                intencja.putExtra("login", login);
                startActivity(intencja);
                finish();
            }
        });

        Button terminarz = findViewById(R.id.terminarz);

        terminarz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencja = new Intent(MainActivity.this, Terminarz.class);
                LocalDate dateObj = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date = dateObj.format(formatter);
                intencja.putExtra("data", date);
                intencja.putExtra("email", email);
                intencja.putExtra("login", login);
                startActivity(intencja);
                finish();
            }
        });


    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putString("login", login);
        outState.putString("email", email);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        login = savedInstanceState.getString("login");
        email = savedInstanceState.getString("email");
    }
}