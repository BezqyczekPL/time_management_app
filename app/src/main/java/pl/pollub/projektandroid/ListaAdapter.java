package pl.pollub.projektandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.List;


public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder>
{
    Context context;
    private List<Lista_Element> mElementList;
    private LayoutInflater mLayoutInflater;
    int wiersz;

    public ListaAdapter(Context context, List<Lista_Element> lista)
    {
        this.context = context;
        this.mElementList = lista;
    }

    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        this.mLayoutInflater = LayoutInflater.from(parent.getContext());
        View wiersz = mLayoutInflater.inflate(R.layout.linijka_zakupy, null);
        ListaViewHolder viewHolder = new ListaViewHolder(wiersz);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position)
    {

        Lista_Element lista = mElementList.get(position);
        holder.bindToWordViewHolder(position);
        this.wiersz = position;

        holder.name.setTag(position);
        holder.name.setText(lista.getProdukt());

        holder.checkbox.setChecked(mElementList.get(position).czyWcisnieto());
        holder.checkbox.setTag(mElementList.get(position));

        holder.amount.setTag(position);
        holder.amount.setText(lista.getLiczba());

        holder.edytuj.setTag(position);
        holder.edytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencja = new Intent(context, EdytujLista.class);
                intencja.putExtra("id", lista.getId());
                intencja.putExtra("produkt", lista.getProdukt());
                intencja.putExtra("liczba", lista.getLiczba());
                intencja.putExtra("login", lista.getLogin());
                intencja.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intencja);
            }
        });



        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                CheckBox cb = (CheckBox) v;
                Lista_Element lista_element = (Lista_Element) cb.getTag();

                lista_element.ustawCheckbox(cb.isChecked());
                mElementList.get(position).ustawCheckbox(cb.isChecked());

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setMessage("Czy jesteś pewny, że już to kupiłeś?")
                        .setCancelable(false)
                        .setPositiveButton("Tak",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        mElementList.remove(position);

                                        Handler handler = new Handler(Looper.getMainLooper());
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                String[] field = new String[1];
                                                field[0] = "name";
                                                String[] data = new String[1];
                                                String pozycja = String.valueOf(lista.getId());
                                                data[0] = "1";
                                                PutData putData = new PutData("http://87.246.222.160/Projekt/updateLista.php?id="+pozycja, "POST", field, data);
                                                if (putData.startPut()) {
                                                    if (putData.onComplete()) {}
                                                }
                                            }
                                        });
                                        notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                cb.setChecked(false);
                            }
                        });

                builder.show();

            }
        });
    }

    @Override
    public int getItemCount()
    {
        if (mElementList != null)
        {
            return mElementList.size();
        }
        return 0;
    }

    public void setElementList(List<Lista_Element> elementList)
    {
        this.mElementList = elementList;
        notifyDataSetChanged();
    }

    public class ListaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name;
        TextView amount;
        CheckBox checkbox;
        Button edytuj;
        View view;
        public ListaViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.view = itemView;
            name = itemView.findViewById(R.id.produkt_listy);
            amount = itemView.findViewById(R.id.liczba);
            checkbox = itemView.findViewById(R.id.produkt);
            edytuj = itemView.findViewById(R.id.edytuj_produkt);
            itemView.setOnClickListener(this);
        }

        public void bindToWordViewHolder(int position)
        {

        }

        @Override
        public void onClick(View v)
        {

        }
    }
}
