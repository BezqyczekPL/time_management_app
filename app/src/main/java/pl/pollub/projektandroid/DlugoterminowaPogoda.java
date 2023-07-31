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

import pl.pollub.projektandroid.R;

public class DlugoterminowaPogoda extends AppCompatActivity {

    TextView jutroMin, jutroMax, jutroCisnienie, pojutrzeMin, pojutrzeMax, pojutrzeCisnienie, pozniejMin, pozniejMax, pozniejCisnienie;
    TextView jutroOpis, pojutrzeOpis, pozniejOpis;
    String nazwaMiasta, login, email;
    View view;
    ImageView jutroIkona, pojutrzeIkona, pozniejIkona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pogodadlugoterminowa);

            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nazwaMiasta = null;
                login = null;
                email = null;
            } else {
                nazwaMiasta = extras.getString("miasto");
                login = extras.getString("login");
                email = extras.getString("email");
            }

        Log.d("Login:",login);
        jutroMin = findViewById(R.id.jutroMin);
        jutroMax = findViewById(R.id.jutroMax);
        jutroCisnienie = findViewById(R.id.jutroCisnienie);
        pojutrzeMin = findViewById(R.id.pojutrzeMin);
        pojutrzeMax = findViewById(R.id.pojutrzeMax);
        pojutrzeCisnienie = findViewById(R.id.pojutrzeCisnienie);
        pozniejMin = findViewById(R.id.pozniejMin);
        pozniejMax = findViewById(R.id.pozniejMax);
        pozniejCisnienie = findViewById(R.id.pozniejCisnienie);
        jutroIkona = findViewById(R.id.jutroIkona);
        pojutrzeIkona = findViewById(R.id.pojutrzeIkona);
        pozniejIkona = findViewById(R.id.pozniejIkona);
        jutroOpis = findViewById(R.id.jutroOpis);
        pojutrzeOpis = findViewById(R.id.pojutrzeOpis);
        pozniejOpis = findViewById(R.id.pozniejOpis);
        Log.i("Test4", "Działa");
        getWeather(view);
    }

    public void getWeather(View view) {
        try {
            DownloadTask task = new DownloadTask();
            Log.i("Test3", "Działa");
            task.execute("http://api.openweathermap.org/data/2.5/forecast?q=" + nazwaMiasta + "&APPID=36b8a62828e7fe2a2632d7b5020bbaf6&lang=pl&units=metric").get();
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
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
            Log.i("Test2", "Działa");
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
                Log.i("Weather content", jsonObject.toString());
                String lista_dni = jsonObject.getString("list");
                JSONArray arr = new JSONArray(lista_dni);

                JSONObject miasto = jsonObject.getJSONObject("city");
                int strefaCzasowa = miasto.getInt("timezone");
                strefaCzasowa = strefaCzasowa/7200;

                JSONObject jutro = arr.getJSONObject(5+strefaCzasowa);
                JSONObject jutroNoc = arr.getJSONObject(11+strefaCzasowa);
                JSONObject pojutrze = arr.getJSONObject(13+strefaCzasowa);
                JSONObject pojutrzeNoc = arr.getJSONObject(19+strefaCzasowa);
                JSONObject pozniej = arr.getJSONObject(21+strefaCzasowa);
                JSONObject pozniejNoc = arr.getJSONObject(27+strefaCzasowa);

                String pogodaJutro = jutro.getString("weather");
                String pogodaPojutrze = pojutrze.getString("weather");
                String pogodaPozniej = pozniej.getString("weather");
                JSONArray arrJutro = new JSONArray(pogodaJutro);
                JSONArray arrPojutrze = new JSONArray(pogodaPojutrze);
                JSONArray arrPozniej = new JSONArray(pogodaPozniej);
                JSONObject jutroPogoda = arrJutro.getJSONObject(0);
                JSONObject pojutrzePogoda = arrPojutrze.getJSONObject(0);
                JSONObject pozniejPogoda = arrPozniej.getJSONObject(0);

                JSONObject jutroTemp = jutro.getJSONObject("main");
                JSONObject pojutrzeTemp = pojutrze.getJSONObject("main");
                JSONObject pozniejTemp = pozniej.getJSONObject("main");
                JSONObject jutroNocTemp = jutroNoc.getJSONObject("main");
                JSONObject pojutrzeNocTemp = pojutrzeNoc.getJSONObject("main");
                JSONObject pozniejNocTemp = pozniejNoc.getJSONObject("main");

                String jMin = jutroNocTemp.getString("temp_min");
                jMin = jMin.substring(0, jMin.indexOf("."));
                String jMax = jutroTemp.getString("temp_max");
                jMax = jMax.substring(0, jMax.indexOf("."));
                String jCis = jutroTemp.getString("pressure");
                String jOpis = jutroPogoda.getString("description");
                String jIkona = jutroPogoda.getString("icon");
                String jUrl = "http://openweathermap.org/img/w/" + jIkona + ".png";
                String pMin = pojutrzeNocTemp.getString("temp_min");
                pMin = pMin.substring(0, pMin.indexOf("."));
                String pMax = pojutrzeTemp.getString("temp_max");
                pMax = pMax.substring(0, pMax.indexOf("."));
                String pCis = pojutrzeTemp.getString("pressure");
                String pOpis = pojutrzePogoda.getString("description");
                String pIkona = pojutrzePogoda.getString("icon");
                String pUrl = "http://openweathermap.org/img/w/" + pIkona + ".png";
                String min = pozniejNocTemp.getString("temp_min");
                min = min.substring(0, min.indexOf("."));
                String max = pozniejTemp.getString("temp_max");
                max = max.substring(0, max.indexOf("."));
                String cis = pozniejTemp.getString("pressure");
                String opis = pozniejPogoda.getString("description");
                String ikona = pozniejPogoda.getString("icon");
                String url = "http://openweathermap.org/img/w/" + ikona + ".png";

                jutroMin.setText("Min: " +jMin+"°");
                jutroMax.setText("Max: "+jMax+"°");
                jutroCisnienie.setText("Ciśnienie: " + jCis + " hPa");
                jutroOpis.setText(jOpis);

                pojutrzeMin.setText("Min: " +pMin+"°");
                pojutrzeMax.setText("Max: "+pMax+"°");
                pojutrzeCisnienie.setText("Ciśnienie: " + pCis+ " hPa");
                pojutrzeOpis.setText(pOpis);

                pozniejMin.setText("Min: " +min+"°");
                pozniejMax.setText("Max: "+max+"°");
                pozniejCisnienie.setText("Ciśnienie: " + cis+ " hPa");
                pozniejOpis.setText(opis);

                Picasso.get().load(jUrl).into(jutroIkona);
                Picasso.get().load(pUrl).into(pojutrzeIkona);
                Picasso.get().load(url).into(pozniejIkona);


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
        Intent intent = new Intent(DlugoterminowaPogoda.this, Pogoda.class);
        intent.putExtra("email", email);
        intent.putExtra("login", login);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(DlugoterminowaPogoda.this, Pogoda.class);
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