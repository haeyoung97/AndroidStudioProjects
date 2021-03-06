package org.techtowm.recyclerviewexample;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Diary> diaryArrayList = new ArrayList<>();
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        addDummy();
        setRecyclerView();
        setListeners();
    }

    private void init(){
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void addDummy(){
        diaryArrayList.add(new Diary("오늘도", "2017-04.10 03:00"));
        diaryArrayList.add(new Diary("개미는", "2017-03.31 03:00"));
        diaryArrayList.add(new Diary("열심히", "2017-09.30 03:00"));
        diaryArrayList.add(new Diary("일을 하네", "2017-3.7 03:00"));
    }

    private void setRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getApplicationContext(), diaryArrayList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void setListeners(){
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), WriteActivity.class));
            }
        });
    }
}
