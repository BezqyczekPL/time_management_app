package pl.pollub.projektandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pl.pollub.projektandroid.R;

public class Pogoda extends AppCompatActivity {

    EditText editText;
    TextView stopnie, zachmurzenie, min, max, wschod, zachod, cisnienie, predkosc;
    ImageView ikonaPogody;
    Button pole1, pole2, dlugoterminowka;
    String nazwaMiasta, predkosc2, login, email;
    DecimalFormat df = new DecimalFormat("#.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pogoda);
        Log.d("Login:",login);
        editText = findViewById(R.id.editText);
        ikonaPogody = findViewById(R.id.ikonaPogody);
        stopnie = findViewById(R.id.stopnie);
        zachmurzenie = findViewById(R.id.zachmurzenie);
        min = findViewById(R.id.min);
        max = findViewById(R.id.max);
        wschod = findViewById(R.id.wschod);
        zachod = findViewById(R.id.zachod);
        pole1 = findViewById(R.id.button2);
        pole2 = findViewById(R.id.button4);
        cisnienie = findViewById(R.id.cisnienie);
        predkosc = findViewById(R.id.predkosc);
        dlugoterminowka = findViewById(R.id.dlugoterminowa);


    }

    public void getWeather(View view) {
        try {
            DownloadTask task = new DownloadTask();
            nazwaMiasta = URLEncoder.encode(editText.getText().toString(), "UTF-8");

            task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + nazwaMiasta + "&APPID=36b8a62828e7fe2a2632d7b5020bbaf6&lang=pl&units=metric").get();
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();
        }
    }

    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                pole1.setVisibility(View.VISIBLE);
                pole2.setVisibility(View.VISIBLE);
                dlugoterminowka.setVisibility(View.VISIBLE);

                String weatherInfo = jsonObject.getString("weather");
                int timezone = jsonObject.getInt("timezone");
                JSONObject temperaturaObject = jsonObject.getJSONObject("main");
                JSONObject wiatr = jsonObject.getJSONObject("wind");
                JSONObject slonce = jsonObject.getJSONObject("sys");

                String temperatura = temperaturaObject.getString("temp");
                String temperatura_min = temperaturaObject.getString("temp_min");
                String temperatura_max = temperaturaObject.getString("temp_max");
                String cisnienie1 = temperaturaObject.getString("pressure");
                Double predkosc1 = wiatr.getDouble("speed");
                predkosc1 = predkosc1*3.6;
                predkosc2 = df.format(predkosc1);

                Long wschod1 = slonce.getLong("sunrise");
                Long zachod1 = slonce.getLong("sunset");

                java.util.Date wschod_slonca =new java.util.Date(((long)wschod1*1000)+(timezone-3600)*1000);
                java.util.Date zachod_slonca =new java.util.Date(((long)zachod1*1000)+(timezone-3600)*1000);

                DateFormat sourceFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                DateFormat targetFormat = new SimpleDateFormat("HH:mm:ss");

                Date wsch = sourceFormat.parse(wschod_slonca.toString());
                String w = targetFormat.format(wsch);

                Date zach = sourceFormat.parse(zachod_slonca.toString());
                String z = targetFormat.format(zach);


                temperatura = temperatura.substring(0, temperatura.indexOf("."));
                temperatura_min = temperatura_min.substring(0, temperatura_min.indexOf("."));
                temperatura_max = temperatura_max.substring(0, temperatura_max.indexOf("."));

                Log.i("Weather content", jsonObject.toString());

                JSONArray arr = new JSONArray(weatherInfo);

                String message = "";

                for (int i=0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);

                    String description = jsonPart.getString("description");
                    String icon = jsonPart.getString("icon");
                    String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

                    Picasso.get().load(iconUrl).into(ikonaPogody);

                    if (!description.equals("")) {
                        message += description + "\r\n";
                    }
                }

                stopnie.setText(temperatura + "°");
                zachmurzenie.setText(message);
                max.setText("Max: "+temperatura_max+"°");
                min.setText("Min: "+temperatura_min+"°");
                wschod.setText("Wschód: "+w);
                zachod.setText("Zachód: "+z);
                cisnienie.setText("Ciśnienie: "+cisnienie1 + " hPa");
                predkosc.setText("Prędkość wiatru: "+predkosc2 + " km/h");



                dlugoterminowka.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DownloadTask.this.cancel(true);
                        Intent intencja = new Intent(Pogoda.this, DlugoterminowaPogoda.class);
                        intencja.putExtra("miasto", nazwaMiasta);
                        intencja.putExtra("login",login);
                        intencja.putExtra("email",email);
                        startActivity(intencja);
                        finish();
                    }
                });
                if (!message.equals("")) {
                    //resultTextView.setText(message);
                } else {
                    Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
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

    public void onBackPressed() {
        Log.d("Tag", "Kliknięto");
        Intent intent = new Intent(Pogoda.this, MainActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("login", login);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Pogoda.this, MainActivity.class);
                intent.putExtra("login", login);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}