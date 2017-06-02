package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPage extends AppCompatActivity
{


    public Button btn1;
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
    public Button btn;
    public void createloginpage()
    {
        btn = (Button) findViewById(R.id.createlogin_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage = new Intent(MenuPage.this,Createlogin.class);
                startActivity(changepage);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
            newpage();
        createloginpage();
    }
}
