package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.Adapter.TabelViewPanenAdapter;
import com.example.myapplication.Model.PanenModel;

import java.util.ArrayList;
import java.util.List;

public class LapPanenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_panen);

        RecyclerView recyclerView = findViewById(R.id.rvTabel);

        TabelViewPanenAdapter adapter = new TabelViewPanenAdapter(getPanenList());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }

    private List getPanenList() {
        List movieList = new ArrayList<>();

        movieList.add(new PanenModel("1","2","3","4","5","6"));

        return movieList;
    }

}
