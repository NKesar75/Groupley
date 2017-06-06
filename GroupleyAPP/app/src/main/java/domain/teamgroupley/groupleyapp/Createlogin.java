package domain.teamgroupley.groupleyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Createlogin extends AppCompatActivity {

   /* public Button btn1;
    public void newpage()
    {
        btn1 = (Button) findViewById(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage = new Intent(Createlogin.this,MenuPage.class);
                startActivity(changepage);
            }
        });
    }
    public Button btn;
    public  void alertbox()
    {
        btn = (Button) findViewById(R.id.button5);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(Createlogin.this);
                a_builder.setMessage("Account Created!!!").setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Create Account");
                alert.show();
            }
        });
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createlogin);
        setContentView(R.layout.activity_intrest_selection);
//        newpage();
//        alertbox();
    }
}
