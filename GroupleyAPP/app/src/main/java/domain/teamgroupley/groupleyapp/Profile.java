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

public class Profile extends AppCompatActivity {

    private Button Logout;
    private static final String TAG = "Profile";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mListView = (ListView)findViewById(R.id.profile_details_list);

        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser usery = mAuth.getCurrentUser();
        userID = usery.getUid();

        Logout = (Button) findViewById(R.id.logout_btn);

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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
  }

    private void showData(DataSnapshot dataSnapshot)
    {

        for(DataSnapshot ds : dataSnapshot.getChildren())
        {
            ProfileDataReading info = new ProfileDataReading();
            info.setFirstName(ds.child(userID).child("UserInfo").getValue(ProfileDataReading.class).getFirstName());
            info.setLastName(ds.child(userID).child("UserInfo").getValue(ProfileDataReading.class).getLastName());
            info.setGender(ds.child(userID).child("UserInfo").getValue(ProfileDataReading.class).getGender());
            info.setDateofbirth(ds.child(userID).child("UserInfo").getValue(ProfileDataReading.class).getDateofbirth());
            info.setUsername(ds.child(userID).child("UserInfo").getValue(ProfileDataReading.class).getUsername());

            Log.d(TAG,"showData: Firstname:" + info.getFirstName());
            Log.d(TAG,"showData: Lastname:" + info.getLastName());
            Log.d(TAG,"showData: Sex:" + info.getGender());
            Log.d(TAG,"showData: DOB:" + info.getDateofbirth());
            Log.d(TAG,"showData: UserName:" + info.getUsername());

            ArrayList<String> array = new ArrayList<>();
            array.add("First Name: "+ info.getFirstName());
            array.add("Last Name: " + info.getLastName());
            array.add("Gender: " + info.getGender());
            array.add("Date of Birth: " + info.getDateofbirth());
            array.add("Username: " + info.getUsername());
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            mListView.setAdapter(adapter);
        }
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
}