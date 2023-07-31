package pl.pollub.projektandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToDoList extends AppCompatActivity {
    private ToDoAdapter mAdapter;
    String email, login;
    private List<ToDo_Element> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.todo);
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

        RecyclerView recyclerView = findViewById(R.id.Terminarz_Recycler);

        mAdapter = new ToDoAdapter(getApplicationContext(), lista);

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
        ApiInterfaceToDo apiService = ApiClient.getRetrofit().create(ApiInterfaceToDo.class);
        Call<List<ToDo_Element>> call = apiService.getWydarzenia(login);

        call.enqueue(new Callback<List<ToDo_Element>>() {
            @Override
            public void onResponse(Call<List<ToDo_Element>> call, Response<List<ToDo_Element>> response) {
                lista = response.body();

                Log.d("TAG","Response = " + lista);

                mAdapter.setElementList(lista);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<List<ToDo_Element>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }

        });

        FloatingActionButton fab = findViewById(R.id.przycisk_dodaj_do_terminarza);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intencja = new Intent(ToDoList.this, DodajDoToDoList.class);
                intencja.putExtra("email", email);
                intencja.putExtra("login", login);
                startActivity(intencja);
                finish();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ToDoList.this, MainActivity.class);
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
        Intent intent = new Intent(ToDoList.this, MainActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent newData) {

        super.onActivityResult(requestCode, resultCode, newData);

    }
}