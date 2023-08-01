package pl.pollub.projektandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.w3c.dom.Text;

import java.util.List;


public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>
{
    Context context;
    private List<ToDo_Element> mElementList;
    private LayoutInflater mLayoutInflater;
    int wiersz;

    public ToDoAdapter(Context context, List<ToDo_Element> lista)
    {
        this.context = context;
        this.mElementList = lista;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        this.mLayoutInflater = LayoutInflater.from(parent.getContext());
        View wiersz = mLayoutInflater.inflate(R.layout.linijka_todo, null);
        ToDoViewHolder viewHolder = new ToDoViewHolder(wiersz);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position)
    {
        ToDo_Element to_do = mElementList.get(position);
        holder.bindToWordViewHolder(position);
        this.wiersz = position;

        holder.title.setTag(position);
        holder.title.setText(to_do.getWydarzenie());

        holder.deadline_day.setTag(position);
        holder.deadline_day.setText(to_do.getData_deadline());

        holder.deadline_time.setTag(position);
        holder.deadline_time.setText(to_do.getGodzina_deadline());

        holder.zakoncz.setChecked(mElementList.get(position).czyWcisnietoZakoncz());
        holder.zakoncz.setTag(mElementList.get(position));

        holder.now.setTag(position);
        holder.now.setText(String.valueOf(to_do.getNow()));

        holder.login.setTag(position);
        holder.login.setText(to_do.getLogin());

        holder.edytuj.setTag(mElementList.get(position));


        holder.w_trakcie.setTag(mElementList.get(position));
        if(to_do.getNow()==1) {
            holder.w_trakcie.setChecked(true);
            holder.itemView.setBackgroundColor(Color.parseColor("#FF0000"));
        }



        holder.edytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", to_do.getWydarzenie() + " " + to_do.getId());
                Intent intencja = new Intent(context, EdytujToDoList.class);
                intencja.putExtra("id", to_do.getId());
                intencja.putExtra("title", to_do.getWydarzenie());
                intencja.putExtra("login", to_do.getLogin());
                intencja.putExtra("deadline_day", to_do.getData_deadline());
                intencja.putExtra("deadline_time", to_do.getGodzina_deadline());
                intencja.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intencja);
            }
        });

            holder.w_trakcie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox cb = (CheckBox) view;
                    if(cb.isChecked())
                    {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            cb.setChecked(true);
                            String[] field = new String[1];
                            field[0] = "nazwa_wydarzenia";
                            String[] data = new String[1];
                            data[0] = "1";
                            PutData putData = new PutData("http://192.168.1.12/Projekt/updateToDoTeraz1.php?id=" + to_do.getId(), "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    Intent intencja = new Intent(context, ToDoList.class);
                                    intencja.putExtra("login", to_do.getLogin());
                                    intencja.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intencja);
                                }
                            }
                        }
                    });
                    }
                    else
                    {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                cb.setChecked(false);
                                String[] field = new String[1];
                                field[0] = "nazwa_wydarzenia";
                                String[] data = new String[1];
                                data[0] = "1";
                                PutData putData = new PutData("http://192.168.1.12/Projekt/updateToDoTeraz0.php?id=" + to_do.getId(), "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        Intent intencja = new Intent(context, ToDoList.class);
                                        intencja.putExtra("login", to_do.getLogin());
                                        intencja.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intencja);
                                    }
                                }
                            }
                        });
                    }

                    notifyDataSetChanged();
                }
            });

        holder.zakoncz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                CheckBox cb = (CheckBox) v;
                ToDo_Element terminarz_element = (ToDo_Element) cb.getTag();

                terminarz_element.ustawCheckboxZakoncz(cb.isChecked());
                mElementList.get(position).ustawCheckboxZakoncz(cb.isChecked());

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setMessage("Czy jesteś pewny, że wydarzenie się zakończyło (zostało odwołane)?")
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
                                                field[0] = "nazwa_wydarzenia";
                                                String[] data = new String[1];
                                                data[0] = "1";
                                                PutData putData = new PutData("http://192.168.1.12/Projekt/updateToDoZakoncz.php?id="+ to_do.getId(), "POST", field, data);
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

    public void setElementList(List<ToDo_Element> elementList)
    {
        this.mElementList = elementList;
        notifyDataSetChanged();
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView title;
        TextView deadline_day;
        TextView deadline_time;
        TextView login;
        CheckBox zakoncz, w_trakcie, edytuj;
        TextView now;
        View view;
        public ToDoViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.view = itemView;
            title = itemView.findViewById(R.id.wydarzenie);
            deadline_day = itemView.findViewById(R.id.data_deadline);
            deadline_time = itemView.findViewById(R.id.godzina_deadline1);
            zakoncz = itemView.findViewById(R.id.zakoncz_checkbox);
            now = itemView.findViewById(R.id.now);
            login = itemView.findViewById(R.id.id_user);
            w_trakcie = itemView.findViewById(R.id.w_trakcie_checkbox);
            edytuj = itemView.findViewById(R.id.edytuj_checkbox);
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
