package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
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


    private static final String TAG = "PeopleAttending";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listview;
    private GridView gridView;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    private List<Product> peopleList = new ArrayList<>();
    private int currentViewMode = 0;

    static final int VIEW_MODE_LISTVIEW_PEOPLE = 0;
    static final int VIEW_MODE_GRIDVIEW_PEOPLE = 1;


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

        stubList = (ViewStub) findViewById(R.id.stub_list);
        stubGrid = (ViewStub) findViewById(R.id.stub_grid);

        //inflate viewstub before get view
        stubList.inflate();
        stubGrid.inflate();

        listview = (ListView) findViewById(R.id.my_listview);
        gridView = (GridView) findViewById(R.id.mygridview);

        //Get current view mode in share refrence
        SharedPreferences share = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = share.getInt("CurrentViewMode", VIEW_MODE_LISTVIEW_PEOPLE);

        switchView();


        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void showData(DataSnapshot dataSnapshot) {
        int counter = 1;
        String Event = "Event";
        String username;
        String mPhoto;
        int evenum = Description.desnum;

        peopleList.clear();

        for (DataSnapshot ds : dataSnapshot.child("Events").child(Event + evenum).child("People").getChildren()) {

                username = dataSnapshot.child("Events").child(Event + evenum).child("People").child("Person" + counter).child("Name").getValue(String.class).toString();
                mPhoto = dataSnapshot.child("Events").child(Event + evenum).child("People").child("Person" + counter).child("Photo").getValue(String.class).toString();
            String Date = "";
            String cat = "";
            int eventNum = 0;
                peopleList.add(new Product(username,Date,cat,mPhoto,eventNum));
                ++counter;
            }
            setAdapters();
    }

    private void switchView() {
        if (VIEW_MODE_LISTVIEW_PEOPLE == currentViewMode) {
            //display listview
            stubList.setVisibility(View.VISIBLE);
            //hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            stubList.setVisibility(View.GONE);
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }

    private void setAdapters() {
        listViewAdapter = new ListViewAdapter(this, R.layout.list_item, peopleList);
        gridViewAdapter = new GridViewAdapter(this, R.layout.griditem, peopleList);
        listview.setAdapter(listViewAdapter);
        gridView.setAdapter(gridViewAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_1_people:
                if (VIEW_MODE_LISTVIEW_PEOPLE == currentViewMode)
                    currentViewMode = VIEW_MODE_GRIDVIEW_PEOPLE;
                else
                    currentViewMode = VIEW_MODE_LISTVIEW_PEOPLE;

                //switch view
                switchView();
                //save view mode in share refrence
                SharedPreferences share = getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putInt("CurrentViewMode", currentViewMode);
                editor.commit();

                break;
        }

        if(item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.peoplemenu, menu);
        return super.onCreateOptionsMenu(menu);
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
