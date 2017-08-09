package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.ListView;
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

import static domain.teamgroupley.groupleyapp.R.id.nav_profile;


public class CreatedEventList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView mListView;
    private static final String TAG = "CreatedEventList";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String Userid = user.getUid();
    public String data;


    public static int CreateEventTitle;

    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listview;
    private GridView gridView;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    private List<Product> productList = new ArrayList<>();;
    private int currentViewMode = 0;

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;

    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_event_list);
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

        stubList = (ViewStub)findViewById(R.id.stub_list);
        stubGrid = (ViewStub)findViewById(R.id.stub_grid);

        //inflate viewstub before get view
        stubList.inflate();
        stubGrid.inflate();

        listview = (ListView)findViewById(R.id.my_listview);
        gridView = (GridView)findViewById(R.id.mygridview);

        //Get current view mode in share refrence
        SharedPreferences share = getSharedPreferences("ViewMode",MODE_PRIVATE);
        currentViewMode = share.getInt("CurrentViewMode",VIEW_MODE_LISTVIEW);

        //Register item lick
        listview.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();

        draw = (DrawerLayout)findViewById(R.id.activity_createdEventList);
        toggle = new ActionBarDrawerToggle(this,draw,R.string.open,R.string.close);

        draw.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigation = (NavigationView)findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);
    }

    private void switchView()
    {
        if(VIEW_MODE_LISTVIEW == currentViewMode)
        {
            //display listview
            stubList.setVisibility(View.VISIBLE);
            //hide gridview
            stubGrid.setVisibility(View.GONE);
        }
        else
        {
            stubList.setVisibility(View.GONE);
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }

    private void setAdapters()
    {
        listViewAdapter = new ListViewAdapter(this,R.layout.list_item,productList);
        gridViewAdapter = new GridViewAdapter(this,R.layout.griditem,productList);
        listview.setAdapter(listViewAdapter);
        gridView.setAdapter(gridViewAdapter);

    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            CreateEventTitle = position + 1;

            startActivity(new Intent(CreatedEventList.this,Update_Create_event.class));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item_menu_1:
                if(VIEW_MODE_LISTVIEW == currentViewMode)
                    currentViewMode = VIEW_MODE_GRIDVIEW;
                else
                    currentViewMode = VIEW_MODE_LISTVIEW;

                //switch view
                switchView();
                //save view mode in share refrence
                SharedPreferences share = getSharedPreferences("ViewMode",MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putInt("CurrentViewMode",currentViewMode);
                editor.commit();

                break;

            case R.id.item_menu_2:
                startActivity(new Intent(CreatedEventList.this,Filter.class));
                break;
        }
        switch (item.getItemId())
        {
            case R.id.nav_home:
                return true;
            case R.id.nav_Events:
                return true;
            case R.id.nav_create_event:
                return true;
            case R.id.nav_profile:
                return true;
            case R.id.nav_settings:
                return true;
            case R.id.nav_your_event:
                return true;
        }
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showData(DataSnapshot dataSnapshot)
    {
        productList.clear();
        String Event = "Event";
        int count = 1;


        for (DataSnapshot ds: dataSnapshot.child(Userid).child("CreatedEvents").getChildren())
        {
            // Product value = ds.getValue(Product.class);
            // value.setImageid(R.mipmap.ic_launcher_round);
            String tit = dataSnapshot.child(Userid).child("CreatedEvents").child(Event+count).child("Title").getValue(String.class).toString();
            String Dat = dataSnapshot.child(Userid).child("CreatedEvents").child(Event+count).child("Date").getValue(String.class).toString();
            String Cat = dataSnapshot.child(Userid).child("CreatedEvents").child(Event+count).child("Category").getValue(String.class).toString();
            String Img = dataSnapshot.child(Userid).child("CreatedEvents").child(Event+count).child("Image").child("url").getValue(String.class).toString();
            ++count;

            productList.add(new Product(tit,"Date: "+Dat,"Category: "+Cat,Img));
        }


        setAdapters();
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

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_createdEventList);
        if(drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        if(id==R.id.nav_home)
        {
            startActivity(new Intent(CreatedEventList.this,Home.class));
        }
        else if(id==R.id.nav_Events)
        {
            startActivity(new Intent(CreatedEventList.this,Registered_Events.class));
        }
        else if(id == R.id.nav_create_event)
        {
            startActivity(new Intent(CreatedEventList.this,Create_Event.class));
        }

        else if(id== nav_profile)
        {
            startActivity(new Intent(CreatedEventList.this,Profile.class));

        }
        else if(id==R.id.nav_settings)
        {
            startActivity(new Intent(CreatedEventList.this,Settings.class));
        }
        else if(id==R.id.nav_your_event)
        {
            DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_createdEventList);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_createdEventList);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}