package domain.teamgroupley.groupleyapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static domain.teamgroupley.groupleyapp.R.id.nav_profile;

public class Create_Event extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText address;
    EditText Max;
    Button Create;
    Spinner Cat;
    EditText Date;
    EditText Time;

    private static final String TAG = "Create_Event";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();
    String username;

    private StorageReference storageReference;
    private ImageView imageView;
    private Uri imguri;
    private String defulaturi;

    public  static final int Request_Code = 1234;

    ArrayList<String> Catgorylist;

    long EVENTCOUNT;
    long CreatedEVENTCOUNT;
    long REGISTEREDEVENTCOUNT;
    String Event = "Event";

    String createUsername;

    private DatePickerDialog.OnDateSetListener mDateSetListner;

    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;

    @SuppressWarnings("VisibleForTests")
            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);

                Cat = (Spinner) findViewById(R.id.Category_SPINNER);
                Date = (EditText) findViewById(R.id.Date_txt);
                Time = (EditText) findViewById(R.id.time_txt);

                imageView =(ImageView) findViewById(R.id.image_load);
                defulaturi = "https://firebasestorage.googleapis.com/v0/b/groupleyproject.appspot.com/o/deflut.png?alt=media&token=41443f2c-7c29-4bd1-be7b-ec74167fc1ee";

        final EditText Title = (EditText) findViewById(R.id.Title_txt);
        final EditText Disc = (EditText) findViewById(R.id.des_txt);
        final EditText Addey  = (EditText) findViewById(R.id.address_txt);
        final EditText Max = (EditText) findViewById(R.id.max_people_txt);

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
        storageReference = FirebaseStorage.getInstance().getReference();
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

                 shoData(dataSnapshot);

                EVENTCOUNT = dataSnapshot.child("Events").getChildrenCount() + 1;
                CreatedEVENTCOUNT = dataSnapshot.child(userID).child("CreatedEvents").getChildrenCount() + 1;
                username = dataSnapshot.child(userID).child("UserInfo").child("UserName").getValue(String.class).toString();
                REGISTEREDEVENTCOUNT = dataSnapshot.child(userID).child("RegisteredEvents").getChildrenCount() + 1;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Create = (Button) findViewById(R.id.Create_event_btn);
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String tie = Title.getText().toString();
                final String Die = Disc.getText().toString();
                final String Cator = Cat.getSelectedItem().toString();
                final String Day = Date.getText().toString();
                final String Tim = Time.getText().toString();
                final String ADd = Addey.getText().toString();
                final String MAxppl = Max.getText().toString();



                if (!Cator.equals("") && !Day.equals("") && !Tim.equals("")) {
                    if (!tie.equals("")) {
                            if (!ADd.equals("")) {
                                if (!MAxppl.equals("")) {
                                    if (!Die.equals("")) {

                                        final StorageReference ref = storageReference.child(userID).child(Event + EVENTCOUNT).child(System.currentTimeMillis() + "." + getImageExt(imguri));

                                            ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                                {
                                                    Toast.makeText(getApplicationContext(), "Event Created", Toast.LENGTH_SHORT).show();
                                                    ImageUpload imageUpload = new ImageUpload(taskSnapshot.getDownloadUrl().toString());

                                                    myRef.child(userID).child("CreatedEvents").child(Event + CreatedEVENTCOUNT).child("Title").setValue(tie);
                                                    myRef.child(userID).child("CreatedEvents").child(Event + CreatedEVENTCOUNT).child("Description").setValue(Die);
                                                    myRef.child(userID).child("CreatedEvents").child(Event + CreatedEVENTCOUNT).child("Category").setValue(Cator);
                                                    myRef.child(userID).child("CreatedEvents").child(Event + CreatedEVENTCOUNT).child("Date").setValue(Day);
                                                    myRef.child(userID).child("CreatedEvents").child(Event + CreatedEVENTCOUNT).child("Time").setValue(Tim);
                                                    myRef.child(userID).child("CreatedEvents").child(Event + CreatedEVENTCOUNT).child("Address").setValue(ADd);
                                                    myRef.child(userID).child("CreatedEvents").child(Event + CreatedEVENTCOUNT).child("Max_People").setValue(MAxppl);
                                                    myRef.child(userID).child("CreatedEvents").child(Event + CreatedEVENTCOUNT).child("EVENTNUMBER").setValue(EVENTCOUNT);
                                                    myRef.child(userID).child("CreatedEvents").child(Event + CreatedEVENTCOUNT).child("Image").setValue(imageUpload);

                                                    myRef.child("Events").child(Event + EVENTCOUNT).child("Title").setValue(tie);
                                                    myRef.child("Events").child(Event + EVENTCOUNT).child("Description").setValue(Die);
                                                    myRef.child("Events").child(Event + EVENTCOUNT).child("Category").setValue(Cator);
                                                    myRef.child("Events").child(Event + EVENTCOUNT).child("Date").setValue(Day);
                                                    myRef.child("Events").child(Event + EVENTCOUNT).child("Time").setValue(Tim);
                                                    myRef.child("Events").child(Event + EVENTCOUNT).child("Address").setValue(ADd);
                                                    myRef.child("Events").child(Event + EVENTCOUNT).child("Max_People").setValue(MAxppl);
                                                    myRef.child("Events").child(Event + EVENTCOUNT).child("EVENTNUMBER").setValue(EVENTCOUNT);
                                                    myRef.child("Events").child(Event + EVENTCOUNT).child("Image").setValue(imageUpload);

                                                    myRef.child("Events").child(Event + EVENTCOUNT).child("People").child("Person1").child("Name").setValue(username);
                                                    myRef.child("Events").child(Event + EVENTCOUNT).child("People").child("Person1").child("Photo").setValue(" ");
                                                    myRef.child("Events").child(Event + EVENTCOUNT).child("People").child("Person1").child("FID").setValue(userID);

                                                    myRef.child(userID).child("RegisteredEvents").child(Event + REGISTEREDEVENTCOUNT).child("Title").setValue(tie);
                                                    myRef.child(userID).child("RegisteredEvents").child(Event + REGISTEREDEVENTCOUNT).child("Description").setValue(Die);
                                                    myRef.child(userID).child("RegisteredEvents").child(Event + REGISTEREDEVENTCOUNT).child("Category").setValue(Cator);
                                                    myRef.child(userID).child("RegisteredEvents").child(Event + REGISTEREDEVENTCOUNT).child("Date").setValue(Day);
                                                    myRef.child(userID).child("RegisteredEvents").child(Event + REGISTEREDEVENTCOUNT).child("Time").setValue(Tim);
                                                    myRef.child(userID).child("RegisteredEvents").child(Event + REGISTEREDEVENTCOUNT).child("Address").setValue(ADd);
                                                    myRef.child(userID).child("RegisteredEvents").child(Event + REGISTEREDEVENTCOUNT).child("Max_People").setValue(MAxppl);
                                                    myRef.child(userID).child("RegisteredEvents").child(Event + REGISTEREDEVENTCOUNT).child("EVENTNUMBER").setValue(EVENTCOUNT);
                                                    myRef.child(userID).child("RegisteredEvents").child(Event + REGISTEREDEVENTCOUNT).child("Image").setValue(imageUpload);

                                                    if (imguri != null) {}
                                                    else{}


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            Intent changepage = new Intent(Create_Event.this, Home.class);
                                            startActivity(changepage);
                                            Toast.makeText(Create_Event.this, "Event Has Been Created", Toast.LENGTH_SHORT).show();

                                    }else{
                                        Disc.setError("Description can not be empty");
                                        Disc.requestFocus();
                                    }
                            }
                                else{
                                    Max.setError("Max People can not be empty");
                                    Max.requestFocus();
                                }
                        }
                        else{
                                Addey.setError("Address can not be empty");
                                Addey.requestFocus();
                            }
                    }
                        else {
                        Title.setError("Title can not be empty");
                        Title.requestFocus();
                            }
                    }
                else {
                    Title.setError("Missing some information");
                    Title.requestFocus();
                }
            }
        });

        draw = (DrawerLayout)findViewById(R.id.activity_Create_Event);
        toggle = new ActionBarDrawerToggle(this,draw,R.string.open,R.string.close);

        draw.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigation = (NavigationView)findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);

    }

    public void chooseimg_btn(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SelectImage"),Request_Code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imguri = data.getData();
            try
            {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),imguri);
                imageView.setImageBitmap(bm);
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

//    @SuppressWarnings("VisibleForTests")
//    public void btnUpload(View v)
//    {
//        if(imguri != null)
//        {
//            final ProgressDialog dialog = new ProgressDialog(this);
//            dialog.setTitle("Uploading Image.......");
//            dialog.show();
//
//            StorageReference ref = storageReference.child(FB_STORAGE_PATH+System.currentTimeMillis()+ "." + getImageExt(imguri));
//            ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
//                {
//                    dialog.dismiss();
//                    Toast.makeText(getApplicationContext(), "Event Created", Toast.LENGTH_SHORT).show();
//                    ImageUpload imageUpload = new ImageUpload(taskSnapshot.getDownloadUrl().toString());
//
//                    String uploadurl = myRef.push().getKey();
//                    myRef.child(uploadurl).setValue(imageUpload);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    dialog.dismiss();
//                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = (100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getBytesTransferred();
//                    dialog.setMessage("Creating Event...." + (int)progress + "%");
//
//                }
//            });
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_SHORT).show();
//        }
//    }


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
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_home:
                return true;
            case R.id.nav_Events:
                return true;
            case R.id.nav_create_event:
                return true;
            case R.id.nav_profile:
                return true;
            case R.id.nav_settings:
                return true;
            case R.id.nav_your_event:
                return true;
        }
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_Create_Event);
        if(drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        if(id==R.id.nav_home)
        {
            startActivity(new Intent(Create_Event.this,Home.class));
        }
        else if(id==R.id.nav_Events)
        {
            startActivity(new Intent(Create_Event.this,Registered_Events.class));
        }
        else if(id == R.id.nav_create_event)
        {
            DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_Create_Event);
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        else if(id== nav_profile)
        {
            startActivity(new Intent(Create_Event.this,Profile.class));

        }
        else if(id==R.id.nav_settings)
        {
            startActivity(new Intent(Create_Event.this,Settings.class));
        }
        else if(id==R.id.nav_your_event)
        {
            startActivity(new Intent(Create_Event.this,CreatedEventList.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_Create_Event);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shoData(DataSnapshot dataSnapshot) {

        createUsername = dataSnapshot.child(userID).child("UserInfo").child("UserName").getValue(String.class).toString();

        Catgorylist = new ArrayList<>();

        boolean SArcy     = dataSnapshot.child(userID).child("Interests").child("sarchery").getValue(boolean.class).booleanValue();
        boolean SBasy     = dataSnapshot.child(userID).child("Interests").child("sbaseball").getValue(boolean.class).booleanValue();
        boolean SbKy      = dataSnapshot.child(userID).child("Interests").child("sbasketball").getValue(boolean.class).booleanValue();
        boolean Cycy      = dataSnapshot.child(userID).child("Interests").child("scycling").getValue(boolean.class).booleanValue();
        boolean Fish      = dataSnapshot.child(userID).child("Interests").child("sfishing").getValue(boolean.class).booleanValue();
        boolean Footy     = dataSnapshot.child(userID).child("Interests").child("sfootball").getValue(boolean.class).booleanValue();
        boolean Frisy     = dataSnapshot.child(userID).child("Interests").child("sfrisbe").getValue(boolean.class).booleanValue();
        boolean SGofy     = dataSnapshot.child(userID).child("Interests").child("sgolf").getValue(boolean.class).booleanValue();
        boolean Shockeyy  = dataSnapshot.child(userID).child("Interests").child("shoccey").getValue(boolean.class).booleanValue();
        boolean SHunty    = dataSnapshot.child(userID).child("Interests").child("shunting").getValue(boolean.class).booleanValue();
        boolean SSKatey   = dataSnapshot.child(userID).child("Interests").child("sskateboarding").getValue(boolean.class).booleanValue();
        boolean SSnowy    = dataSnapshot.child(userID).child("Interests").child("ssnowBoarding").getValue(boolean.class).booleanValue();
        boolean Swsy      = dataSnapshot.child(userID).child("Interests").child("swaterSports").getValue(boolean.class).booleanValue();
        boolean Wrey      = dataSnapshot.child(userID).child("Interests").child("swrestling").getValue(boolean.class).booleanValue();
        boolean Fesy      = dataSnapshot.child(userID).child("Interests").child("pfestivles").getValue(boolean.class).booleanValue();
        boolean Housy     = dataSnapshot.child(userID).child("Interests").child("phouseParites").getValue(boolean.class).booleanValue();
        boolean Nighty    = dataSnapshot.child(userID).child("Interests").child("pnightClubs").getValue(boolean.class).booleanValue();
        boolean Gacty     = dataSnapshot.child(userID).child("Interests").child("gaction").getValue(boolean.class).booleanValue();
        boolean Gadvy     = dataSnapshot.child(userID).child("Interests").child("gadventure").getValue(boolean.class).booleanValue();
        boolean GFpy      = dataSnapshot.child(userID).child("Interests").child("gfps").getValue(boolean.class).booleanValue();
        boolean Gindy     = dataSnapshot.child(userID).child("Interests").child("gindies").getValue(boolean.class).booleanValue();
        boolean GMMy      = dataSnapshot.child(userID).child("Interests").child("gmmo").getValue(boolean.class).booleanValue();
        boolean GpaFy     = dataSnapshot.child(userID).child("Interests").child("gpartyfamily").getValue(boolean.class).booleanValue();
        boolean GRPy      = dataSnapshot.child(userID).child("Interests").child("grpg").getValue(boolean.class).booleanValue();
        boolean Gsiy      = dataSnapshot.child(userID).child("Interests").child("gsimulation").getValue(boolean.class).booleanValue();
        boolean Gspy      = dataSnapshot.child(userID).child("Interests").child("gsports").getValue(boolean.class).booleanValue();
        boolean GStry     = dataSnapshot.child(userID).child("Interests").child("gstragy").getValue(boolean.class).booleanValue();
        boolean MCy       = dataSnapshot.child(userID).child("Interests").child("mcountry").getValue(boolean.class).booleanValue();
        boolean MDRy      = dataSnapshot.child(userID).child("Interests").child("mdrillrap").getValue(boolean.class).booleanValue();
        boolean MEdy      = dataSnapshot.child(userID).child("Interests").child("medm").getValue(boolean.class).booleanValue();
        boolean MJzy      = dataSnapshot.child(userID).child("Interests").child("mjazz").getValue(boolean.class).booleanValue();
        boolean MRpy      = dataSnapshot.child(userID).child("Interests").child("mrap").getValue(boolean.class).booleanValue();
        boolean Mroy      = dataSnapshot.child(userID).child("Interests").child("mrock").getValue(boolean.class).booleanValue();
        boolean MRNy      = dataSnapshot.child(userID).child("Interests").child("mrnb").getValue(boolean.class).booleanValue();
        boolean MScry     = dataSnapshot.child(userID).child("Interests").child("mscremo").getValue(boolean.class).booleanValue();
        boolean MoActy    = dataSnapshot.child(userID).child("Interests").child("moAction").getValue(boolean.class).booleanValue();
        boolean MOAniy    = dataSnapshot.child(userID).child("Interests").child("moAnimation").getValue(boolean.class).booleanValue();
        boolean MOComy    = dataSnapshot.child(userID).child("Interests").child("moComdey").getValue(boolean.class).booleanValue();
        boolean MODoy     = dataSnapshot.child(userID).child("Interests").child("moDocumentary").getValue(boolean.class).booleanValue();
        boolean MOFy      = dataSnapshot.child(userID).child("Interests").child("moFamily").getValue(boolean.class).booleanValue();
        boolean MOHOry    = dataSnapshot.child(userID).child("Interests").child("moHorror").getValue(boolean.class).booleanValue();
        boolean MoMusy    = dataSnapshot.child(userID).child("Interests").child("moMusical").getValue(boolean.class).booleanValue();
        boolean MOSiy     = dataSnapshot.child(userID).child("Interests").child("moSifi").getValue(boolean.class).booleanValue();
        boolean MOSpoy    = dataSnapshot.child(userID).child("Interests").child("moSports").getValue(boolean.class).booleanValue();
        boolean MOTHrily  = dataSnapshot.child(userID).child("Interests").child("moThriller").getValue(boolean.class).booleanValue();
        boolean MoWay     = dataSnapshot.child(userID).child("Interests").child("moWar").getValue(boolean.class).booleanValue();
        boolean TActy     = dataSnapshot.child(userID).child("Interests").child("taction").getValue(boolean.class).booleanValue();
        boolean TADvy     = dataSnapshot.child(userID).child("Interests").child("tadventure").getValue(boolean.class).booleanValue();
        boolean TAniy     = dataSnapshot.child(userID).child("Interests").child("tanimation").getValue(boolean.class).booleanValue();
        boolean TBioy     = dataSnapshot.child(userID).child("Interests").child("tbiography").getValue(boolean.class).booleanValue();
        boolean TCom      = dataSnapshot.child(userID).child("Interests").child("tcomedy").getValue(boolean.class).booleanValue();
        boolean TCriy     = dataSnapshot.child(userID).child("Interests").child("tcrime").getValue(boolean.class).booleanValue();
        boolean TDoy      = dataSnapshot.child(userID).child("Interests").child("tdocoumentary").getValue(boolean.class).booleanValue();
        boolean TDray     = dataSnapshot.child(userID).child("Interests").child("tdrama").getValue(boolean.class).booleanValue();
        boolean Tfay      = dataSnapshot.child(userID).child("Interests").child("tfamily").getValue(boolean.class).booleanValue();
        boolean TGamey    = dataSnapshot.child(userID).child("Interests").child("tgameShows").getValue(boolean.class).booleanValue();
        boolean THisy     = dataSnapshot.child(userID).child("Interests").child("thistory").getValue(boolean.class).booleanValue();
        boolean Thory     = dataSnapshot.child(userID).child("Interests").child("thorror").getValue(boolean.class).booleanValue();
        boolean TMysy     = dataSnapshot.child(userID).child("Interests").child("tmystery").getValue(boolean.class).booleanValue();
        boolean Trey      = dataSnapshot.child(userID).child("Interests").child("treality").getValue(boolean.class).booleanValue();
        boolean Tsiy      = dataSnapshot.child(userID).child("Interests").child("tsitcom").getValue(boolean.class).booleanValue();
        boolean TSpoy     = dataSnapshot.child(userID).child("Interests").child("tsports").getValue(boolean.class).booleanValue();
        boolean TTalky    = dataSnapshot.child(userID).child("Interests").child("ttalkShows").getValue(boolean.class).booleanValue();
        boolean Tway      = dataSnapshot.child(userID).child("Interests").child("twar").getValue(boolean.class).booleanValue();
        boolean Dacty     = dataSnapshot.child(userID).child("Interests").child("dacting").getValue(boolean.class).booleanValue();
        boolean Dcosy     = dataSnapshot.child(userID).child("Interests").child("dcosplay").getValue(boolean.class).booleanValue();
        boolean Dlay      = dataSnapshot.child(userID).child("Interests").child("dlarping").getValue(boolean.class).booleanValue();
        boolean CActy     = dataSnapshot.child(userID).child("Interests").child("cactionfigures").getValue(boolean.class).booleanValue();
        boolean CCry      = dataSnapshot.child(userID).child("Interests").child("ccars").getValue(boolean.class).booleanValue();
        boolean Ccinsy    = dataSnapshot.child(userID).child("Interests").child("ccoins").getValue(boolean.class).booleanValue();
        boolean Ccomy     = dataSnapshot.child(userID).child("Interests").child("ccomics").getValue(boolean.class).booleanValue();
        boolean CGuny     = dataSnapshot.child(userID).child("Interests").child("cguns").getValue(boolean.class).booleanValue();
        boolean Ctrcy     = dataSnapshot.child(userID).child("Interests").child("ctrucks").getValue(boolean.class).booleanValue();


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
