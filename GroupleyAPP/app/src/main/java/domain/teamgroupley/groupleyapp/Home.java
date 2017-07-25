package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.Sampler;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.eventsInterceptionEnabled;
import static android.R.attr.value;

public class Home extends AppCompatActivity
{

    private ListView mListView;

    private static final String TAG = "Home";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();
    private String data;

    private List<String> marr = new ArrayList();

    public static String EventTitle;

    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listview;
    private GridView gridView;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    private List<Product> productList;
    private int currentViewMode = 0;
    private String title;
    private String date;
    private ArrayList<String> titlearray = new ArrayList<>();
    private ArrayList<String> datearray = new ArrayList<>();

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                getProductList(dataSnapshot);
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
        if(VIEW_MODE_LISTVIEW == currentViewMode)
        {
            listViewAdapter = new ListViewAdapter(this,R.layout.list_item,productList);
            listview.setAdapter(listViewAdapter);
        }
        else
        {
            gridViewAdapter = new GridViewAdapter(this,R.layout.griditem,productList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            EventTitle = productList.get(position).getTitle();
            startActivity(new Intent(Home.this,Description.class));
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
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Product> getProductList(DataSnapshot dataSnapshot)
    {

        for (DataSnapshot ds: dataSnapshot.child("Events").child(EventTitle).getChildren())
        {
            Product value = ds.getValue(Product.class);
             title = value.getTitle();
             date = value.getDate();
            productList = new ArrayList<>();
            productList.add(new Product(R.mipmap.ic_launcher_round,title,date));
        }
        setAdapters();
        return productList;
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

