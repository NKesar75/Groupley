package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    private Button Logout;
    private static final String TAG = "Profile";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String UserID = user.getUid();
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextView FirstName;
    private TextView LastName;
    private TextView Gender;
    private TextView DOB;
    private TextView username;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mFirstName = mRootRef.child(UserID).child("UserInfo").child("Firstname");
    DatabaseReference mLastName = mRootRef.child(UserID).child("UserInfo").child("Lastname");
    DatabaseReference mGender = mRootRef.child(UserID).child("UserInfo").child("Sex");
    DatabaseReference mDOB = mRootRef.child(UserID).child("UserInfo").child("DOB");
    DatabaseReference mUsername = mRootRef.child(UserID).child("UserInfo").child("UserName");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Logout = (Button) findViewById(R.id.logout_btn);
        FirstName = (TextView)findViewById(R.id.First_name_txtview);
        LastName = (TextView)findViewById(R.id.Last_Name_txtview);
        Gender = (TextView)findViewById(R.id.Gender_txtview);
        DOB = (TextView)findViewById(R.id.DOB_txtview);
        username = (TextView)findViewById(R.id.Username_txtview);

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
  }

    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        mFirstName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String first = dataSnapshot.getValue(String.class);
                FirstName.setText(first);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mLastName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String last = dataSnapshot.getValue(String.class);
                LastName.setText(last);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mGender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gen = dataSnapshot.getValue(String.class);
                Gender.setText(gen);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDOB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String date = dataSnapshot.getValue(String.class);
                DOB.setText(date);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mUsername.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user = dataSnapshot.getValue(String.class);
                username.setText(user);
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