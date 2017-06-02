package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPage extends AppCompatActivity
{
    public Button btn1;
    public void insertpage()
    {
        btn1 = (Button) findViewById(R.id.button4);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage = new Intent(MenuPage.this,IntrestSelection.class);
                startActivity(changepage);
            }
        });
    }
    public Button btn2;
    public void createaccountpage()
    {
        btn2 = (Button) findViewById(R.id.button6);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(MenuPage.this,Createlogin.class);
                startActivity(changepage2);
            }
        });
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
            newpage();
    }
}
