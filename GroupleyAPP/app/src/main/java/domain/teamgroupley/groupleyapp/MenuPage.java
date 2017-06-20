package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuPage extends AppCompatActivity
{


   private Button home;
   private Button reg_event;
   private Button create_event;
    private Button profile;
    private Button settings;

    public void homepage()
    {
        home = (Button) findViewById(R.id.Upcoming_Events_BTN);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage = new Intent(MenuPage.this,Home.class);
                startActivity(changepage);
            }
        });
    }

    public void registeredeventpage()
    {
        reg_event = (Button) findViewById(R.id.reg_event_btn);
        reg_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(MenuPage.this,Registered_Events.class);
                startActivity(changepage2);
            }
        });
    }

    public void createeventpage()
    {
        create_event = (Button) findViewById(R.id.create_event_btn);
        create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(MenuPage.this,Create_Event.class);
                startActivity(changepage2);
            }
        });
    }

    public void updateCreatedEventsListpage()
    {
        reg_event = (Button) findViewById(R.id.update_event_btn);
        reg_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(MenuPage.this,CreatedEventList.class);
                startActivity(changepage2);
            }
        });
    }

    public void profilepage()
    {
        profile = (Button) findViewById(R.id.profile_btn);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(MenuPage.this,Profile.class);
                startActivity(changepage2);
            }
        });
    }
    public void settingspage()
    {
        settings = (Button) findViewById(R.id.settings_btn);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(MenuPage.this,Settings.class);
                startActivity(changepage2);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        homepage();
        registeredeventpage();
        createeventpage();
        profilepage();
        settingspage();
        updateCreatedEventsListpage();
    }
}
