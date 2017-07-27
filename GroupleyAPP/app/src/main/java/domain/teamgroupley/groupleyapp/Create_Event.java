package domain.teamgroupley.groupleyapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.attr.data;

public class Create_Event extends AppCompatActivity {

    EditText Title;
    EditText Disc;
    Spinner Cat;
    EditText Date;
    EditText Time;
    EditText Addey;
    EditText Max;
    Button Create;


    private List<String> fifty = new ArrayList<String>();



    private static final String TAG = "Create_Event";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    String userID;

    long EVENTCOUNT;
    long CreatedEVENTCOUNT;
    long REGISTEREDEVENTCOUNT;
    String Event = "Event";

    private DatePickerDialog.OnDateSetListener mDateSetListner;

    public void SendEventTodatabase() {
        Create = (Button) findViewById(R.id.Create_event_btn);
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
                if (!tie.equals("") && !Die.equals("") && !Cator.equals("") && !Day.equals("") && !Tim.equals("")
                        && !ADd.equals("") && !MAxppl.equals("")) {
                    CreateEventStatsinfo Passing = new CreateEventStatsinfo(tie, Die, Cator, Day, Tim, ADd, MAxppl);
                    FirebaseUser user = mAuth.getCurrentUser();
                    userID = user.getUid();



                    myRef.child(userID).child("CreatedEvents").child(Event+CreatedEVENTCOUNT).child("Title").setValue(tie);
                    myRef.child(userID).child("CreatedEvents").child(Event+CreatedEVENTCOUNT).child("Description").setValue(Die);
                    myRef.child(userID).child("CreatedEvents").child(Event+CreatedEVENTCOUNT).child("Category").setValue(Cator);
                    myRef.child(userID).child("CreatedEvents").child(Event+CreatedEVENTCOUNT).child("Date").setValue(Day);
                    myRef.child(userID).child("CreatedEvents").child(Event+CreatedEVENTCOUNT).child("Time").setValue(Tim);
                    myRef.child(userID).child("CreatedEvents").child(Event+CreatedEVENTCOUNT).child("Address").setValue(ADd);
                    myRef.child(userID).child("CreatedEvents").child(Event+CreatedEVENTCOUNT).child("Max_People").setValue(MAxppl);

                    myRef.child("Events").child(Event+EVENTCOUNT).child("Title").setValue(tie);
                    myRef.child("Events").child(Event+EVENTCOUNT).child("Description").setValue(Die);
                    myRef.child("Events").child(Event+EVENTCOUNT).child("Category").setValue(Cator);
                    myRef.child("Events").child(Event+EVENTCOUNT).child("Date").setValue(Day);
                    myRef.child("Events").child(Event+EVENTCOUNT).child("Time").setValue(Tim);
                    myRef.child("Events").child(Event+EVENTCOUNT).child("Address").setValue(ADd);
                    myRef.child("Events").child(Event+EVENTCOUNT).child("Max_People").setValue(MAxppl);

                    myRef.child(userID).child("RegisteredEvents").child(Event+REGISTEREDEVENTCOUNT).child("Title").setValue(tie);
                    myRef.child(userID).child("RegisteredEvents").child(Event+REGISTEREDEVENTCOUNT).child("Description").setValue(Die);
                    myRef.child(userID).child("RegisteredEvents").child(Event+REGISTEREDEVENTCOUNT).child("Category").setValue(Cator);
                    myRef.child(userID).child("RegisteredEvents").child(Event+REGISTEREDEVENTCOUNT).child("Date").setValue(Day);
                    myRef.child(userID).child("RegisteredEvents").child(Event+REGISTEREDEVENTCOUNT).child("Time").setValue(Tim);
                    myRef.child(userID).child("RegisteredEvents").child(Event+REGISTEREDEVENTCOUNT).child("Address").setValue(ADd);
                    myRef.child(userID).child("RegisteredEvents").child(Event+REGISTEREDEVENTCOUNT).child("Max_People").setValue(MAxppl);


                    Intent changepage = new Intent(Create_Event.this, MenuPage.class);
                    startActivity(changepage);
                    Toast.makeText(Create_Event.this, "Event Has Been Created", Toast.LENGTH_SHORT).show();
                } else {
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
        Title = (EditText) findViewById(R.id.Title_txt);
        Disc = (EditText) findViewById(R.id.des_txt);
        Date = (EditText) findViewById(R.id.Date_txt);
        Time = (EditText) findViewById(R.id.time_txt);
        Addey = (EditText) findViewById(R.id.address_txt);
        Max = (EditText) findViewById(R.id.max_people_txt);

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
                        mDateSetListner, year, month, day);
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

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    java.util.Date enteredDate = sdf.parse(mMonth + "/" + mDay + "/" + mYear);
                    Date curDate = sdf.parse(mCurMonth + "/" + mCurDay + "/" + mCurYear);

                    if (enteredDate.before(curDate)) {
                        Toast.makeText(Create_Event.this, "Pick Today's Date or a Date after it", Toast.LENGTH_SHORT).show();
                    } else {
                        Date.setText(date);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };

        Time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar timechoose = Calendar.getInstance();
                new TimePickerDialog(Create_Event.this, onTimeSetListener, timechoose.get(Calendar.HOUR_OF_DAY), timechoose.get(Calendar.MINUTE), true).show();
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

                    // shoData(dataSnapshot);
                EVENTCOUNT = dataSnapshot.child("Events").getChildrenCount() + 1;
                CreatedEVENTCOUNT = dataSnapshot.child(userID).child("CreatedEvents").getChildrenCount() + 1;
                REGISTEREDEVENTCOUNT = dataSnapshot.child(userID).child("RegisteredEvents").getChildrenCount() + 1;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Create_Event.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Category));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cat.setAdapter(myAdapter);

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
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Time.setText(hourOfDay + ":" + minute);
        }
    };

//    private void shoData(DataSnapshot dataSnapshot) {
//
//        for (DataSnapshot ds : dataSnapshot.child(userID).child("Interests").getChildren()) {
//            Intresetedloggedup intey = ds.getValue(Intresetedloggedup.class);
//            if (intey.isCActionfigures()) {
//                fifty.add("Action Figures");
//            }
//            if (intey.isCCars()) {
//                fifty.add("Cars");
//            }
//
//            if (intey.isCCoins())
//            {
//                fifty.add("Coins");
//            }
//
//            if (intey.isCComics())
//            {
//                fifty.add("Comic Books");
//            }
//
//            if (intey.isCGuns())
//            {
//                fifty.add("Guns");
//            }
//
//            if (intey.isCTrucks())
//            {
//                fifty.add("Trucks");
//            }
//
//            if (intey.isDActing())
//            {
//                fifty.add("Acting");
//            }
//
//            if (intey.isDCosplay())
//            {
//                fifty.add("Cosplay");
//            }
//
//            if (intey.isDLarping())
//            {
//                fifty.add("Larping");
//            }
//
//            if (intey.isGAction())
//            {
//                fifty.add("Game Action");
//            }
//
//            if (intey.isGAdventure())
//            {
//                fifty.add("Game Adventure");
//            }
//
//            if (intey.isGFPS())
//            {
//                fifty.add("Game FPS");
//            }
//
//            if (intey.isGIndies())
//            {
//                fifty.add("Game Indies");
//            }
//
//            if (intey.isGMMO())
//            {
//                fifty.add("Game MMO");
//            }
//
//            if (intey.isGPartyfamily())
//            {
//                fifty.add("Game Party/Fam");
//            }
//
//            if (intey.isGRPG())
//            {
//                fifty.add("Game RPG");
//            }
//
//            if (intey.isGSimulation())
//            {
//                fifty.add("Game Sim");
//            }
//
//            if (intey.isGSports())
//            {
//                fifty.add("Game Sports");
//            }
//
//            if (intey.isGStragy())
//            {
//                fifty.add("Game Stragey");
//            }
//
//            if (intey.isGAction())
//            {
//                fifty.add("Game Action");
//            }
//
//            if (intey.isMCountry())
//            {
//                fifty.add("Country");
//            }
//
//            if (intey.isMDrillrap())
//            {
//                fifty.add("Drill Rap");
//            }
//
//            if (intey.isMEDM())
//            {
//                fifty.add("EDM");
//            }
//
//            if (intey.isMJAZZ())
//            {
//                fifty.add("Jazz");
//            }
//
//            if (intey.isMoAction())
//            {
//                fifty.add("Moive Action");
//            }
//
//            if (intey.isMoAnimation())
//            {
//                fifty.add("Moive Animation");
//            }
//
//            if (intey.isMoComdey())
//            {
//                fifty.add("Moive Comedy");
//            }
//
//            if (intey.isMoDocumentary())
//            {
//                fifty.add("Moive Documentary");
//            }
//
//            if (intey.isMoFamily())
//            {
//                fifty.add("Moive Family");
//            }
//
//            if (intey.isMoHorror())
//            {
//                fifty.add("Moive Horror");
//            }
//
//            if (intey.isMoMusical())
//            {
//                fifty.add("Moive Musical");
//            }
//
//            if (intey.isMoSifi())
//            {
//                fifty.add("Movie Sifi");
//            }
//
//            if (intey.isMoSports())
//            {
//                fifty.add("Moive Sports");
//            }
//
//            if (intey.isMoThriller())
//            {
//                fifty.add("Moive Thriller");
//            }
//
//            if (intey.isMoWar())
//            {
//                fifty.add("Moive War");
//            }
//
//            if (intey.isTAction())
//            {
//                fifty.add("Tv Aciton");
//            }
//
//            if (intey.isTAdventure())
//            {
//                fifty.add("Tv Adventure");
//            }
//
//            if (intey.isTAnimation())
//            {
//                fifty.add("Tv Animation");
//            }
//
//            if (intey.isTBiography())
//            {
//                fifty.add("Tv Biography");
//            }
//
//            if (intey.isTComedy())
//            {
//                fifty.add("Tv Comedy");
//            }
//
//            if (intey.isTCrime())
//            {
//                fifty.add("Tv Crime");
//            }
//
//            if (intey.isTDocoumentary())
//            {
//                fifty.add("Tv Documentary");
//            }
//
//            if (intey.isTDrama())
//            {
//                fifty.add("Tv Drama");
//            }
//
//            if (intey.isTFamily())
//            {
//                fifty.add("Tv Family");
//            }
//
//            if (intey.isTGameShows())
//            {
//                fifty.add("Tv Gameshows");
//            }
//
//            if (intey.isTHistory())
//            {
//                fifty.add("Tv History");
//            }
//
//            if (intey.isTHorror())
//            {
//                fifty.add("Tv Horror");
//            }
//
//            if (intey.isTMystery())
//            {
//                fifty.add("Tv Mystery");
//            }
//
//            if (intey.isTReality())
//            {
//                fifty.add("Tv Reality");
//            }
//
//            if (intey.isTSitcom())
//            {
//                fifty.add("Tv Sitcom");
//            }
//
//            if (intey.isTSports())
//            {
//                fifty.add("Tv Sports");
//            }
//
//            if (intey.isTTalkShows())
//            {
//                fifty.add("Tv Talkshows");
//            }
//
//            if (intey.isTWar())
//            {
//                fifty.add("Tv Wars");
//            }
//
//            if (intey.isPFestivles())
//            {
//                fifty.add("Festival");
//            }
//
//            if (intey.isPHouseParites())
//            {
//                fifty.add("House Party");
//            }
//
//            if (intey.isPNightClubs())
//            {
//                fifty.add("Night Club");
//            }
//
//            if (intey.isSArchery())
//            {
//                fifty.add("Archery");
//            }
//
//            if (intey.isSBaseball())
//            {
//                fifty.add("Baseball");
//            }
//
//            if (intey.isSBasketball())
//            {
//                fifty.add("Basketball");
//            }
//
//            if (intey.isSCycling())
//            {
//                fifty.add("Cycling");
//            }
//
//            if (intey.isSFootball())
//            {
//                fifty.add("Football");
//            }
//
//            if (intey.isSFrisbe())
//            {
//                fifty.add("Frisbe");
//            }
//
//            if (intey.isSGolf())
//            {
//                fifty.add("Golf");
//            }
//
//            if (intey.isSHoccey())
//            {
//                fifty.add("Hockey");
//            }
//
//            if (intey.isSHunting())
//            {
//                fifty.add("Hunting");
//            }
//
//            if (intey.isSSkateboarding())
//            {
//                fifty.add("Skateboarding");
//            }
//
//            if (intey.isSSnowBoarding())
//            {
//                fifty.add("Snowboarding");
//            }
//
//            if (intey.isSWaterSports())
//            {
//                fifty.add("Water Sports");
//            }
//        }
//
//    }
}
