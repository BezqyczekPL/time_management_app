package pl.pollub.projektandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DodajDoToDoList extends AppCompatActivity
{
    String data_dead;
    String godzina_dead;
    String email;
    LocalDate data_deadline2;
    int user_id;
    String login;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.dodaj_do_to_do);
        super.onCreate(savedInstanceState);
        TimePicker picker1=findViewById(R.id.TimePicker1);
        picker1.setIs24HourView(true);
        DatePicker picker2=findViewById(R.id.DatePicker1);
        TextView data_deadline = findViewById(R.id.data_deadline);
        TextView godzina_deadline = findViewById(R.id.godzina_deadline);


        EditText nazwa_wydarzenia = findViewById(R.id.nazwa_wyd);

        Button potwierdz = findViewById(R.id.potwierdz);

        Button wybierz_date_deadline = findViewById(R.id.wybierz_date_deadline);
        Button wybierz_godzine_deadline = findViewById(R.id.wybierz_godzine_deadline);

        Button dodaj_wydarzenie = findViewById(R.id.dodaj_wydarzenie);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                login = null;
                email = null;
            } else {
                login = extras.getString("login");
                email = extras.getString("email");
            }
        } else {
            login = savedInstanceState.getString("login");
            email = savedInstanceState.getString("email");
        }

        ApiInterfaceJakieId apiService = ApiClient.getRetrofit().create(ApiInterfaceJakieId.class);
        Call<List<IdInfo>> call = apiService.getId(login);

        call.enqueue(new Callback<List<IdInfo>>() {
            @Override
            public void onResponse(Call<List<IdInfo>>  call, Response<List<IdInfo>> response) {
                user_id = response.body().get(0).getId();
                Log.d("TAG","Response = " + login);
            }

            @Override
            public void onFailure(Call<List<IdInfo>>  call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }

        });

        wybierz_date_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                picker1.setVisibility(View.INVISIBLE);
                picker2.setVisibility(View.VISIBLE);
                potwierdz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        int dzien = picker2.getDayOfMonth();
                        int miesiac = picker2.getMonth()+1;
                        int rok = picker2.getYear();
                        String data = dzien + "." + miesiac + "." + rok;
                        String data_do_bazy = rok + "-" + miesiac + "-" + dzien;
                        data_deadline.setText("Wybrana data: " + data );
                        data_dead = data_do_bazy;
                    }
                });

            }
        });


        wybierz_godzine_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                picker1.setVisibility(View.VISIBLE);
                picker2.setVisibility(View.INVISIBLE);
                potwierdz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        int minuty = picker1.getMinute();
                        int godziny = picker1.getHour();

                        String Minuty = ""+minuty;
                        if (minuty < 10)
                        {
                            Minuty = "0"+Minuty;
                        }
                        String godzina = godziny + ":" + Minuty;
                        String godzina_do_bazy = godziny + ":" + Minuty + ":00";

                        godzina_deadline.setText("Wybrana godzina: " + godzina );
                        godzina_dead = godzina_do_bazy;
                    }
                });

            }
        });


        dodaj_wydarzenie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (data_dead != null) {
                try {

                        Date data_deadline1 = dateFormat.parse(data_dead);
                        data_deadline2 = data_deadline1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                        if (data_dead != null && godzina_dead != null && nazwa_wydarzenia.getText().toString().trim().length() > 0 && nazwa_wydarzenia.getText().toString().trim().length() <= 20) {
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Starting Write and Read data with URL
                                    //Creating array for parameters
                                    String[] field = new String[4];
                                    field[0] = "title";
                                    field[1] = "deadline_day";
                                    field[2] = "deadline_time";
                                    field[3] = "user_id";
                                    //Creating array for data
                                    String[] data = new String[5];
                                    data[0] = nazwa_wydarzenia.getText().toString();
                                    data[1] = data_dead;
                                    data[2] = godzina_dead;
                                    data[3] = String.valueOf(user_id);
                                    PutData putData = new PutData("http://192.168.1.12/Projekt/terminarz.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            if (result.equals("Udalo sie dodac wydarzenie do bazy danych")) {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), ToDoList.class);
                                                intent.putExtra("email", email);
                                                intent.putExtra("login", login);
                                                startActivity(intent);
                                                finish();
                                                nazwa_wydarzenia.setText("");
                                            } else {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                                Log.d("TAG","Response = " + nazwa_wydarzenia.getText().toString() + " " + data_dead + " " + godzina_dead + " " + user_id);
                                            }

                                        }
                                    }

                                }
                            });
                        } else
                            Toast.makeText(DodajDoToDoList.this, "Nie dodałeś którejś z wartości lub któraś jest niepoprawna!", Toast.LENGTH_LONG).show();

                }


            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(DodajDoToDoList.this, ToDoList.class);
                intent.putExtra("email", email);
                intent.putExtra("login", login);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        Log.d("Tag", "Kliknięto");
        Intent intent = new Intent(DodajDoToDoList.this, ToDoList.class);
        intent.putExtra("email", email);
        intent.putExtra("login", login);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putString("email", email);
        outState.putString("login", login);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        email = savedInstanceState.getString("email");
        login = savedInstanceState.getString("login");
    }
}