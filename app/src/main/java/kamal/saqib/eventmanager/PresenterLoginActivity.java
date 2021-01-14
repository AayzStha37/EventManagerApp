package kamal.saqib.eventmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

/**
 * Created by Dell on 6/19/2017.
 */

public class PresenterLoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button  btnLogin, btnReset;
    private boolean loggedIn;

    //*
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    //*

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.presenter_login);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.edtxt_uname);
        inputPassword = (EditText) findViewById(R.id.edtxt_pw);
       // btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.bt_login);
        btnReset = (Button) findViewById(R.id.bt_forgot_pw);
        loggedIn = false;

        //*
        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        mFirebaseInstance.getReference("app_title").setValue("Users and Events");
        //*



        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Enter Email Address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(PresenterLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if(!task.isSuccessful()){
                                    Toast.makeText(PresenterLoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                } else {
                                    loggedIn = true;
                                    final Query q = mFirebaseDatabase.orderByChild("email").equalTo(email);
//
//

                                    q.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            Employee cus = dataSnapshot.getValue(Employee.class);
                                            userId = dataSnapshot.getKey();
                                            if(cus.getPost().equals("Presenter"))
                                                toPresenterActivity(cus);
                                            else {
                                                Toast.makeText(PresenterLoginActivity.this, "You are not an Presenter", Toast.LENGTH_LONG).show();
                                                return;
                                            }

                                            //onDataChange(dataSnapshot);

                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }




                                    });



                                    Log.i("Loginey", String.valueOf(loggedIn));



                                }
                            }
                        });


            }
        });

    }


    private void toPresenterActivity(Employee cus){
        Intent i = new Intent(getApplicationContext(), PresenterActivity.class);
        Log.i("Loginex", String.valueOf(loggedIn));
        //i.putExtra("organiserName",(Serializable) cus);
        Bundle orgObject = new Bundle();
        orgObject.putSerializable("presenterName",(Serializable) cus);

        i.putExtra("bundle", orgObject);
        //i.putExtra("UserId", userId);
        startActivity(i);
        //setResult(Activity.RESULT_OK, i);
        finish();
    }

//    public void onBackPressed(){
//        backToMain();
//    }

}
