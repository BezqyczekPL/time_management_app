package pl.pollub.projektandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormatSymbols;

public class Kalendarz extends AppCompatActivity
{
    String Month,Day, email, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalendarz);

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

        CalendarView calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
                month=month+1;

                if((month > 0 && month < 10) || (day > 0 && day < 10))
                {
                    Month="0"+month;
                    Day="0"+day;

                }
                else
                {
                    Month= String.valueOf(month);
                    Day = String.valueOf(day);
                }
                String data = year + "-" + month + "-" + day;
                Intent intencja = new Intent(Kalendarz.this, Terminarz.class);
                intencja.putExtra("email", email);
                intencja.putExtra("login", login);
                intencja.putExtra("data", data);
                startActivity(intencja);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Kalendarz.this, MainActivity.class);
                intent.putExtra("login", login);
                intent.putExtra("email", email);
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
        Intent intent = new Intent(Kalendarz.this, MainActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("login", login);
        startActivity(intent);
        finish();
    }
}
