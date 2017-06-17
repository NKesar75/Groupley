package domain.teamgroupley.groupleyapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Date;

public class Create_Event extends AppCompatActivity {

    EditText Title;
    EditText Disc;
    Spinner Cat;
    EditText Date;
    EditText Time;
    EditText Addey;
    EditText Max;
    Button Create;

    private static final String TAG = "Create_Event";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private DatePickerDialog.OnDateSetListener mDateSetListner;

    public void SendEventTodatabase(){
        Create = (Button)findViewById(R.id.Create_event_btn);
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tie = Title.getText().toString();
                String Die = Disc.getText().toString();
                String Cator = Cat.getSelectedItem().toString();
                String Day = Date.getText().toString();
                String Tim = Time.getText().toString();
                String ADd = Addey.getText().toString();
                String MAxppl = Max.getText().toString();
                if (!tie.equals("") && !Die.equals("")&& !Cator.equals("")&& !Day.equals("")&& !Tim.equals("")
                        && !ADd.equals("") && !MAxppl.equals("")){
                    CreateEventStatsinfo Passing = new CreateEventStatsinfo(tie,Die,Cator,Day,Tim,ADd,MAxppl);
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    myRef.child(userID).child("CreatedEvents").setValue(Passing);
                    myRef.child("Events").setValue(Passing);
                    Intent changepage = new Intent(Create_Event.this, MenuPage.class);
                    startActivity(changepage);
                    Toast.makeText(Create_Event.this, "Event Has Been Created", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Create_Event.this, "Missing some information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);
        Cat = (Spinner) findViewById(R.id.Category_SPINNER);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Create_Event.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Category));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cat.setAdapter(myAdapter);
        Title = (EditText)findViewById(R.id.Title_txt);
        Disc = (EditText) findViewById(R.id.des_txt);
        Date = (EditText)findViewById(R.id.Date_txt);
        Time = (EditText)findViewById(R.id.time_txt);
        Addey = (EditText)findViewById(R.id.address_txt);
        Max = (EditText)findViewById(R.id.max_people_txt);

        Date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Create_Event.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListner,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
                dialog.show();
            }
        });

        mDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;

                String mMonth = String.valueOf(month);
                String mDay = String.valueOf(dayOfMonth);
                String mYear = String.valueOf(year);

                Calendar cal = Calendar.getInstance();
                int curYear = cal.get(Calendar.YEAR);
                int curMonth = cal.get(Calendar.MONTH);
                int curDay = cal.get(Calendar.DAY_OF_MONTH);

                String mCurMonth = String.valueOf(curMonth);
                String mCurDay = String.valueOf(curDay);
                String mCurYear = String.valueOf(curYear);

                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    java.util.Date enteredDate = sdf.parse(mMonth + "/" + mDay + "/" + mYear);
                    Date curDate = sdf.parse(mCurMonth + "/" + mCurDay + "/" + mCurYear);

                    if(enteredDate.before(curDate))
                    {
                        Toast.makeText(Create_Event.this, "Pick Today's Date or a Date after it", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Date.setText(date);
                    }
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }

            }
        };


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        SendEventTodatabase();
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
