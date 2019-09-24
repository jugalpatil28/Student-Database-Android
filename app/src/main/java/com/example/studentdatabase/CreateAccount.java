//package com.example.studentdatabase;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//public class CreateAccount extends AppCompatActivity {
//
//    private static final String TAG = "EmailPassword";
//    private EditText email,password,confirmPassword;
//    private Spinner spinnerYear, spinnerBatch, spinnerBranch;
//    private String year,branch,batch;
//    private FirebaseFirestore dataBase;
//    private Button createButtton;
//    private FirebaseAuth mAuth;
//    private ProgressDialog pd;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_account);
//
//        FirebaseApp.initializeApp(this);
//
//        dataBase = FirebaseFirestore.getInstance();
//        mAuth = FirebaseAuth.getInstance();
//
//        pd = new ProgressDialog(CreateAccount.this);
//        pd.setMessage("loading");
//
//        initialize();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//
//    private void updateUI(FirebaseUser user) {
//        pd.dismiss();
//        if(user!=null) {
//            startActivity(new Intent(CreateAccount.this, MainActivity.class));
//        }
//    }
//
//    private void initialize(){
//
//        email = findViewById(R.id.fieldEmail);
//        password = findViewById(R.id.fieldPassword);
//        confirmPassword = findViewById(R.id.fieldConfirmPassword);
//
//        spinnerYear = findViewById(R.id.spinnerYear);
//        spinnerBatch = findViewById(R.id.spinnerBatch);
//        spinnerBranch = findViewById(R.id.spinnerBranch);
//
//        createButtton = findViewById(R.id.done);
//
//        spinnerBranch = findViewById(R.id.spinnerBranch);
//        ArrayAdapter<CharSequence> adapterBranch = ArrayAdapter.createFromResource(this,R.array.Branch, R.layout.spinner_item);
//        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerBranch.setAdapter(adapterBranch);
//        spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                branch = spinnerBranch.getItemAtPosition(position).toString().trim().toUpperCase();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spinnerBatch = findViewById(R.id.spinnerBatch);
//        ArrayAdapter<CharSequence> adapterBatch = ArrayAdapter.createFromResource(this,R.array.Batch, R.layout.spinner_item);
//        adapterBatch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerBatch.setAdapter(adapterBatch);
//        spinnerBatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                batch = spinnerBatch.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spinnerYear = findViewById(R.id.spinnerYear);
//        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this,R.array.Year, R.layout.spinner_item);
//        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerYear.setAdapter(adapterYear);
//        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                year = spinnerYear.getItemAtPosition(position).toString().trim().toUpperCase();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        createButtton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createAccount(email.getText().toString().trim(),password.getText().toString().trim());
//            }
//        });
//    }
//
//    private void addUserDetails(){
//
//        if (validateForm()){
//
//            UserDetails batchGuardian = new UserDetails(branch,year,batch);
//
//            dataBase.collection("user_details")
//                    .document(email.getText().toString())
//                    .set(batchGuardian)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(CreateAccount.this, "Batch Guardian added", Toast.LENGTH_SHORT).show();
//                            createAccount(email.getText().toString(),password.getText().toString());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(CreateAccount.this, "Batch Guardian not added", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//        } else {
//            Toast.makeText(CreateAccount.this, "You must enter all fields ", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private boolean validateForm() {
//        boolean valid = true;
//        String email = this.email.getText().toString();
//        if (TextUtils.isEmpty(email)) {
//            this.email.setError("Required.");
//            valid = false;
//        } else {
//            this.email.setError(null);
//        }
//
//        String password = this.password.getText().toString();
//        if (TextUtils.isEmpty(password)) {
//            this.password.setError("Required.");
//            valid = false;
//        } else {
//            this.password.setError(null);
//        }
//
//        String confirmPassword = this.confirmPassword.getText().toString();
//        if(confirmPassword.equals(password)){
//            this.confirmPassword.setError("Required.");
//            valid = false;
//        } else {
//            this.confirmPassword.setError(null);
//        }
//
//        return valid;
//    }
//
//    private void createAccount(String email, String password) {
//        Log.d(TAG, "createAccount:" + email);
//        if (!validateForm()) {
//            return;
//        }
//
//        pd.show();
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                            addUserDetails();
//                        } else {
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(CreateAccount.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                        pd.dismiss();
//                    }
//                });
//    }
//}
