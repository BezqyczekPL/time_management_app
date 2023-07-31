package pl.pollub.projektandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Terminarz extends AppCompatActivity
{
    private TerminarzAdapter mAdapter;
    String login, email, data;
    Date date;

    private List<Terminarz_Element> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminarz);
        TextView data1 = findViewById(R.id.data_terminarz);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                login = null;
                email = null;
                data = null;

            } else {
                login = extras.getString("login");
                email = extras.getString("email");
                data = extras.getString("data");
            }
        } else {
            login = savedInstanceState.getString("login");
            email = savedInstanceState.getString("email");
            data = savedInstanceState.getString("data");
        }
        RecyclerView recyclerView = findViewById(R.id.Terminarz_Recycler);
        mAdapter = new TerminarzAdapter(getApplicationContext(), lista);


        DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DateFormat targetFormat = new SimpleDateFormat("dd.MM.yyyy");

        try {
            date = sourceFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String data2 = targetFormat.format(date);
        data1.setText(data2);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        DividerItemDecoration divider = new DividerItemDecoration(
                recyclerView.getContext(), DividerItemDecoration.VERTICAL
        );
        divider.setDrawable(
                ContextCompat.getDrawable(getBaseContext(), R.drawable.line_divider)
        );
        recyclerView.addItemDecoration(divider);

        ApiInterfaceTerminarz apiService = ApiClient.getRetrofit().create(ApiInterfaceTerminarz.class);
        Call<List<Terminarz_Element>> call = apiService.getWydarzenia(login,data);

        call.enqueue(new Callback<List<Terminarz_Element>>() {
            @Override
            public void onResponse(Call<List<Terminarz_Element>> call, Response<List<Terminarz_Element>> response) {
                lista = response.body();

                Log.d("TAG","Response = " + lista);

                mAdapter.setElementList(lista);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<List<Terminarz_Element>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }

        });

        FloatingActionButton fab = findViewById(R.id.przycisk_dodaj);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intencja = new Intent(Terminarz.this, DodajDoTerminarza.class);
                intencja.putExtra("email", email);
                intencja.putExtra("login", login);
                intencja.putExtra("data",data);
                startActivity(intencja);
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Terminarz.this, Kalendarz.class);
                intent.putExtra("email", email);
                intent.putExtra("login", login);
                intent.putExtra("data",data);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBackPressed() {
        Log.d("Tag", "Kliknięto");
        Intent intent = new Intent(Terminarz.this, Kalendarz.class);
        intent.putExtra("email", email);
        intent.putExtra("login", login);
        intent.putExtra("data",data);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putString("email", email);
        outState.putString("login", login);
        outState.putString("data", data);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        email = savedInstanceState.getString("email");
        login = savedInstanceState.getString("login");
        data = savedInstanceState.getString("data");
    }


}
