package pl.pollub.projektandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DodajDoTerminarza  extends AppCompatActivity {
    String email;
    String login, date;
    String minuta;
    int user_id;
    Button ustaw;
    TextView godzina;
    String godzina2 = null;
    int numer_godziny, numer_minuty;
    String godzinaDoBazy = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dodaj_do_terminarza);
        super.onCreate(savedInstanceState);
        ustaw = findViewById(R.id.ustaw);
        godzina = findViewById(R.id.godzina_wydarz);
        NumberPicker picker = findViewById(R.id.ile_godzin);
        TimePicker picker1 = findViewById(R.id.timeP);
        picker1.setIs24HourView(true);
        picker.setMaxValue(24);
        picker.setMinValue(1);
        EditText nazwa_wydarzenia = findViewById(R.id.nazwa_w);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                login = null;
                email = null;
                date = null;
            } else {
                login = extras.getString("login");
                email = extras.getString("email");
                date = extras.getString("data");
            }
        } else {
            login = savedInstanceState.getString("login");
            email = savedInstanceState.getString("email");
            date = savedInstanceState.getString("data");
        }

        ApiInterfaceJakieId apiService = ApiClient.getRetrofit().create(ApiInterfaceJakieId.class);
        Call<List<IdInfo>> call = apiService.getId(login);

        call.enqueue(new Callback<List<IdInfo>>() {
            @Override
            public void onResponse(Call<List<IdInfo>> call, Response<List<IdInfo>> response) {
                user_id = response.body().get(0).getId();
                Log.d("TAG", "Response = " + login);
            }

            @Override
            public void onFailure(Call<List<IdInfo>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }

        });
        ustaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker1.setVisibility(View.VISIBLE);
            }
        });

        picker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {

                numer_godziny = hour;
                numer_minuty = minute;

                if(numer_minuty < 10)
                {
                    minuta = "0"+numer_minuty;
                }
                else
                {
                    minuta = String.valueOf(numer_minuty);
                }
                godzinaDoBazy = hour + ":" + minuta;
                godzina.setText("Godzina rozpoczęcia: "+ hour + ":" + minuta);
            }
        });
        Button przycisk = findViewById(R.id.przycisk_dodaj_do_term);
        przycisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wydarzenie = nazwa_wydarzenia.getText().toString();
                String liczba = "" + picker.getValue();
                int godzina_zakonczenia = numer_godziny + picker.getValue();
                int godzina_zakonczenia2 = godzina_zakonczenia;
                Log.d("Test1",String.valueOf(godzina_zakonczenia));
                if (godzina_zakonczenia >= 24)
                {
                    godzina_zakonczenia2 = godzina_zakonczenia - 24;
                    Log.d("Test2",String.valueOf(godzina_zakonczenia2));

                }
                if (godzina_zakonczenia2 >= 10) {
                    godzina2 = godzina_zakonczenia2 + ":" + minuta;
                    Log.d("Test3",godzina2);
                }
                else
                {
                    godzina2 = "0"+godzina_zakonczenia2+":" + minuta;
                    Log.d("Test4",godzina2);
                }
                if (wydarzenie.length() == 0) {
                    nazwa_wydarzenia.setError("Nie wpisaleś nazwy wydarzenia!");
                } else if (wydarzenie.length() > 40) {
                    nazwa_wydarzenia.setError("Nazwa wydarzenia jest za długa");
                } else if (godzinaDoBazy == null) {
                    nazwa_wydarzenia.setError("Nie podałeś godziny!");
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "name";
                            field[1] = "time";
                            field[2] = "start_time";
                            field[3] = "date";
                            field[4] = "user_id";
                            field[5] = "end_time";
                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = wydarzenie;
                            data[1] = liczba;
                            data[2] = godzinaDoBazy;
                            data[3] = date;
                            data[4] = String.valueOf(user_id);
                            data[5] = godzina2;

                            PutData putData = new PutData("http://192.168.1.12/Projekt/terminarzyk.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Udalo sie dodac wydarzenie do bazy danych")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Terminarz.class);
                                        intent.putExtra("email", email);
                                        intent.putExtra("login", login);
                                        intent.putExtra("data", date);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }

                        }
                    });
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(DodajDoTerminarza.this, Terminarz.class);
                intent.putExtra("email", email);
                intent.putExtra("login", login);
                intent.putExtra("data", date);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("email", email);
        outState.putString("login", login);
        outState.putString("data", date);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        email = savedInstanceState.getString("email");
        login = savedInstanceState.getString("login");
        date = savedInstanceState.getString("data");
    }

    public void onBackPressed() {
        Log.d("Tag", "Kliknięto");
        Intent intent = new Intent(DodajDoTerminarza.this, Terminarz.class);
        intent.putExtra("email", email);
        intent.putExtra("login", login);
        intent.putExtra("data", date);
        startActivity(intent);
        finish();
    }
}
