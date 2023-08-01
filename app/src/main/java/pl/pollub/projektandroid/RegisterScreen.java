package pl.pollub.projektandroid;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterScreen extends AppCompatActivity
{
    EditText haslo, email, imie, nazwisko, data, login;
    Button rejestracja;
    int rok, miesiac, dzien;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.rejestracja);
        super.onCreate(savedInstanceState);
        email = findViewById(R.id.pole_email);
        haslo = findViewById(R.id.pole_haslo);
        login = findViewById(R.id.pole_login);
        imie = findViewById(R.id.pole_imie);
        nazwisko = findViewById(R.id.pole_nazwisko);
        data = findViewById(R.id.pole_data);
        Pattern imie_nazwisko_pattern = Pattern.compile("^[a-zA-Ząćęńółśźż]+$");
        Pattern login_pattern = Pattern.compile("^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{0,19}$");
        Pattern email_pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");



        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                rok = year;
                miesiac = monthOfYear;
                miesiac = miesiac+1;
                dzien = dayOfMonth;
                updateLabel();
            }

            private void updateLabel()
            {
                String myFormat = "dd.MM.yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                data.setText(sdf.format(myCalendar.getTime()));
            }

        };


        rejestracja = findViewById(R.id.przycisk_rejestracja);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterScreen.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        rejestracja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imie1, nazwisko1, haslo1, login1, email1, data1, str = null;
                Date date;

                imie1 = imie.getText().toString();

                nazwisko1 = nazwisko.getText().toString();

                haslo1 = haslo.getText().toString();
                email1 = email.getText().toString();
                login1 = login.getText().toString();
                data1 = data.getText().toString();
                Matcher imie_matcher = imie_nazwisko_pattern.matcher(imie1);
                Matcher nazwisko_matcher = imie_nazwisko_pattern.matcher(nazwisko1);
                Matcher login_matcher = login_pattern.matcher(login1);
                Matcher email_matcher = email_pattern.matcher(email1);
                if (!imie1.equals("") && !nazwisko1.equals("") && !haslo1.equals("") && !email1.equals("") && !data1.equals("")) {
                    LocalDate data_urodzenia = LocalDate.of(rok, miesiac, dzien);
                    LocalDate dzisiaj = LocalDate.now();
                    if ((imie1.length() < 3)) {
                        imie.setError("Wpisane imię jest za krótkie");
                        Toast.makeText(getApplicationContext(), "Wpisane imię jest za krótkie", Toast.LENGTH_SHORT).show();
                    } else if ((imie1.length() > 45)) {
                        imie.setError("Wpisane imię jest za długie");
                        Toast.makeText(getApplicationContext(), "Wpisane imię jest za długie", Toast.LENGTH_SHORT).show();
                    } else if (!imie_matcher.find()) {
                        imie.setError("W imieniu podano niewlasciwe znaki");
                        Toast.makeText(getApplicationContext(), "W imieniu podano niewlasciwe znaki", Toast.LENGTH_SHORT).show();
                    }else if ((nazwisko1.length() < 3)) {
                        nazwisko.setError("Wpisane nazwisko jest za krótkie");
                        Toast.makeText(getApplicationContext(), "Wpisane nazwisko jest za krótkie", Toast.LENGTH_SHORT).show();
                    }else if ((nazwisko1.length() > 45)) {
                        nazwisko.setError("Wpisane nazwisko jest za długie");
                        Toast.makeText(getApplicationContext(), "Wpisane nazwisko jest za długie", Toast.LENGTH_SHORT).show();
                    }else if (!nazwisko_matcher.find()) {
                        nazwisko.setError("W nazwisku podano niewlasciwe wartosci");
                        Toast.makeText(getApplicationContext(), "W nazwisku podano niewlasciwe wartosci", Toast.LENGTH_SHORT).show();
                    }else if (haslo1.length() < 8) {
                        haslo.setError("Wpisane hasło jest za krótkie (min. 8 znaków)");
                        Toast.makeText(getApplicationContext(), "Wpisane hasło jest za krótkie (min. 8 znaków)", Toast.LENGTH_SHORT).show();
                    } else if (!login_matcher.find()) {
                        login.setError("Niepoprawny login");
                        Toast.makeText(getApplicationContext(), "Niepoprawny login", Toast.LENGTH_SHORT).show();
                    }
                    else if (!email_matcher.find())
                    {
                        email.setError("Podano niewlasciwy email");
                        Toast.makeText(getApplicationContext(), "Podano niewlasciwy email", Toast.LENGTH_SHORT).show();
                    } else if (data_urodzenia.isAfter(dzisiaj.minusYears(10)))
                    {
                        data.setError("Jesteś za młody (musisz mieć minimum 10 lat)");
                        Toast.makeText(getApplicationContext(), "Jestes za mlody (musisz mieć minimum 10 lat)", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        String inputPattern = "dd.MM.yyyy";
                        String outputPattern = "yyyy-MM-dd";

                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);


                        try {
                            date = inputFormat.parse(data1);
                            str = outputFormat.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        imie1 = imie1.substring(0, 1).toUpperCase() + imie1.substring(1).toLowerCase();
                        nazwisko1 = nazwisko1.substring(0, 1).toUpperCase() + nazwisko1.substring(1).toLowerCase();
                        Handler handler = new Handler(Looper.getMainLooper());
                        String finalStr = str;
                        String finalNazwisko = nazwisko1;
                        String finalImie = imie1;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[6];
                                field[0] = "email";
                                field[1] = "password";
                                field[2] = "login";
                                field[3] = "name";
                                field[4] = "surname";
                                field[5] = "date_of_birth";

                                //Creating array for data
                                String[] data = new String[6];
                                data[0] = email1;
                                data[1] = haslo1;
                                data[2] = login1;
                                data[3] = finalImie;
                                data[4] = finalNazwisko;
                                data[5] = finalStr;

                                PutData putData = new PutData("http://192.168.1.12/Projekt/rejestracja.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if (result.equals("Udalo sie zarejestrowac!")) {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
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
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie wprowadziłeś czegoś", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
