package kamal.saqib.eventmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class OrganiserDetails extends AppCompatActivity {

    EditText nameField, phoneField;
    Button confirmBtn;
    String name;
    String phone;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organiser_details);

        nameField = (EditText) findViewById(R.id.name);
        phoneField = (EditText) findViewById(R.id.phone);
        confirmBtn = (Button) findViewById(R.id.btn_confirm);

        Intent i = getIntent();
        final String email = i.getStringExtra("email");
        final String post = "Organizer";

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        //Bundle bun = i.getBundleExtra();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = nameField.getText().toString();
                phone = phoneField.getText().toString();
                if (name.length() == 0 || phone.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Field left blank!!", Toast.LENGTH_SHORT).show();
                } else{
                    createUser(name, phone, email, post);

                }

            }
        });
    }

    private void createUser(String name, String phone, String email, String post) {
        if(TextUtils.isEmpty(userId)){
            userId = mFirebaseDatabase.push().getKey();
        }
        Employee employee = new Employee(name, phone, email, post);
        mFirebaseDatabase.child(userId).setValue(employee);
        Intent i = new Intent(getApplicationContext(), OrganizerActivity.class);

        Bundle orgObject = new Bundle();
        orgObject.putSerializable("organiserName",(Serializable) employee);

        i.putExtra("bundle", orgObject);
        startActivity(i);
        finish();

    }



}
