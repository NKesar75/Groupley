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
import android.view.MenuItem;
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
import java.util.ArrayList;
import java.util.Date;

public class Update_Create_event extends AppCompatActivity {


    private Spinner Cat;
    private EditText Date;
    private EditText Time;
    private EditText Addey;
    private EditText Max;
    private EditText Title;
    private EditText Disc;

    private Button Update;

    private static final String TAG = "Update_Create_event";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();

    ArrayList<String> Catgorylist;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__create_event);

        Cat = (Spinner) findViewById(R.id.Category_SPINNER_update);
        Date = (EditText)findViewById(R.id.Date_txt_update);
        Time = (EditText)findViewById(R.id.time_txt_update);
        Addey = (EditText)findViewById(R.id.address_txt_update);
        Max = (EditText)findViewById(R.id.max_people_txt_update);
        Title = (EditText)findViewById(R.id.Title_txt_update);
        Disc = (EditText) findViewById(R.id.des_txt_update);

      final EditText Addeyy = (EditText)findViewById(R.id.address_txt_update);
      final EditText Maxy = (EditText)findViewById(R.id.max_people_txt_update);
      final EditText Titley = (EditText)findViewById(R.id.Title_txt_update);
      final EditText Discy = (EditText) findViewById(R.id.des_txt_update);

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
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                shoData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        Update = (Button)findViewById(R.id.Update_event_btn);
        Update.setFocusable(true);
        Update.setFocusableInTouchMode(true);///add this line
        Update.requestFocus();
        Update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String Die = Discy.getText().toString();
                String Day = Date.getText().toString();
                String Tim = Time.getText().toString();
                String ADd = Addeyy.getText().toString();
                String MAxppl = Maxy.getText().toString();
                String Cator = Cat.getSelectedItem().toString();
                String tie = Titley.getText().toString();

                if (!Cator.equals("") && !Day.equals("") && !Tim.equals("")) {
                    if (!tie.equals("")) {
                        if (!ADd.equals("")) {
                            if (!MAxppl.equals("")) {
                                if (!Die.equals("")) {
                                    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

                                    mRootRef.child(USerid).child("CreatedEvents").child(Event + EVENTCOUNT).child("EVENTNUMBER");

                                    mRootRef.child(USerid).child("CreatedEvents").child(Event + EVENTCOUNT).child("Description").setValue(Die);
                                    mRootRef.child(USerid).child("CreatedEvents").child(Event + EVENTCOUNT).child("Date").setValue(Day);
                                    mRootRef.child(USerid).child("CreatedEvents").child(Event + EVENTCOUNT).child("Time").setValue(Tim);
                                    mRootRef.child(USerid).child("CreatedEvents").child(Event + EVENTCOUNT).child("Address").setValue(ADd);
                                    mRootRef.child(USerid).child("CreatedEvents").child(Event + EVENTCOUNT).child("Max_People").setValue(MAxppl);
                                    mRootRef.child(USerid).child("CreatedEvents").child(Event + EVENTCOUNT).child("Title").setValue(tie);
                                    mRootRef.child(USerid).child("CreatedEvents").child(Event + EVENTCOUNT).child("Category").setValue(Cator);


                                    mRootRef.child("Events").child(Event + updatecount).child("Description").setValue(Die);
                                    mRootRef.child("Events").child(Event + updatecount).child("Date").setValue(Day);
                                    mRootRef.child("Events").child(Event + updatecount).child("Time").setValue(Tim);
                                    mRootRef.child("Events").child(Event + updatecount).child("Address").setValue(ADd);
                                    mRootRef.child("Events").child(Event + updatecount).child("Max_People").setValue(MAxppl);
                                    mRootRef.child("Events").child(Event + updatecount).child("Title").setValue(tie);
                                    mRootRef.child("Events").child(Event + updatecount).child("Category").setValue(Cator);


                                    Intent changepage = new Intent(Update_Create_event.this, CreatedEventList.class);
                                    startActivity(changepage);
                                    Toast.makeText(Update_Create_event.this, "Event Has Been Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Discy.setError("Description can not be empty");
                                    Discy.requestFocus();
                                }
                            } else {
                                Maxy.setError("Max People can not be empty");
                                Maxy.requestFocus();
                            }
                        } else {
                            Addeyy.setError("Address can not be empty");
                            Addeyy.requestFocus();
                        }
                    } else {
                        Titley.setError("Title can not be empty");
                        Titley.requestFocus();
                    }
                } else {
                    Titley.setError("Missing some information");
                    Titley.requestFocus();
                }
            }

        });


        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
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
                int x = 0;
                for (int i = 0; i < Catgorylist.size(); i++) {
                    String checker = Catgorylist.get(i);
                    if (checker.equals(temp)){
                        x = i;
                        break;
                    }
                }


                Cat.setSelection(x);
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

    private void shoData(DataSnapshot dataSnapshot) {

        Catgorylist = new ArrayList<>();

        boolean SArcy     = dataSnapshot.child(USerid).child("Interests").child("sarchery").getValue(boolean.class).booleanValue();
        boolean SBasy     = dataSnapshot.child(USerid).child("Interests").child("sbaseball").getValue(boolean.class).booleanValue();
        boolean SbKy      = dataSnapshot.child(USerid).child("Interests").child("sbasketball").getValue(boolean.class).booleanValue();
        boolean Cycy      = dataSnapshot.child(USerid).child("Interests").child("scycling").getValue(boolean.class).booleanValue();
        boolean Fish      = dataSnapshot.child(USerid).child("Interests").child("sfishing").getValue(boolean.class).booleanValue();
        boolean Footy     = dataSnapshot.child(USerid).child("Interests").child("sfootball").getValue(boolean.class).booleanValue();
        boolean Frisy     = dataSnapshot.child(USerid).child("Interests").child("sfrisbe").getValue(boolean.class).booleanValue();
        boolean SGofy     = dataSnapshot.child(USerid).child("Interests").child("sgolf").getValue(boolean.class).booleanValue();
        boolean Shockeyy  = dataSnapshot.child(USerid).child("Interests").child("shoccey").getValue(boolean.class).booleanValue();
        boolean SHunty    = dataSnapshot.child(USerid).child("Interests").child("shunting").getValue(boolean.class).booleanValue();
        boolean SSKatey   = dataSnapshot.child(USerid).child("Interests").child("sskateboarding").getValue(boolean.class).booleanValue();
        boolean SSnowy    = dataSnapshot.child(USerid).child("Interests").child("ssnowBoarding").getValue(boolean.class).booleanValue();
        boolean Swsy      = dataSnapshot.child(USerid).child("Interests").child("swaterSports").getValue(boolean.class).booleanValue();
        boolean Wrey      = dataSnapshot.child(USerid).child("Interests").child("swrestling").getValue(boolean.class).booleanValue();
        boolean Fesy      = dataSnapshot.child(USerid).child("Interests").child("pfestivles").getValue(boolean.class).booleanValue();
        boolean Housy     = dataSnapshot.child(USerid).child("Interests").child("phouseParites").getValue(boolean.class).booleanValue();
        boolean Nighty    = dataSnapshot.child(USerid).child("Interests").child("pnightClubs").getValue(boolean.class).booleanValue();
        boolean Gacty     = dataSnapshot.child(USerid).child("Interests").child("gaction").getValue(boolean.class).booleanValue();
        boolean Gadvy     = dataSnapshot.child(USerid).child("Interests").child("gadventure").getValue(boolean.class).booleanValue();
        boolean GFpy      = dataSnapshot.child(USerid).child("Interests").child("gfps").getValue(boolean.class).booleanValue();
        boolean Gindy     = dataSnapshot.child(USerid).child("Interests").child("gindies").getValue(boolean.class).booleanValue();
        boolean GMMy      = dataSnapshot.child(USerid).child("Interests").child("gmmo").getValue(boolean.class).booleanValue();
        boolean GpaFy     = dataSnapshot.child(USerid).child("Interests").child("gpartyfamily").getValue(boolean.class).booleanValue();
        boolean GRPy      = dataSnapshot.child(USerid).child("Interests").child("grpg").getValue(boolean.class).booleanValue();
        boolean Gsiy      = dataSnapshot.child(USerid).child("Interests").child("gsimulation").getValue(boolean.class).booleanValue();
        boolean Gspy      = dataSnapshot.child(USerid).child("Interests").child("gsports").getValue(boolean.class).booleanValue();
        boolean GStry     = dataSnapshot.child(USerid).child("Interests").child("gstragy").getValue(boolean.class).booleanValue();
        boolean MCy       = dataSnapshot.child(USerid).child("Interests").child("mcountry").getValue(boolean.class).booleanValue();
        boolean MDRy      = dataSnapshot.child(USerid).child("Interests").child("mdrillrap").getValue(boolean.class).booleanValue();
        boolean MEdy      = dataSnapshot.child(USerid).child("Interests").child("medm").getValue(boolean.class).booleanValue();
        boolean MJzy      = dataSnapshot.child(USerid).child("Interests").child("mjazz").getValue(boolean.class).booleanValue();
        boolean MRpy      = dataSnapshot.child(USerid).child("Interests").child("mrap").getValue(boolean.class).booleanValue();
        boolean Mroy      = dataSnapshot.child(USerid).child("Interests").child("mrock").getValue(boolean.class).booleanValue();
        boolean MRNy      = dataSnapshot.child(USerid).child("Interests").child("mrnb").getValue(boolean.class).booleanValue();
        boolean MScry     = dataSnapshot.child(USerid).child("Interests").child("mscremo").getValue(boolean.class).booleanValue();
        boolean MoActy    = dataSnapshot.child(USerid).child("Interests").child("moAction").getValue(boolean.class).booleanValue();
        boolean MOAniy    = dataSnapshot.child(USerid).child("Interests").child("moAnimation").getValue(boolean.class).booleanValue();
        boolean MOComy    = dataSnapshot.child(USerid).child("Interests").child("moComdey").getValue(boolean.class).booleanValue();
        boolean MODoy     = dataSnapshot.child(USerid).child("Interests").child("moDocumentary").getValue(boolean.class).booleanValue();
        boolean MOFy      = dataSnapshot.child(USerid).child("Interests").child("moFamily").getValue(boolean.class).booleanValue();
        boolean MOHOry    = dataSnapshot.child(USerid).child("Interests").child("moHorror").getValue(boolean.class).booleanValue();
        boolean MoMusy    = dataSnapshot.child(USerid).child("Interests").child("moMusical").getValue(boolean.class).booleanValue();
        boolean MOSiy     = dataSnapshot.child(USerid).child("Interests").child("moSifi").getValue(boolean.class).booleanValue();
        boolean MOSpoy    = dataSnapshot.child(USerid).child("Interests").child("moSports").getValue(boolean.class).booleanValue();
        boolean MOTHrily  = dataSnapshot.child(USerid).child("Interests").child("moThriller").getValue(boolean.class).booleanValue();
        boolean MoWay     = dataSnapshot.child(USerid).child("Interests").child("moWar").getValue(boolean.class).booleanValue();
        boolean TActy     = dataSnapshot.child(USerid).child("Interests").child("taction").getValue(boolean.class).booleanValue();
        boolean TADvy     = dataSnapshot.child(USerid).child("Interests").child("tadventure").getValue(boolean.class).booleanValue();
        boolean TAniy     = dataSnapshot.child(USerid).child("Interests").child("tanimation").getValue(boolean.class).booleanValue();
        boolean TBioy     = dataSnapshot.child(USerid).child("Interests").child("tbiography").getValue(boolean.class).booleanValue();
        boolean TCom      = dataSnapshot.child(USerid).child("Interests").child("tcomedy").getValue(boolean.class).booleanValue();
        boolean TCriy     = dataSnapshot.child(USerid).child("Interests").child("tcrime").getValue(boolean.class).booleanValue();
        boolean TDoy      = dataSnapshot.child(USerid).child("Interests").child("tdocoumentary").getValue(boolean.class).booleanValue();
        boolean TDray     = dataSnapshot.child(USerid).child("Interests").child("tdrama").getValue(boolean.class).booleanValue();
        boolean Tfay      = dataSnapshot.child(USerid).child("Interests").child("tfamily").getValue(boolean.class).booleanValue();
        boolean TGamey    = dataSnapshot.child(USerid).child("Interests").child("tgameShows").getValue(boolean.class).booleanValue();
        boolean THisy     = dataSnapshot.child(USerid).child("Interests").child("thistory").getValue(boolean.class).booleanValue();
        boolean Thory     = dataSnapshot.child(USerid).child("Interests").child("thorror").getValue(boolean.class).booleanValue();
        boolean TMysy     = dataSnapshot.child(USerid).child("Interests").child("tmystery").getValue(boolean.class).booleanValue();
        boolean Trey      = dataSnapshot.child(USerid).child("Interests").child("treality").getValue(boolean.class).booleanValue();
        boolean Tsiy      = dataSnapshot.child(USerid).child("Interests").child("tsitcom").getValue(boolean.class).booleanValue();
        boolean TSpoy     = dataSnapshot.child(USerid).child("Interests").child("tsports").getValue(boolean.class).booleanValue();
        boolean TTalky    = dataSnapshot.child(USerid).child("Interests").child("ttalkShows").getValue(boolean.class).booleanValue();
        boolean Tway      = dataSnapshot.child(USerid).child("Interests").child("twar").getValue(boolean.class).booleanValue();
        boolean Dacty     = dataSnapshot.child(USerid).child("Interests").child("dacting").getValue(boolean.class).booleanValue();
        boolean Dcosy     = dataSnapshot.child(USerid).child("Interests").child("dcosplay").getValue(boolean.class).booleanValue();
        boolean Dlay      = dataSnapshot.child(USerid).child("Interests").child("dlarping").getValue(boolean.class).booleanValue();
        boolean CActy     = dataSnapshot.child(USerid).child("Interests").child("cactionfigures").getValue(boolean.class).booleanValue();
        boolean CCry      = dataSnapshot.child(USerid).child("Interests").child("ccars").getValue(boolean.class).booleanValue();
        boolean Ccinsy    = dataSnapshot.child(USerid).child("Interests").child("ccoins").getValue(boolean.class).booleanValue();
        boolean Ccomy     = dataSnapshot.child(USerid).child("Interests").child("ccomics").getValue(boolean.class).booleanValue();
        boolean CGuny     = dataSnapshot.child(USerid).child("Interests").child("cguns").getValue(boolean.class).booleanValue();
        boolean Ctrcy     = dataSnapshot.child(USerid).child("Interests").child("ctrucks").getValue(boolean.class).booleanValue();


        if (SArcy){
            Catgorylist.add("Archery");
        }

        if (SBasy){
            Catgorylist.add("Baseball");
        }

        if (SbKy){
            Catgorylist.add("Basketball");
        }

        if (Cycy){
            Catgorylist.add("Bicycle");
        }

        if (Fish){
            Catgorylist.add("Fishing");
        }

        if (Footy){
            Catgorylist.add("Football");
        }

        if (Frisy){
            Catgorylist.add("Frisbe");
        }

        if (SGofy){
            Catgorylist.add("Golf");
        }

        if (Shockeyy){
            Catgorylist.add("Hockey");
        }

        if (SHunty){
            Catgorylist.add("Hunting");
        }

        if (SSKatey){
            Catgorylist.add("Skateboarding");
        }

        if (SSnowy){
            Catgorylist.add("Snowboarding");
        }

        if (Swsy){
            Catgorylist.add("Water Sports");
        }

        if (Wrey){
            Catgorylist.add("Wrestling");
        }

        if (Fesy){
            Catgorylist.add("Festival");
        }

        if (Housy){
            Catgorylist.add("House Party");
        }

        if (Nighty){
            Catgorylist.add("Night Club");
        }

        if (Gacty){
            Catgorylist.add("Action Game");
        }

        if (Gadvy){
            Catgorylist.add("Adventure Game");
        }

        if (GFpy){
            Catgorylist.add("FPS Game");
        }

        if (Gindy){
            Catgorylist.add("Indie Game");
        }

        if (GMMy){
            Catgorylist.add("MMO Game");
        }

        if (GpaFy){
            Catgorylist.add("Party Game");
        }

        if (GRPy){
            Catgorylist.add("RPG Game");
        }

        if (Gsiy){
            Catgorylist.add("Simulation Game");
        }

        if (Gspy){
            Catgorylist.add("Sports Game");
        }

        if (GStry){
            Catgorylist.add("Stragey Game");
        }

        if (MCy){
            Catgorylist.add("Country Music");
        }

        if (MDRy){
            Catgorylist.add("Drill Rap");
        }

        if (MEdy){
            Catgorylist.add("EDM");
        }

        if (MJzy){
            Catgorylist.add("Jazz");
        }

        if (MRpy){
            Catgorylist.add("Rap");
        }

        if (Mroy){
            Catgorylist.add("Rock");
        }

        if (MRNy){
            Catgorylist.add("RNB");
        }

        if (MScry){
            Catgorylist.add("Scremo");
        }

        if (MoActy){
            Catgorylist.add("Action Movie");
        }

        if (MOAniy){
            Catgorylist.add("Animation Movie");
        }

        if (MOComy){
            Catgorylist.add("Comdey Movie");
        }

        if (MODoy){
            Catgorylist.add("Documentary Movie");
        }

        if (MOFy){
            Catgorylist.add("Family Movie");
        }

        if (MOHOry){
            Catgorylist.add("Horror Movie");
        }

        if (MoMusy){
            Catgorylist.add("Musical Movie");
        }

        if (MOSiy){
            Catgorylist.add("Sifi Movie");
        }

        if (MOSpoy){
            Catgorylist.add("Sports Movie");
        }

        if (MOTHrily){
            Catgorylist.add("Thriller Movie");
        }

        if (MoWay){
            Catgorylist.add("War Movie");
        }

        if (TActy){
            Catgorylist.add("Action Shows");
        }

        if (TADvy){
            Catgorylist.add("Adventure Shows");
        }

        if (TAniy){
            Catgorylist.add("Animation Shows");
        }

        if (TBioy){
            Catgorylist.add("Biography Shows");
        }

        if (TCom){
            Catgorylist.add("Comedy Shows");
        }

        if (TCriy){
            Catgorylist.add("Crime Shows");
        }

        if (TDoy){
            Catgorylist.add("Documentary Shows");
        }

        if (TDray){
            Catgorylist.add("Drama Shows");
        }

        if (Tfay){
            Catgorylist.add("Family Shows");
        }

        if (TGamey){
            Catgorylist.add("Game Shows");
        }

        if (THisy){
            Catgorylist.add("History Shows");
        }

        if (Thory){
            Catgorylist.add("Horror Shows");
        }

        if (TMysy){
            Catgorylist.add("Mystery Shows");
        }

        if (Trey){
            Catgorylist.add("Reality Shows");
        }

        if (Tsiy){
            Catgorylist.add("Sifi Shows");
        }

        if (TSpoy){
            Catgorylist.add("Sports Shows");
        }

        if (TTalky){
            Catgorylist.add("Talk Shows");
        }

        if (Tway){
            Catgorylist.add("War Shows");
        }

        if (Dacty){
            Catgorylist.add("Acting");
        }

        if (Dcosy){
            Catgorylist.add("Cosplay");
        }

        if (Dlay){
            Catgorylist.add("Larping");
        }

        if (CActy){
            Catgorylist.add("Action Figures");
        }

        if (CCry){
            Catgorylist.add("Cars");
        }

        if (Ccinsy){
            Catgorylist.add("Coins");
        }

        if (Ccomy){
            Catgorylist.add("Comics");
        }

        if (CGuny){
            Catgorylist.add("Guns");
        }

        if (Ctrcy){
            Catgorylist.add("Trucks");
        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Catgorylist);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cat.setAdapter(myAdapter);

    }
}

