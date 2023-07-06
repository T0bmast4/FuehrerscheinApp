package dev.tobi.fuehrerscheinapp.utils;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.tobi.fuehrerscheinapp.AddActivity;
import dev.tobi.fuehrerscheinapp.DetailActivity;
import dev.tobi.fuehrerscheinapp.ListActivity;
import dev.tobi.fuehrerscheinapp.R;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ListViewHolder> {

    private Context context;
    private ArrayList drive_id, drive_start, drive_ziel, drive_kilometer, drive_kilometer_stand, drive_datum, drive_zeit, drive_wetter;

    public CustomAdapter(Context context, ArrayList<Integer> drive_id, ArrayList drive_start, ArrayList drive_ziel, ArrayList drive_kilometer, ArrayList drive_kilometer_stand, ArrayList drive_datum, ArrayList drive_zeit, ArrayList drive_wetter) {
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
    public CustomAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ListViewHolder holder, int position) {
        holder.id_txt.setText(String.valueOf(drive_id.get(position)));
        holder.start_txt.setText(String.valueOf(drive_start.get(position)));
        holder.ziel_txt.setText(String.valueOf(drive_ziel.get(position)));
        holder.kilometer_txt.setText(String.valueOf(drive_kilometer.get(position)));
        holder.date_txt.setText(String.valueOf(drive_datum.get(position)));
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("start", String.valueOf(drive_start.get(position)));
                intent.putExtra("ziel", String.valueOf(drive_ziel.get(position)));
                intent.putExtra("kilometer", String.valueOf(drive_kilometer.get(position)));
                intent.putExtra("kilometer_stand", String.valueOf(drive_kilometer_stand.get(position)));
                intent.putExtra("datum", String.valueOf(drive_datum.get(position)));
                intent.putExtra("zeit", String.valueOf(drive_zeit.get(position)));
                intent.putExtra("wetter", String.valueOf(drive_wetter.get(position)));
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
        ConstraintLayout rowLayout;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt = itemView.findViewById(R.id.id_txt);
            start_txt = itemView.findViewById(R.id.start_txt);
            ziel_txt = itemView.findViewById(R.id.ziel_txt);
            kilometer_txt = itemView.findViewById(R.id.kilometer_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            rowLayout = itemView.findViewById(R.id.rowLayout);
        }
    }
}
