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

public class UpdateProfile extends AppCompatActivity {

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

    private Button Save;

    private static final String TAG = "UpdateProfile";
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Save = (Button)findViewById(R.id.EDIT_PROFILE_BTN_update);
        FirstName = (EditText)findViewById(R.id.First_NAME_Tst_update);
        LastName = (EditText)findViewById(R.id.LAST_NAME_tst_update);
        Gender =   (EditText)findViewById(R.id.Gender_TST_update);
        DOB =      (EditText)findViewById(R.id.DOB_tst_update);
        username = (EditText)findViewById(R.id.USERNAME_Tst_update);
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
                    Toast.makeText(UpdateProfile.this, "Signed Out", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String First = FirstName.getText().toString();
                String Last = LastName.getText().toString();
                String User = username.getText().toString();


                if (!First.equals("") && !Last.equals("") && !User.equals("")) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();

                    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                    mRootRef.child(userID).child("UserInfo").child("Firstname").setValue(First);
                    mRootRef.child(userID).child("UserInfo").child("Lastname").setValue(Last);
                    mRootRef.child(userID).child("UserInfo").child("UserName").setValue(User);

                    Intent changepage = new Intent(UpdateProfile.this, Create_Interest.class);
                    startActivity(changepage);
                }
                else{
                    Toast.makeText(UpdateProfile.this, "Missing some information", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(UpdateProfile.this,Profile.class));
                Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
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
