package com.example.candroid_diagnostics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ECUrecyclerViewAdapter extends RecyclerView.Adapter<ECUrecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<sensorModel> sensorModels;

    public ECUrecyclerViewAdapter(Context context, ArrayList<sensorModel> sensorModels){
        this.context = context;
        this.sensorModels = sensorModels;
    }

    @NonNull
    @Override
    public ECUrecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //where you inflate layout (giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row,parent, false);

        return new ECUrecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ECUrecyclerViewAdapter.MyViewHolder holder, int position) {
        //based on the position of the recycler view
        //assign values to the view created in the recyclerviewrow layout file

        holder.sensorName.setText(sensorModels.get(position).getSensorName());
        holder.data1.setText(sensorModels.get(position).getDataValue1());
        holder.data2.setText(sensorModels.get(position).getDataValue2());
        holder.imageView.setImageResource(sensorModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        //recycler view gets the number of items that is the developer wants to display
        return sensorModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //bit like the onCreate method fr

        ImageView imageView;
        TextView sensorName, data1, data2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageDisplayView1);
            sensorName = itemView.findViewById(R.id.sensorNameTextView);
            data1 = itemView.findViewById(R.id.dataDisplayTextView1);
            data2 = itemView.findViewById(R.id.dataDisplayTextView2);
        }
    }
}
