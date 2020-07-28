package com.example.smartagriculture;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView ldr,temp,hum,soil;
    Button btn;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ldr = (TextView)findViewById(R.id.ldr);
        temp = (TextView)findViewById(R.id.temp);
        hum = (TextView)findViewById(R.id.hum);
        soil = (TextView)findViewById(R.id.soil);

        btn = (Button)findViewById(R.id.start);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef = FirebaseDatabase.getInstance().getReference().child("users").child("18DCS035").child("sensorData");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String ldr_ = snapshot.child("ldrStatus").getValue().toString();
                        String temp_ = snapshot.child("temp").getValue().toString();
                        String hum_ = snapshot.child("hum").getValue().toString();
                        String soil_ = snapshot.child("soilMoisture").getValue().toString();
                        ldr.setText(ldr_);
                        temp.setText(temp_);
                        hum.setText(hum_);
                        soil.setText(soil_);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }

}
