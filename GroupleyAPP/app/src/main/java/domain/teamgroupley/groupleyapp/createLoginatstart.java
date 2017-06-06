package domain.teamgroupley.groupleyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class createLoginatstart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_loginatstart);

        Spinner mySpinner = (Spinner) findViewById(R.id.GENDER_SPINNER);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(createLoginatstart.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Genders));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
    }
}
