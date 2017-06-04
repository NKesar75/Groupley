package domain.teamgroupley.groupleyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
            }
        });
    }
    public  void alertbox()
    {
        btn2 = (Button) findViewById(R.id.Save_CB);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(IntrestSelection.this);
                a_builder.setMessage("Interest Saved!!!!!").setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = a_builder.create();
                alert.setTitle("INTERESTS");
                alert.show();
            }
        });
    }

    public void gotomenu()
    {
        btn1 = (Button) findViewById(R.id.menu_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage = new Intent(IntrestSelection.this,MenuPage.class);
                startActivity(changepage);
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrest_selection);
        gotomenu();
        savechanges();
        alertbox();
    }
}
