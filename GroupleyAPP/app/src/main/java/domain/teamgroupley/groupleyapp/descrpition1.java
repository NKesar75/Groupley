package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class descrpition1 extends AppCompatActivity {

    public Button btn1;
    public void joinpage()
    {
        btn1 = (Button) findViewById(R.id.join_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join2 = new Intent(descrpition1.this,join.class);
                startActivity(join2);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descrpition1);
        joinpage();
    }
}
