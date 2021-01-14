package kamal.saqib.eventmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.Serializable;
import java.util.ArrayList;

public class PresenterActivity extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    String email,presentername;
    Employee employee;
    ArrayList<Event> events;
    Button bt_my,bt_all,add_new,logout;
    TextView tx1, tx2, myevents, allevents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presenter_details);

        //setContentView(R.layout.activity_organizer2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Presenter Details");

        //setContentView(R.layout.activity_organizer2);
        tx1 = (TextView) findViewById(R.id.tv_disp_uname);
        tx2 = (TextView) findViewById(R.id.tv_disp_umail);


        myevents = (TextView) findViewById(R.id.tv_my_events);
        allevents = (TextView) findViewById(R.id.tv_all_events);

        logout = (Button) findViewById(R.id.bt_logout);


        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        employee = (Employee) args.getSerializable("presenterName");

        presentername = employee.getName();


        firebaseAuth = FirebaseAuth.getInstance();
        email = firebaseAuth.getCurrentUser().getEmail().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("Event");

        events = employee.getEvent();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                events.clear();

                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    Event event = user.getValue(Event.class);
                    ArrayList<Employee> presenter = event.getPresenter();
                    for (Employee employee : presenter) {
                        if (employee.getName() == presentername) {
                            events.add(event);
                            break;
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        if(view==bt_my){
            Intent in = new Intent(getBaseContext(), MyEventlist.class);
            Bundle args = new Bundle();
            args.putSerializable("myeventlist",(Serializable)events);
            in.putExtra("BUNDLE",args);
            startActivity(in);
        }
        else if(view==bt_all){
            Intent in = new Intent(getBaseContext(), alleventlist.class);
            startActivity(in);
        }
        else if(view==logout){
            new android.support.v7.app.AlertDialog.Builder(PresenterActivity.this).setTitle("Log Out").
                    setMessage("Are you sure you want to Log out ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Logged Out Succesfully", Toast.LENGTH_LONG).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();

        }

    }
}
