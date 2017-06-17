package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class Profile extends AppCompatActivity {

    private EditText FirstName;
    private EditText LastName ;
    private EditText Gender;
    private EditText DOB;
    private EditText username ;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mFirstName = mRootRef.child(USerid).child("UserInfo").child("Firstname");
    DatabaseReference mLastName = mRootRef.child(USerid).child("UserInfo").child("Lastname");
    DatabaseReference mGender = mRootRef.child(USerid).child("UserInfo").child("Sex");
    DatabaseReference mDOB = mRootRef.child(USerid).child("UserInfo").child("DOB");
    DatabaseReference mUsername = mRootRef.child(USerid).child("UserInfo").child("UserName");


    private Button Logout;
    private Button Interest;
    private static final String TAG = "Profile";
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Logout = (Button) findViewById(R.id.logout_btn);
        Interest = (Button)findViewById(R.id.SEE_INTREST_PROFILE_BTN);
        FirstName = (EditText)findViewById(R.id.First_NAME_Tst);
        LastName = (EditText)findViewById(R.id.LAST_NAME_tst);
        Gender =   (EditText)findViewById(R.id.Gender_TST);
        DOB =      (EditText)findViewById(R.id.DOB_tst);
        username = (EditText)findViewById(R.id.USERNAME_Tst);
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
                    Toast.makeText(Profile.this, "Signed Out", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Profile.this,LoginUser.class));

            }

        });

        Interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,IntrestSelection.class));
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        mFirstName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Name = dataSnapshot.getValue(String.class);
                FirstName.setText(Name);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mLastName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String LName = dataSnapshot.getValue(String.class);
                LastName.setText(LName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDOB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                DOB.setText(Temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mGender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                Gender.setText(Temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ValueEventListener valueEventListener = mUsername.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                username.setText(Temp);
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