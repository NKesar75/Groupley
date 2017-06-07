package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class event_page extends AppCompatActivity {

    public ImageButton btn2;
    public void des1()
    {
        btn2 = (ImageButton) findViewById(R.id.btn_1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(event_page.this,descrpition1.class);
                startActivity(changepage2);
            }
        });
    }
    public ImageButton btn;
    public void des2()
    {
        btn = (ImageButton) findViewById(R.id.btn_2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(event_page.this,descrpition2.class);
                startActivity(changepage2);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        des1();
        des2();
    }
}
