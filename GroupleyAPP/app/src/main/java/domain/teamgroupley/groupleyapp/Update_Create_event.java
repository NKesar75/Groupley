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

    private EditText Title;
    private EditText Disc;
    private EditText Cat;
    private EditText Date;
    private EditText Time;
    private EditText Addey;
    private EditText Max;
    private Button Update;

    private static final String TAG = "Update_Create_event";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();

    long EVENTCOUNT = CreatedEventList.CreateEventTitle;
    long updatecount;
    String Event = "Event";

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mTitle = mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("Title");
    DatabaseReference mDescription = mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("Description");
    DatabaseReference mCategory = mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("Category");
    DatabaseReference mDate = mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("Date");
    DatabaseReference mTime = mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("Time");
    DatabaseReference mAddress = mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("Address");
    DatabaseReference mMaxPpl = mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("Max_People");
    DatabaseReference mNumber = mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("EVENTNUMBER");

    private DatePickerDialog.OnDateSetListener mDateSetListner;

    public void UpdateCreatedEvents()
    {

        Update = (Button)findViewById(R.id.Update_event_btn);
        Update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String Die = Disc.getText().toString();
                String Day = Date.getText().toString();
                String Tim = Time.getText().toString();
                String ADd = Addey.getText().toString();
                String MAxppl = Max.getText().toString();

                if (!Die.equals("")&& !Day.equals("")&& !Tim.equals("")
                        && !ADd.equals("") && !MAxppl.equals("")){

                    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

                    mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("EVENTNUMBER");

                    mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("Description").setValue(Die);
                    mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("Date").setValue(Day);
                    mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("Time").setValue(Tim);
                    mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("Address").setValue(ADd);
                    mRootRef.child(USerid).child("CreatedEvents").child(Event+EVENTCOUNT).child("Max_People").setValue(MAxppl);

                    mRootRef.child("Events").child(Event+updatecount).child("Description").setValue(Die);
                    mRootRef.child("Events").child(Event+updatecount).child("Date").setValue(Day);
                    mRootRef.child("Events").child(Event+updatecount).child("Time").setValue(Tim);
                    mRootRef.child("Events").child(Event+updatecount).child("Address").setValue(ADd);
                    mRootRef.child("Events").child(Event+updatecount).child("Max_People").setValue(MAxppl);

                    Intent changepage = new Intent(Update_Create_event.this, MenuPage.class);
                    startActivity(changepage);
                    Toast.makeText(Update_Create_event.this, "Event Has Been Updated", Toast.LENGTH_SHORT).show();
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
        Cat = (EditText) findViewById(R.id.Catergory_txt_updt);
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
        UpdateCreatedEvents();
    }
    @Override
    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        mTitle.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String temp = dataSnapshot.getValue(String.class);
                Title.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDescription.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String temp = dataSnapshot.getValue(String.class);
                Disc.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String temp = dataSnapshot.getValue(String.class);
                Cat.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String temp = dataSnapshot.getValue(String.class);
                Date.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String temp = dataSnapshot.getValue(String.class);
                Time.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mNumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updatecount = dataSnapshot.getValue(long.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAddress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String temp = dataSnapshot.getValue(String.class);
                Addey.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMaxPpl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String temp = dataSnapshot.getValue(String.class);
                Max.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

