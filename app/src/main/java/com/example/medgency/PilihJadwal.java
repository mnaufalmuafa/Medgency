package com.example.medgency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class PilihJadwal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_jadwal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ToolbarPilihJadwal);

        TextView toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText("Tiket Reservasi");
        setSupportActionBar(toolbar);
    }
}
