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

public class EdytujLista extends AppCompatActivity
{
    String email;
    String login, produkt, liczba;
    int user_id, id;
    String licz;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.dodaj_do_listy);
        super.onCreate(savedInstanceState);
        NumberPicker picker = findViewById(R.id.ile_sztuk);
        picker.setMaxValue(100);
        picker.setMinValue(1);
        NumberPicker picker2 = findViewById(R.id.ile_sztuk2);
        picker2.setMinValue(0);
        picker2.setMaxValue(5);
        picker2.setDisplayedValues(new String[] { "g", "dkg", "kg", "ml", "l", " "});
        String[] displayedValues = picker2.getDisplayedValues();
        EditText nazwa_produktu = findViewById(R.id.jaka_nazwa);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                login = null;
                email = null;
            } else {
                login = extras.getString("login");
                email = extras.getString("email");
                id = extras.getInt("id");
                produkt = extras.getString("produkt");
                liczba = extras.getString("liczba");
                nazwa_produktu.setText(produkt);
                if(liczba.indexOf(" ") != -1) {
                    licz = liczba.substring(0, liczba.indexOf(' '));
                }
                else licz = liczba;
                picker.setValue(Integer.parseInt(licz));
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

        Button przycisk = findViewById(R.id.przycisk_dodaj_do_listy);
        przycisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String produkt = nazwa_produktu.getText().toString();
                String ilosc = "" + picker.getValue() + " " +displayedValues[picker2.getValue()];
                String czyWcisnieto = String.valueOf('0');
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                if (produkt.length() == 0)
                {
                    nazwa_produktu.setError("Nie wpisaleś nazwy produktu!");
                }
                else if(produkt.length() > 15)
                {
                    nazwa_produktu.setError("Nazwa produktu jest za długa");
                }
                else
                {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "name";
                            field[1] = "amount";
                            field[2] = "status";
                            field[3] = "user_id";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = produkt;
                            data[1] = ilosc;
                            data[2] = czyWcisnieto;
                            data[3] = String.valueOf(user_id);
                            PutData putData = new PutData("http://87.246.222.160/Projekt/UpdateListaZakupow.php?id="+id+"&produkt="+produkt+"&liczba="+ilosc, "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {

                                        Intent intent = new Intent(getApplicationContext(), Lista.class);
                                        intent.putExtra("email", email);
                                        intent.putExtra("login", login);
                                        startActivity(intent);
                                        finish();
                                        nazwa_produktu.setText("");


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
                Intent intent = new Intent(EdytujLista.this, Lista.class);
                intent.putExtra("email", email);
                intent.putExtra("login", login);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
    public void onBackPressed() {
        Log.d("Tag", "Kliknięto");
        Intent intent = new Intent(EdytujLista.this, Lista.class);
        intent.putExtra("email", email);
        intent.putExtra("login", login);
        startActivity(intent);
        finish();
    }
}
