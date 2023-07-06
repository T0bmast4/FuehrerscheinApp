package dev.tobi.fuehrerscheinapp.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.tobi.fuehrerscheinapp.AddActivity;
import dev.tobi.fuehrerscheinapp.DetailActivity;
import dev.tobi.fuehrerscheinapp.ListActivity;
import dev.tobi.fuehrerscheinapp.MainActivity;
import dev.tobi.fuehrerscheinapp.R;
import dev.tobi.fuehrerscheinapp.mysql.SQLFahrten;

public class CustomAdapterOverview extends RecyclerView.Adapter<CustomAdapterOverview.ListViewHolder> {

    private Context context;
    private ArrayList drive_id, drive_start, drive_ziel, drive_kilometer, drive_kilometer_stand, drive_datum, drive_zeit, drive_wetter;

    public CustomAdapterOverview(Context context, ArrayList<Integer> drive_id, ArrayList<String> drive_start, ArrayList<String> drive_ziel, ArrayList<Integer> drive_kilometer, ArrayList<Integer> drive_kilometer_stand, ArrayList<String> drive_datum, ArrayList<String> drive_zeit, ArrayList<String> drive_wetter) {
        this.context = context;
        this.drive_id = drive_id;
        this.drive_start = drive_start;
        this.drive_ziel = drive_ziel;
        this.drive_kilometer = drive_kilometer;
        this.drive_kilometer_stand = drive_kilometer_stand;
        this.drive_datum = drive_datum;
        this.drive_zeit = drive_zeit;
        this.drive_wetter = drive_wetter;
    }

    @NonNull
    @Override
    public CustomAdapterOverview.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row2, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterOverview.ListViewHolder holder, int position) {
        holder.id_txt.setText(String.valueOf(drive_id.get(position)));
        holder.start_txt.setText(String.valueOf(drive_start.get(position)));
        holder.ziel_txt.setText(String.valueOf(drive_ziel.get(position)));
        holder.addAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLFahrten.addCarDrive(MyApp.getLoggedInUser(),
                        drive_start.get(position).toString(),
                        drive_ziel.get(position).toString(),
                        Integer.parseInt(drive_kilometer.get(position).toString()),
                        Integer.parseInt(drive_kilometer_stand.get(position).toString()),
                        drive_zeit.get(position).toString(), drive_wetter.get(position).toString());

                Intent intent = new Intent(context, AddActivity.class);
                intent.putExtra("start", String.valueOf(drive_start.get(position)));
                intent.putExtra("ziel", String.valueOf(drive_ziel.get(position)));
                intent.putExtra("weather", String.valueOf(drive_wetter.get(position)));
                intent.putExtra("zeit", String.valueOf(drive_zeit.get(position)));
                intent.putExtra("kilometer", String.valueOf(drive_kilometer_stand.get(position)));
                context.startActivity(intent);
            }
        });

        holder.returnDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddActivity.class);
                intent.putExtra("start", String.valueOf(drive_ziel.get(position)));
                intent.putExtra("ziel", String.valueOf(drive_start.get(position)));
                intent.putExtra("weather", String.valueOf(drive_wetter.get(position)));
                intent.putExtra("zeit", String.valueOf(drive_zeit.get(position)));
                intent.putExtra("kilometer", String.valueOf(drive_kilometer_stand.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drive_id.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        private TextView id_txt, start_txt, ziel_txt, kilometer_txt, date_txt;
        private Button addAgainButton;
        private Button returnDrive;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt = itemView.findViewById(R.id.id_txt);
            start_txt = itemView.findViewById(R.id.start_txt);
            ziel_txt = itemView.findViewById(R.id.ziel_txt);
            kilometer_txt = itemView.findViewById(R.id.kilometer_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            addAgainButton = itemView.findViewById(R.id.addAgainButton);
            returnDrive = itemView.findViewById(R.id.returnDrive);
        }
    }
}
