package com.example.studentdatabase;

import android.Manifest;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Button addButton,searchButton,updateButton,deleteButton,smsButton;
    Spinner spinnerYear,spinnerBatch,spinnerBranch;
    String branch,batch,year;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

        initialize();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addStudent = new Intent(MainActivity.this,AddStudent.class);
                addStudent.putExtra("year",year);
                addStudent.putExtra("batch",batch);
                addStudent.putExtra("branch",branch);
                startActivity(addStudent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchStudent = new Intent(MainActivity.this,SearchStudent.class);
                searchStudent.putExtra("year",year);
                searchStudent.putExtra("batch",batch);
                searchStudent.putExtra("branch",branch);
                startActivity(searchStudent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateStudent = new Intent(MainActivity.this,UpdateStudent.class);
                updateStudent.putExtra("year",year);
                updateStudent.putExtra("batch",batch);
                updateStudent.putExtra("branch",branch);
                startActivity(updateStudent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deleteStudent = new Intent(MainActivity.this,DeleteStudent.class);
                deleteStudent.putExtra("year",year);
                deleteStudent.putExtra("batch",batch);
                deleteStudent.putExtra("branch",branch);
                startActivity(deleteStudent);
            }
        });

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sms = new Intent(MainActivity.this,SMSActivity.class);
                sms.putExtra("year",year);
                sms.putExtra("batch",batch);
                sms.putExtra("branch",branch);
                startActivity(sms);
            }
        });
    }

    public void initialize(){
        addButton = findViewById(R.id.addButton);
        searchButton = findViewById(R.id.searchButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        smsButton = findViewById(R.id.sendSMSButton);

        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerBatch = findViewById(R.id.spinnerBatch);
        spinnerBranch = findViewById(R.id.spinnerBranch);

        spinnerBranch = findViewById(R.id.spinnerBranch);

        ArrayAdapter<CharSequence> adapterBranch = ArrayAdapter.createFromResource(this,R.array.Branch, R.layout.spinner_item);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(adapterBranch);
        spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branch = spinnerBranch.getItemAtPosition(position).toString().trim().toUpperCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerBatch = findViewById(R.id.spinnerBatch);
        ArrayAdapter<CharSequence> adapterBatch = ArrayAdapter.createFromResource(this,R.array.Batch, R.layout.spinner_item);
        adapterBatch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBatch.setAdapter(adapterBatch);
        spinnerBatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                batch = spinnerBatch.getItemAtPosition(position).toString().toUpperCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerYear = findViewById(R.id.spinnerYear);
        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this,R.array.Year, R.layout.spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = spinnerYear.getItemAtPosition(position).toString().trim().toUpperCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sendSMSMessage();
    }

    private void sendSMSMessage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}
