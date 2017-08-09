package domain.teamgroupley.groupleyapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PeopleAttending extends AppCompatActivity {

    ListView peopleattendingevent;

    private static final String TAG = "PeopleAttending";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    private List<String> Userseverywhere = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_attending);

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

        peopleattendingevent = (ListView)findViewById(R.id.People_listView);;



    }

    private void showData(DataSnapshot dataSnapshot) {
        int counter = 1;
        String Event = "Event";
        String username;
        int evenum = Description.desnum;

        for (DataSnapshot ds : dataSnapshot.child("Events").child(Event + evenum).child("People").getChildren()) {

                username = dataSnapshot.child("Events").child(Event + evenum).child("People").child("Person" + counter).getValue(String.class).toString();
                Userseverywhere.add(username);
                ++counter;
            }
        ListAdapter listviewadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,Userseverywhere);
        peopleattendingevent.setAdapter(listviewadapter);
    }
}
