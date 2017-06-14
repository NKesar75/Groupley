package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfoStats extends AppCompatActivity {

    private static final String TAG = "UserInfoStats";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    public Button Create;
    public EditText FirstName;
    public EditText LastName;
    public EditText Dateofbirth;
    public EditText Username;
    public Spinner Gender;


    public void CreateUSerinfo() {
        Create = (Button) findViewById(R.id.Create_User_stats_btn);
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String First = FirstName.getText().toString();

                String Last = LastName.getText().toString();

                String DOB = Dateofbirth.getText().toString();

                String User = Username.getText().toString();

                String Sex = Gender.getSelectedItem().toString();


                if (!First.equals("") && !Last.equals("") && !DOB.equals("") && !User.equals("") && !Sex.equals("")) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    Userinformaiton userinformaiton = new Userinformaiton(First, Last, DOB, User, Sex);
                    myRef.child(userID).setValue(userinformaiton);

                }
                else{
                    Toast.makeText(UserInfoStats.this, "Missing some information", Toast.LENGTH_SHORT).show();
                }
                Intent changepage = new Intent(UserInfoStats.this, MenuPage.class);
                startActivity(changepage);
            }
        });
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_stats);
        FirstName = (EditText)findViewById(R.id.First_NAME_Txt);
        LastName = (EditText) findViewById(R.id.LAST_NAME_txt);
        Dateofbirth = (EditText)findViewById(R.id.DOB_txt);
        Username = (EditText)findViewById(R.id.USERNAME_TXT);
        Gender = (Spinner) findViewById(R.id.GENDER_SPINNER);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(UserInfoStats.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Genders));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(myAdapter);

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
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        CreateUSerinfo();
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
