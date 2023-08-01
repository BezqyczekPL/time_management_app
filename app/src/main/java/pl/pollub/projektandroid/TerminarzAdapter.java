package pl.pollub.projektandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
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

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TerminarzAdapter extends RecyclerView.Adapter<TerminarzAdapter.TerminarzViewHolder>
{
    Context context;
    private List<Terminarz_Element> mElementList;
    private LayoutInflater mLayoutInflater;
    int wiersz;

    public TerminarzAdapter(Context context, List<Terminarz_Element> lista)
    {
        this.context = context;
        this.mElementList = lista;
    }

    @NonNull
    @Override
    public TerminarzViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        this.mLayoutInflater = LayoutInflater.from(parent.getContext());
        View wiersz = mLayoutInflater.inflate(R.layout.linijka_terminarz, null);
        TerminarzViewHolder viewHolder = new TerminarzViewHolder(wiersz);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TerminarzViewHolder holder, int position)
    {

        Terminarz_Element lista = mElementList.get(position);
        holder.bindToWordViewHolder(position);
        this.wiersz = position;

        holder.nazwa.setTag(position);
        holder.nazwa.setText(lista.getName());

        holder.rozpoczecie.setTag(position);
        holder.rozpoczecie.setText("Rozpoczęcie: " + lista.getStart_time());

        holder.zakonczenie.setTag(position);
        holder.zakonczenie.setText("Zakończenie: "+lista.getEnd_time());

        holder.czas.setTag(position);
        holder.czas.setText("Czas trwania: "+lista.getTime()+" h");

        holder.status.setTag(position);
        holder.status.setText(String.valueOf(lista.getStatus()));

        holder.login.setTag(position);
        holder.login.setText(lista.getLogin());

        holder.data.setTag(position);
        holder.data.setText(lista.getDate());

        if(lista.getStatus() == 1)
        {
            holder.nazwa.setPaintFlags(holder.nazwa.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.rozpoczecie.setPaintFlags(holder.rozpoczecie.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.zakonczenie.setPaintFlags(holder.zakonczenie.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.czas.setPaintFlags(holder.czas.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.przycisk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Co chcesz zrobić?");
                builder.setItems(new String[]{"Edytuj wydarzenie", "Zakończ wydarzenie", "Powtórz wydarzenie dzisiaj"}, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0)
                        {
                            Intent intencja = new Intent(context, EdytujTerminarz.class);
                            intencja.putExtra("id", lista.getId());
                            intencja.putExtra("nazwa", lista.getName());
                            intencja.putExtra("login", lista.getLogin());
                            intencja.putExtra("czas", lista.getTime());
                            intencja.putExtra("data", lista.getDate());
                            intencja.putExtra("rozpoczecie", lista.getStart_time());
                            intencja.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intencja);
                        }
                        else if (which == 1)
                        {

                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String[] field = new String[1];
                                    field[0] = "nazwa_wydarzenia";
                                    String[] data = new String[1];
                                    data[0] = "1";
                                    PutData putData = new PutData("http://192.168.1.12/Projekt/updateTerminarz1.php?id=" + lista.getId(), "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            Intent intencja = new Intent(context, Terminarz.class);
                                            intencja.putExtra("login", lista.getLogin());
                                            intencja.putExtra("data", lista.getDate());
                                            intencja.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intencja);
                                        }
                                    }
                                }
                            });
                        }
                        else
                        {
                            Intent intencja = new Intent(context, DodajDoTerminarzaGotowiec.class);
                            intencja.putExtra("nazwa", lista.getName());
                            intencja.putExtra("login", lista.getLogin());
                            intencja.putExtra("czas", lista.getTime());
                            LocalDate dateObj = LocalDate.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            String date = dateObj.format(formatter);
                            intencja.putExtra("data", date);
                            intencja.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intencja);
                        }
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

    public void setElementList(List<Terminarz_Element> elementList)
    {
        this.mElementList = elementList;
        notifyDataSetChanged();
    }

    public class TerminarzViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView nazwa;
        TextView rozpoczecie;
        TextView zakonczenie;
        TextView czas;
        TextView status;
        TextView login;
        TextView data;
        Button przycisk;
        View view;
        public TerminarzViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.view = itemView;
            nazwa = itemView.findViewById(R.id.tytul);
            rozpoczecie = itemView.findViewById(R.id.godzina_zaczecia);
            zakonczenie = itemView.findViewById(R.id.godzina_zakonczenia);
            czas = itemView.findViewById(R.id.czas);
            przycisk = itemView.findViewById(R.id.przycisk_terminarz);
            status = itemView.findViewById(R.id.status_wyd);
            login = itemView.findViewById(R.id.login1);
            data = itemView.findViewById(R.id.data_wyd);
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
