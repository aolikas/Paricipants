package com.example.paricipants;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paricipants.database.Participant;
import com.example.paricipants.viewModel.UpdateParticipantViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Date date;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    private TextInputEditText nameEditText, countryEditText;

    private ImageView calendarImage;

    private Spinner spinner;

    private String[] genderClass = {
            "Male",
            "Female"};

    public ArrayList<String> spinnerList = new ArrayList<>(Arrays.asList(genderClass));


    private Button addButton;
    private int itemId = 0;

    private UpdateParticipantViewModel updateParticipantViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = findViewById(R.id.toolbar);


        nameEditText = findViewById(R.id.et_name);
        countryEditText = findViewById(R.id.et_country);

        addButton = findViewById(R.id.btn_save);
        calendarImage = findViewById(R.id.iv_dob);


        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        calendar = Calendar.getInstance();
        updateParticipantViewModel = ViewModelProviders.of(this).get(UpdateParticipantViewModel.class);

        datePickerDialog = new DatePickerDialog(this, UpdateActivity.this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        Intent i = getIntent();
        if (i != null) {
            Participant participant = updateParticipantViewModel.readParticipant(i.getIntExtra("itemId",0));
            nameEditText.setText(participant.getPartName());
            countryEditText.setText(participant.getPartCountry());
            itemId = participant.getPartId();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.GERMANY);
            try {
                calendar.setTime(sdf.parse(participant.getPartBirthday().toLocaleString().substring(0, 15)));// all done
                datePickerDialog = new DatePickerDialog(this,
                        UpdateActivity.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameEditText.getText() == null || countryEditText.getText() == null || date == null)
                    Toast.makeText(UpdateActivity.this, "Missing fields", Toast.LENGTH_SHORT).show();
                else {
                    updateParticipantViewModel.updateParticipant(new Participant(itemId,
                            nameEditText.getText().toString(),
                            countryEditText.getText().toString(),
                            date,
                            spinner.getSelectedItem().toString()
                    ));
                    finish();
                }
            }
        });

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
