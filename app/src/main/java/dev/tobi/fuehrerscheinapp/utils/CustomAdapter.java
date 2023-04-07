package dev.tobi.fuehrerscheinapp.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.DriveViewHolder> {

    @NonNull
    @Override
    public CustomAdapter.DriveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.DriveViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DriveViewHolder extends RecyclerView.ViewHolder{
        public DriveViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
