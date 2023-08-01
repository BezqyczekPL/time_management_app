package pl.pollub.projektandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;



public class LoginScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.loginscreen);
        super.onCreate(savedInstanceState);
        Button rejestracja = findViewById(R.id.Zarejestruj);
        Button logowanie = findViewById(R.id.zaloguj);
        EditText email = findViewById(R.id.email_logowanie);
        EditText haslo = findViewById(R.id.haslo_logowanie);
        //getSupportActionBar().setHomeButtonEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setDisplayShowHomeEnabled(false);

        rejestracja.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v)
            {
                Intent intencja = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intencja);
            }
        });
        logowanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  haslo1, email1;

                haslo1 = haslo.getText().toString();
                email1 = email.getText().toString();

                Handler handler = new Handler(Looper.getMainLooper());
                if (!email1.equals("") && !haslo1.equals(""))
                {
                    handler.post(new Runnable() {
                        @Override
                       public void run() {
                            String[] field = new String[2];
                            field[0] = "email";
                            field[1] = "password";
                            String[] data = new String[2];
                            data[0] = email1;
                           data[1] = haslo1;
                            PutData putData = new PutData("http://192.168.1.12/Projekt/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Udalo sie zalogowac")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtra("email", data[0]);
                                        startActivity(intent);
                                        haslo.setText("");
                                        email.setText("");
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie wprowadziłeś czegoś", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}
