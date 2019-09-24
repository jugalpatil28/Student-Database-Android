package com.example.studentdatabase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SMSActivity extends AppCompatActivity {

    FirebaseFirestore dataBase;

    private EditText editTextGrNo,editTextSMS;
    private CheckBox checkBoxStudent,checkBoxFather,checkBoxMother;
    private String branch,year,batch;
    private String studentMobileNo,fatherMobileNo,motherMobileNo;
    private Button buttonSendSMS;
    private SmsManager smsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        smsManager = SmsManager.getDefault();

        FirebaseApp.initializeApp(this);

        initialize();
    }

    public void initialize(){

        Intent intent = getIntent();
        branch = intent.getStringExtra("branch");
        batch = intent.getStringExtra("batch");
        year = intent.getStringExtra("year");

        dataBase = FirebaseFirestore.getInstance();

        editTextGrNo = findViewById(R.id.editTextGrNo);
        editTextSMS = findViewById(R.id.editTextSMS);
        checkBoxStudent = findViewById(R.id.checkBoxStudent);
        checkBoxFather = findViewById(R.id.checkBoxFather);
        checkBoxMother = findViewById(R.id.checkBoxMother);
        buttonSendSMS  =findViewById(R.id.buttonSendSMS);

        buttonSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchStudent(editTextGrNo.getText().toString().toUpperCase());
            }
        });
    }

    public void searchStudent(String value) {
        if(value!=null)
        {
            final DocumentReference documentReference = dataBase.collection(branch+year+batch).document(value);
            Log.d("gr", "searchStudent: " + value + " " + documentReference.get());
            documentReference.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                Student student =  document.toObject(Student.class);
//                                Log.d("student", "onComplete: " + student.getPersonalInfo().get("grNo") + student.getPersonalInfo().get("firstName"));
                                if(student!=null) {
                                    studentMobileNo = student.getPersonalInfo().get("mobile");
                                    fatherMobileNo = student.getFather().get("mobileNo");
                                    motherMobileNo = student.getMother().get("mobileNo");

                                    if(checkBoxStudent.isChecked()){
                                        smsManager.sendTextMessage(studentMobileNo, null, editTextSMS.getText().toString(), null, null);
                                    }
                                    if(checkBoxFather.isChecked()){
                                        smsManager.sendTextMessage(fatherMobileNo, null, editTextSMS.getText().toString(), null, null);
                                    }
                                    if(checkBoxMother.isChecked()){
                                        smsManager.sendTextMessage(motherMobileNo, null, editTextSMS.getText().toString(), null, null);
                                    }

                                    Toast.makeText(SMSActivity.this,"SMS send Successfully",Toast.LENGTH_SHORT).show();
                                }

                                if(student == null)
                                {
                                    Toast.makeText(SMSActivity.this, "GR number not correct", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }

}
