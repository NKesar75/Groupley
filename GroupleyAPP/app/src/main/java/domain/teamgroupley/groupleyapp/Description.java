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
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private EditText Titl;
    private EditText Descrip;
    private EditText Cater;
    private EditText Dat;
    private EditText Tim;
    private EditText Add;
    private EditText Maxppl;
    private String UserID;
    private Button Join;

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
        FirebaseUser user = mAuth.getCurrentUser();
        UserID = user.getUid();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
                ShowData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(Description.this,join.class);
                startActivity(changepage2);
            }
        });
    }

    private void ShowData(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.getChildren() ){
            CreateEventStatsinfo show = new CreateEventStatsinfo();
            show.SetTitle(ds.child(UserID).getValue(CreateEventStatsinfo.class).GetTitle());
            show.SetDescription(ds.child(UserID).getValue(CreateEventStatsinfo.class).GetDescription());
            show.SetCategory(ds.child(UserID).getValue(CreateEventStatsinfo.class).GetCategory());
            show.SetTime(ds.child(UserID).getValue(CreateEventStatsinfo.class).GetTime());
            show.SetDate(ds.child(UserID).getValue(CreateEventStatsinfo.class).GetDate());
            show.SetAddess(ds.child(UserID).getValue(CreateEventStatsinfo.class).GetAddess());
            show.SetMax_Peopl(ds.child(UserID).getValue(CreateEventStatsinfo.class).GetMax_People());

            Descrip.setText(show.GetDescription());
            Titl.setText(show.GetTitle());
            Cater.setText(show.GetCategory());
            Tim.setText(show.GetTime());
            Dat.setText(show.GetDate());
            Add.setText(show.GetAddess());
            Maxppl.setText(show.GetMax_People());
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
