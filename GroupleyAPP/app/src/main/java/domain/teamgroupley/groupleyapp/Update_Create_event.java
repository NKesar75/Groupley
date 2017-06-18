package domain.teamgroupley.groupleyapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
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
import android.widget.TimePicker;
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

public class Update_Create_event extends AppCompatActivity {

    EditText Title;
    EditText Disc;
    Spinner Cat;
    EditText Date;
    EditText Time;
    EditText Addey;
    EditText Max;
    Button Create;

    private static final String TAG = "Update_Create_event";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();

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
                    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference mFirstName = mRootRef.child(USerid).child("UserInfo").child("Firstname");
                    DatabaseReference mLastName = mRootRef.child(USerid).child("UserInfo").child("Lastname");
                    DatabaseReference mGender = mRootRef.child(USerid).child("UserInfo").child("Sex");
                    DatabaseReference mDOB = mRootRef.child(USerid).child("UserInfo").child("DOB");
                    DatabaseReference mUsername = mRootRef.child(USerid).child("UserInfo").child("UserName");




                    myRef.child(userID).child("CreatedEvents").child(tie).setValue(Passing);
                    myRef.child("Events").child(tie).setValue(Passing);
                    Intent changepage = new Intent(Update_Create_event.this, MenuPage.class);
                    startActivity(changepage);
                    Toast.makeText(Update_Create_event.this, "Event Has Been Created", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Update_Create_event.this, "Missing some information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__create_event);
        Cat = (Spinner) findViewById(R.id.Category_SPINNER);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Update_Create_event.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Category));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cat.setAdapter(myAdapter);
        Title = (EditText)findViewById(R.id.Title_txt_update);
        Disc = (EditText) findViewById(R.id.des_txt_Update);
        Date = (EditText)findViewById(R.id.Date_txt_Update);
        Time = (EditText)findViewById(R.id.time_txt_Update);
        Addey = (EditText)findViewById(R.id.address_txt_Update);
        Max = (EditText)findViewById(R.id.max_people_txt_Update);

        Date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Update_Create_event.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListner,year,month,day);
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
                    java.util.Date curDate = sdf.parse(mCurMonth + "/" + mCurDay + "/" + mCurYear);

                    if(enteredDate.before(curDate))
                    {
                        Toast.makeText(Update_Create_event.this, "Pick Today's Date or a Date after it", Toast.LENGTH_SHORT).show();
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

        Time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v)
            {
                Calendar timechoose = Calendar.getInstance();
                new TimePickerDialog(Update_Create_event.this,onTimeSetListener,timechoose.get(Calendar.HOUR_OF_DAY),timechoose.get(Calendar.MINUTE),true).show();
            }
        });

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

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            Time.setText(hourOfDay + ":" + minute);
        }
    };
}

