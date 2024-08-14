package com.example.mybag;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InputDataActivity extends AppCompatActivity {

    //Deklarasi variable
    Button btinputedit;
    EditText nama, umur, moto;

    DatabaseHelper dbmaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Get ID for variables
        btinputedit = findViewById(R.id.btsave);
        nama = findViewById(R.id.etnama);
        umur = findViewById(R.id.etumur);
        moto = findViewById(R.id.etmoto);

        dbmaster = new DatabaseHelper(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        if (id != null) {
            Cursor data = dbmaster.getAllData();
            if (data.moveToFirst()) {
                do {
                    if (data.getString(0).equals(id)) {
                        nama.setText(data.getString(1));
                        umur.setText(data.getString(2));
                        moto.setText(data.getString(3));
                        btinputedit.setText("Update Data");
                        break;
                    }
                } while (data.moveToNext());
            }

            btinputedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isUpdated = dbmaster.updateData(id, nama.getText().toString(), Integer.parseInt(umur.getText().toString()),moto.getText().toString());
                    if (isUpdated)
                        Toast.makeText(InputDataActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(InputDataActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            btinputedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = dbmaster.insertData(nama.getText().toString(), Integer.parseInt(umur.getText().toString()),moto.getText().toString());
                    if (isInserted)
                        Toast.makeText(InputDataActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(InputDataActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}