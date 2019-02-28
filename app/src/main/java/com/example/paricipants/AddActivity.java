package com.example.paricipants;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paricipants.database.entity.Participant;
import com.example.paricipants.viewModel.ParticipantViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Date date;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    private TextInputEditText nameEditText, countryEditText;
    ImageView calendarImage;


    private Spinner spinner;

    private String[] genderClass = {
            "Male",
            "Female"};

    public ArrayList<String> spinnerList = new ArrayList<>(Arrays.asList(genderClass));

    private Button addButton;


    private ParticipantViewModel addParticipantViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = findViewById(R.id.toolbar);


        nameEditText = findViewById(R.id.et_name);
        countryEditText = findViewById(R.id.et_country);

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        calendar = Calendar.getInstance();

        addParticipantViewModel = ViewModelProviders.of(this).
                get(ParticipantViewModel.class);


        datePickerDialog = new DatePickerDialog(this, AddActivity.this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));



        addButton = findViewById(R.id.btn_save);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.getText() == null || countryEditText.getText() == null || date == null)
                    Toast.makeText(AddActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                else {
                    addParticipantViewModel.addParticipant(new Participant(0,
                            nameEditText.getText().toString(),
                            countryEditText.getText().toString(),
                            date,
                            spinner.getSelectedItem().toString()));
                    finish();
                }
            }

        });

        calendarImage = findViewById(R.id.iv_dob);

        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = calendar.getTime();
    }
}
