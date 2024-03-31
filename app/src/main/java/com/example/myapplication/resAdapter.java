package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class resAdapter extends RecyclerView.Adapter<resAdapter.ViewHolder> {

    ArrayList<restaurant> items;
    ArrayList<restaurant> filteredItems; // Add a separate list for filtered items

    public resAdapter(ArrayList<restaurant> list) {
        items = list;
        filteredItems = new ArrayList<>(list); // Initialize filteredItems with all items initially
    }

    @NonNull
    @Override
    public resAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.design, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull resAdapter.ViewHolder holder, int position) {
        restaurant restaurants = filteredItems.get(position); // Use filteredItems instead of items
        holder.tvn.setText(restaurants.getName());
        holder.tvl.setText(restaurants.getLocation());
        holder.tvp.setText(restaurants.getPhone());
        holder.tvr.setText(restaurants.getRating());
    }

    @Override
    public int getItemCount() {
        return filteredItems.size(); // Return the size of filteredItems
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvn, tvl, tvp, tvr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvn = itemView.findViewById(R.id.tvname);
            tvl = itemView.findViewById(R.id.tvlocation);
            tvp = itemView.findViewById(R.id.tvphone);
            tvr = itemView.findViewById(R.id.tvrating);
        }
    }

    public void filter(String location, String rating) {
        filteredItems.clear(); // Clear the filteredItems list

        for (restaurant restaurant : items) {
            // Check if the restaurant matches the filter criteria
            if ((location.isEmpty() || restaurant.getLocation().toLowerCase().contains(location.toLowerCase())) &&
                    (rating.isEmpty() || restaurant.getRating().toLowerCase().contains(rating.toLowerCase()))) {
                filteredItems.add(restaurant); // Add the restaurant to filteredItems if it matches the criteria
            }
        }

        notifyDataSetChanged(); // Notify adapter of data change
    }
}




