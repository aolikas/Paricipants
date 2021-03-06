package com.example.paricipants;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.paricipants.adapter.ParticipantAdapter;
import com.example.paricipants.database.AppDatabase;
import com.example.paricipants.database.entity.Participant;
import com.example.paricipants.viewModel.ParticipantViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity  extends AppCompatLifeCycleActivity implements View.OnLongClickListener, View.OnClickListener {

    private ParticipantViewModel viewModel;
    private ParticipantAdapter participantViewAdapter;
    private RecyclerView recyclerView;

    private Spinner spinner;
    private String[] genderClass = {
            "Male",
            "Female"};
    ArrayList<String> spinnerList = new ArrayList<>(Arrays.asList(genderClass));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        participantViewAdapter = new ParticipantAdapter(new ArrayList<Participant>(), this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(participantViewAdapter);

        viewModel = ViewModelProviders.of(this).get(ParticipantViewModel.class);

        viewModel.getParticipants().observe(MainActivity.this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(@Nullable List<Participant> participants) {
                participantViewAdapter.addParticipants(participants);
            }
        });




    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppDatabase.destroyInstance();
    }

    @Override
    public boolean onLongClick(View v) {
        Participant participant = (Participant) v.getTag();
        viewModel.deleteParticipant(participant);
        return true;
    }

    @Override
    public void onClick(View v) {
        Participant participant = (Participant) v.getTag();
        Intent i = new Intent(MainActivity.this, UpdateActivity.class);
        i.putExtra("itemId",participant.partId);
        startActivity(i);
    }

}
