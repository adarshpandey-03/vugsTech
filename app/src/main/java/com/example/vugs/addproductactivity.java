package com.example.vugs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addproductactivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    double amountperunit;

    double totalweight;
    String type;

    Spinner producttypespinner;
    TextView amountperunitview;
    TextView totalamountview;
    EditText unit;

    Button btn_calculate;
    Button btn_addproduct;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productadd);

        producttypespinner = (Spinner) findViewById(R.id.producttypespinner);
        amountperunitview = (TextView) findViewById(R.id.amountperunit);
        totalamountview = (TextView) findViewById(R.id.totalAmount);
        unit = (EditText) findViewById(R.id.unit);
        btn_calculate = (Button) findViewById(R.id.btnCalculate);
        btn_addproduct = (Button) findViewById(R.id.btnaddproduct);
        databaseReference = FirebaseDatabase.getInstance().getReference("Product");
        firebaseAuth = FirebaseAuth.getInstance();

        producttypespinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(addproductactivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.garbageType));

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        producttypespinner.setAdapter(spinnerAdapter);

        btn_calculate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                totalweight = Double.parseDouble(unit.getText().toString().trim());
                totalamountview.setText(Double.toString(amountperunit*totalweight));
            }
        });


        btn_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = "Available";

                RequestData rqdata = new RequestData(String.valueOf(totalweight),String.valueOf(amountperunit*totalweight),type, status);

                FirebaseDatabase.getInstance().getReference("Request")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(rqdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(addproductactivity.this, "Your Request is send",Toast.LENGTH_LONG);
                        startActivity(new Intent(getApplicationContext(),CustomerHomeActivity.class));
                    }


                });
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type = adapterView.getItemAtPosition(i).toString().trim().toLowerCase();
        if(type.equals("Oscilloscope")){
            amountperunit = 4500.00;
            amountperunitview.setText(Double.toString(amountperunit));
        }
        if(type.equals("Signal Generator")){
            amountperunit = 7000.00;
            amountperunitview.setText(Double.toString(amountperunit));
        }
        if(type.equals("Multimeter")){
            amountperunit = 1000.00;
            amountperunitview.setText(Double.toString(amountperunit));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(addproductactivity.this, "Please Fill Required Details",Toast.LENGTH_LONG);
    }
}
