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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static domain.teamgroupley.groupleyapp.R.id.item_menu_2_profile;
import static domain.teamgroupley.groupleyapp.R.id.nav_profile;

public class Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView username ;
    private ImageView profileImage;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    DatabaseReference mUsername = mRootRef.child(USerid).child("UserInfo").child("UserName");
    DatabaseReference mProfileImage = mRootRef.child(USerid).child("UserInfo").child("Image").child("url");


//    private Button Logout;
//    private Button Interest;
   // private Button EditProfile;
    private static final String TAG = "Profile";
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        Logout = (Button) findViewById(R.id.logout_btn);
//        Interest = (Button)findViewById(R.id.SEE_INTREST_PROFILE_BTN);
//       // EditProfile = (Button)findViewById(R.id.EDIT_PROFILE_BTN);
//
//        Interest.setFocusable(true);
//        Interest.setFocusableInTouchMode(true);///add this line
//        Interest.requestFocus();

        username = (TextView)findViewById(R.id.USERNAME_Tst);
        profileImage = (ImageView)findViewById(R.id.profile_download);
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
//        Logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                startActivity(new Intent(Profile.this,LoginUser.class));
//
//            }
//
//        });
//
//        Interest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Profile.this,IntrestSelection.class));
//            }
//        });

//        EditProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Profile.this,UpdateProfile.class));
//            }
//        });

        draw = (DrawerLayout)findViewById(R.id.activity_profile);
        toggle = new ActionBarDrawerToggle(this,draw,R.string.open,R.string.close);

        draw.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigation = (NavigationView)findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        mProfileImage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = dataSnapshot.getValue(String.class);
                Glide.with(Profile.this).load(image.toString()).into(profileImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mUsername.addValueEventListener(new ValueEventListener() {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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

        switch (item.getItemId()) {
            case R.id.item_menu_1_profile:
                startActivity(new Intent(Profile.this,info.class));
                break;
            case R.id.item_menu_2_profile:
                mAuth.signOut();
                startActivity(new Intent(Profile.this,LoginUser.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_profile);
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
            startActivity(new Intent(Profile.this,Home.class));
        }
        else if(id==R.id.nav_Events)
        {
            startActivity(new Intent(Profile.this,Registered_Events.class));
        }
        else if(id == R.id.nav_create_event)
        {
            startActivity(new Intent(Profile.this,Create_Event.class));
        }

        else if(id== nav_profile)
        {
            DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_profile);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(id==R.id.nav_settings)
        {
            startActivity(new Intent(Profile.this,Settings.class));
        }
        else if(id==R.id.nav_your_event)
        {
            startActivity(new Intent(Profile.this,CreatedEventList.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_profile);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}