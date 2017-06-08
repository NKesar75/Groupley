package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPage extends AppCompatActivity
{


   private Button btn1;
   private Button btn2;
   private Button btn;


    public void newpage()
    {
        btn1 = (Button) findViewById(R.id.INTREST_BTN);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage = new Intent(MenuPage.this,IntrestSelection.class);
                startActivity(changepage);
            }
        });
    }

    public void createloginpage()
    {
        btn2 = (Button) findViewById(R.id.createlogin_btn);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(MenuPage.this,createLoginatstart.class);
                startActivity(changepage2);
            }
        });
    }

    public void evetnspage()
    {
        btn = (Button) findViewById(R.id.event_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(MenuPage.this,event_page.class);
                startActivity(changepage2);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
            newpage();
        createloginpage();
        evetnspage();
    }
}
