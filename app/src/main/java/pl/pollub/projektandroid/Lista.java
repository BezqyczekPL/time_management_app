package pl.pollub.projektandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lista extends AppCompatActivity
{
    private ListaAdapter mAdapter;
    int grupa_id;
    String email, login;
    private List<Lista_Element> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.lista_zakupow);
        super.onCreate(savedInstanceState);
        lista = new ArrayList<>();

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
        RecyclerView recyclerView = findViewById(R.id.Recycler_czlonkowie);
        mAdapter = new ListaAdapter(getApplicationContext(), lista);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ApiInterfaceLista apiService = ApiClient.getRetrofit().create(ApiInterfaceLista.class);
        Call<List<Lista_Element>> call = apiService.getProdukty(login);

        call.enqueue(new Callback<List<Lista_Element>>() {
            @Override
            public void onResponse(Call<List<Lista_Element>> call, Response<List<Lista_Element>> response) {
                lista = response.body();

                Log.d("TAG","Response = " + lista);

                mAdapter.setElementList(lista);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<List<Lista_Element>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }

        });


        FloatingActionButton fab = findViewById(R.id.przycisk);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intencja = new Intent(Lista.this, DodajDoListy.class);
                intencja.putExtra("login", login);
                intencja.putExtra("email", email);
                startActivity(intencja);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Lista.this, MainActivity.class);
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
        Intent intent = new Intent(Lista.this, MainActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("login", login);
        startActivity(intent);
        finish();
    }
}
