package kamal.saqib.eventmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView ologin,plogin,signup,ourevents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);

        ologin=(TextView) findViewById(R.id.tv_org_login);
        plogin=(TextView) findViewById(R.id.tv_presenter_login);
        signup=(TextView) findViewById(R.id.txtv1);
        ourevents=(TextView) findViewById(R.id.tv_events);

        ologin.setOnClickListener(this);
        plogin.setOnClickListener(this);
        signup.setOnClickListener(this);
        ourevents.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view==ologin){
            startActivity(new Intent(getApplicationContext(), OrgLoginActivity.class));
        }
        else if(view==plogin){
            startActivity(new Intent(getApplicationContext(), PresenterLoginActivity.class));
        }
        else if(view==signup){
            startActivity(new Intent(getApplicationContext(), SignupActivity.class));
        }
        else if(view==ourevents){
            startActivity(new Intent(getApplicationContext(), alleventlist.class));
        }

    }
}
