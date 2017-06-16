package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText Tim;
    private EditText Add;
    private EditText Maxppl;
    private Button Join;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mTitle = mRootRef.child(UserID).child("CreatedEvents").child("Title");
    DatabaseReference mDesc = mRootRef.child(UserID).child("CreatedEvents").child("Description");
    DatabaseReference mCater = mRootRef.child(UserID).child("CreatedEvents").child("Category");
    DatabaseReference mDate = mRootRef.child(UserID).child("CreatedEvents").child("Date");
    DatabaseReference mTime = mRootRef.child(UserID).child("CreatedEvents").child("Time");
    DatabaseReference mAddress = mRootRef.child(UserID).child("CreatedEvents").child("Address");
    DatabaseReference mMax = mRootRef.child(UserID).child("CreatedEvents").child("Max_People");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Descrip = (EditText)findViewById(R.id.des_txt_des);
        Cater = (EditText)findViewById(R.id.Catergory_txt_des);
        Dat = (EditText)findViewById(R.id.Date_txt_des);
        Tim = (EditText)findViewById(R.id.time_txt_des);
        Add = (EditText)findViewById(R.id.address_txt_des);
        Maxppl = (EditText)findViewById(R.id.max_people_txt_des);
        Titl = (EditText)findViewById(R.id.Title_txt_des);
        Join = (Button) findViewById(R.id.Join_event_btn_des);

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


        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(Description.this,join.class);
                startActivity(changepage2);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        mTitle.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class);
                Titl.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDesc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class);
                Descrip.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCater.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class);
                Cater.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class);
                Dat.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class);
                Tim.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAddress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class);
                Add.setText(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMax.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class);
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
