package kamal.saqib.eventmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;


/**
 * Created by Dell on 6/19/2017.
 */

public class SignupActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword, inputName, inputPhone;
    private Button btnSignUp;
    //private ProgressBar progressBar;
    private FirebaseAuth auth;

    //added later******************************
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    //*****************************************

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presenter_signup);

        auth = FirebaseAuth.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sign Up");

        //btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.bt_signup);
        inputEmail = (EditText) findViewById(R.id.edtxt_email);
        inputPassword = (EditText) findViewById(R.id.edtxt_pw1);
        inputName = (EditText) findViewById(R.id.edtxt_uname);
        inputPhone = (EditText) findViewById(R.id.edtxt_re_pw);
       /// btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        //********************
        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        mFirebaseInstance.getReference("app_title").setValue("Users and Events");
        //********************





        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                final String post = "Presenter";
                //Added later*************************
                final String name = inputName.getText().toString().trim();
                final String phone = inputPhone.getText().toString().trim();


                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter Phone Number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //*************************************

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }



                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Toast.makeText(getApplicationContext(), "createUserWithEmail:onComplete"+task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                //progressBar.setVisibility(View.GONE);

                                if(!task.isSuccessful()){
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    createUser(name, phone, email, post);

                                }
                            }
                        });





            }
        });



    }

    private void createUser(String name, String phone, String email, String post) {
        if(TextUtils.isEmpty(userId)){
            userId = mFirebaseDatabase.push().getKey();
        }
        Employee employee = new Employee(name, phone, email, post);
        mFirebaseDatabase.child(userId).setValue(employee);
        Intent i=new Intent(getApplicationContext(), PresenterActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("employee_detail",(Serializable)employee);
        i.putExtra("BUNDLE",args);


        startActivity(i);

        finish();

    }



    protected void onResume(){
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }

}
