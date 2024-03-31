package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvrestaurant;
    resAdapter myAdapter;
    ArrayList<restaurant> rests;
    Button additem;
    EditText etLocation, etRating;
    Button btnFilter;
    SharedPreferences sharedPreferences;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        // Load saved restaurant data
        loadRestaurants();

        rvrestaurant.setHasFixedSize(true);
        rvrestaurant.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new resAdapter(rests);
        rvrestaurant.setAdapter(myAdapter);

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddRestaurantDialog();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get filter criteria
                String location = etLocation.getText().toString();
                String rating = etRating.getText().toString();

                // Apply filter
                myAdapter.filter(location, rating);
            }
        });
    }

    private void init() {
        rests = new ArrayList<>();
        sharedPreferences = getSharedPreferences("restaurant_data", MODE_PRIVATE);
        gson = new Gson();

        // Initialize views
        rvrestaurant = findViewById(R.id.rvrestaurant);
        additem = findViewById(R.id.additem);
        etLocation = findViewById(R.id.etLocation);
        etRating = findViewById(R.id.etRating);
        btnFilter = findViewById(R.id.btnFilter);
    }

    private void showAddRestaurantDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Restaurant");

        // Inflate a layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.add_restaurant, null);
        builder.setView(dialogView);

        // Initialize EditText fields in the dialog layout
        final EditText etName = dialogView.findViewById(R.id.etName);
        final EditText etLocation = dialogView.findViewById(R.id.etLocation);
        final EditText etPhone = dialogView.findViewById(R.id.etPhone);
        final EditText etRating = dialogView.findViewById(R.id.etRating);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve input from EditText fields
                String name = etName.getText().toString();
                String location = etLocation.getText().toString();
                String phone = etPhone.getText().toString();
                String rating = etRating.getText().toString();

                // Create a new restaurant object with the retrieved input
                restaurant newRestaurant = new restaurant(name, location, phone, rating);

                // Add the new restaurant to the ArrayList
                rests.add(newRestaurant);

                // Save the updated restaurant data
                saveRestaurants();

                // Notify the adapter of the RecyclerView about the data change
                myAdapter.notifyDataSetChanged();

                // Optionally, display a toast message or perform other actions
                Toast.makeText(MainActivity.this, "Restaurant added", Toast.LENGTH_SHORT).show();

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Show the AlertDialog
        builder.show();
    }

    private void saveRestaurants() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(rests);
        editor.putString("restaurant_list", json);
        editor.apply();
    }

    private void loadRestaurants() {
        String json = sharedPreferences.getString("restaurant_list", null);
        Type type = new TypeToken<ArrayList<restaurant>>() {}.getType();
        if (json != null) {
            rests = gson.fromJson(json, type);
        }
    }
}
