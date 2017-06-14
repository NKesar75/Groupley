package domain.teamgroupley.groupleyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class IntrestSelection extends AppCompatActivity {

    public Button btn1;
    public Button btn2;

    public void savechanges()
    {
        btn2 = (Button) findViewById(R.id.Save_CB);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage = new Intent(IntrestSelection.this, MenuPage.class);
                startActivity(changepage);
                Toast.makeText(IntrestSelection.this, "Interest Saved.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrest_selection);
        savechanges();
    }
}
