package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class descrpition2 extends AppCompatActivity {

    public Button btn1;
    public void joinpage()
    {
        btn1 = (Button) findViewById(R.id.join_button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join = new Intent(descrpition2.this,join.class);
                startActivity(join);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descrpition2);
        joinpage();
    }
}
