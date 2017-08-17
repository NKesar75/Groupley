package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Description extends AppCompatActivity {

    private static final String TAG = "Description";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    FirebaseUser user = mAuth.getCurrentUser();
    private String UserID = user.getUid();;
    private DatabaseReference myRef;

    private EditText Titl;
    private EditText Descrip;
    private EditText Cater;
    private EditText Dat;
    private EditText Timy;
    private EditText Add;
    private EditText Maxppl;
    private Button Join;
    private Button Peoplechanging;

    private int Eventtie = Home.EventTitle;
    public static int desnum;

    String Event = "Event";

   
    long number;

   
    long people;
   
    String username;
    String image;

    boolean checkifalreadythere = false;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mTitle = mRootRef.child("Events").child(Event+Eventtie).child("Title");
    DatabaseReference mDesc = mRootRef.child("Events").child(Event+Eventtie).child("Description");
    DatabaseReference mCater = mRootRef.child("Events").child(Event+Eventtie).child("Category");
    DatabaseReference mDate = mRootRef.child("Events").child(Event+Eventtie).child("Date");
    DatabaseReference mTime = mRootRef.child("Events").child(Event+Eventtie).child("Time");
    DatabaseReference mAddress = mRootRef.child("Events").child(Event+Eventtie).child("Address");
    DatabaseReference mMax = mRootRef.child("Events").child(Event+Eventtie).child("Max_People");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Descrip = (EditText)findViewById(R.id.des_txt_des);
        Cater = (EditText)findViewById(R.id.Catergory_txt_des);
        Dat = (EditText)findViewById(R.id.Date_txt_des);
        Timy = (EditText)findViewById(R.id.time_txt_des);
        Add = (EditText)findViewById(R.id.address_txt_des);
        Maxppl = (EditText)findViewById(R.id.max_people_txt_des);
        Titl = (EditText)findViewById(R.id.Title_txt_des);
        Join = (Button) findViewById(R.id.Join_event_btn_des);
        Peoplechanging = (Button)findViewById(R.id.Attending);

        Join.setFocusable(true);
        Join.setFocusableInTouchMode(true);///add this line
        Join.requestFocus();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
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
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Peoplechanging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desnum = Eventtie;
                startActivity(new Intent(Description.this, PeopleAttending.class));
            }
        });

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tie = Titl.getText().toString();
                String Die = Descrip.getText().toString();
                String Cator = Cater.getText().toString();
                String Day = Dat.getText().toString();
                String Tim = Timy.getText().toString();
                String ADd = Add.getText().toString();
                String MAxppl = Maxppl.getText().toString();

                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                if (!checkifalreadythere) {
                    myRef.child(userID).child("RegisteredEvents").child(Event + number).child("Title").setValue(tie);
                    myRef.child(userID).child("RegisteredEvents").child(Event + number).child("Description").setValue(Die);
                    myRef.child(userID).child("RegisteredEvents").child(Event + number).child("Category").setValue(Cator);
                    myRef.child(userID).child("RegisteredEvents").child(Event + number).child("Date").setValue(Day);
                    myRef.child(userID).child("RegisteredEvents").child(Event + number).child("Time").setValue(Tim);
                    myRef.child(userID).child("RegisteredEvents").child(Event + number).child("Address").setValue(ADd);
                    myRef.child(userID).child("RegisteredEvents").child(Event + number).child("Max_People").setValue(MAxppl);
                    myRef.child(userID).child("RegisteredEvents").child(Event + number).child("EVENTNUMBER").setValue(Eventtie);

                    myRef.child("Events").child(Event + Eventtie).child("People").child("Person" + people).child("Name").setValue(username);
                    myRef.child("Events").child(Event + Eventtie).child("People").child("Person" + people).child("Photo").setValue(image);
                    myRef.child("Events").child(Event + Eventtie).child("People").child("Person" + people).child("FID").setValue(UserID);

                    Toast.makeText(Description.this, "You Have Joined.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Description.this, Home.class));

                } else {
                    Toast.makeText(Description.this, "You Have Already Joined This Event.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Description.this, Home.class));
                }
            }
        });
        }


    private void showData(DataSnapshot dataSnapshot) {

            number = dataSnapshot.child(UserID).child("RegisteredEvents").getChildrenCount() + 1;
            people = dataSnapshot.child("Events").child(Event + Eventtie).child("People").getChildrenCount() + 1;
            username = dataSnapshot.child(UserID).child("UserInfo").child("UserName").getValue(String.class).toString();
            image = dataSnapshot.child(UserID).child("UserInfo").child("Image").child("url").getValue(String.class).toString();

            int counter = 1;

            for (DataSnapshot ds : dataSnapshot.child(UserID).child("RegisteredEvents").getChildren()) {
                int evnum = dataSnapshot.child(UserID).child("RegisteredEvents").child(Event + counter).child("EVENTNUMBER").getValue(int.class);
                if (evnum == Eventtie) {
                    checkifalreadythere = true;
                }
                ++counter;
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
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        mTitle.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class).toString();
                Titl.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDesc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class).toString();
                Descrip.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCater.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class).toString();
                Cater.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class).toString();
                Dat.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class).toString();
                Timy.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAddress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class).toString();
                Add.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMax.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class).toString();
                Maxppl.setText(temp);
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
}
