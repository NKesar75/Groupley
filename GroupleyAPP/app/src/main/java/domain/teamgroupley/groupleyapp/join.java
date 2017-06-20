package domain.teamgroupley.groupleyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class join extends AppCompatActivity {
    public Button btn2;
    public  void alertbox()
    {
        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(join.this, "You Have Joined.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(join.this,MenuPage.class));
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        alertbox();
    }
}
