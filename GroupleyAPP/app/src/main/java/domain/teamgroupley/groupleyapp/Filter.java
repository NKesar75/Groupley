package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Filter extends AppCompatActivity {

    private static final String TAG = "Filter";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();

    Spinner Sortby;
    Spinner Spefic;
    Spinner Cata;

    RadioButton Aevents;
    RadioButton Yevents;

    Button Fil;

    ArrayList<String> filterCatgorylist = new ArrayList<>();

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mSortby = mRootRef.child(USerid).child("Filter").child("Sortby");
    DatabaseReference mFilterby = mRootRef.child(USerid).child("Filter").child("Filterby");
    DatabaseReference mtakeout = mRootRef.child(USerid).child("Filter").child("Spefic");
    DatabaseReference mtakeoutstring = mRootRef.child(USerid).child("Filter").child("SpeficString");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Sortby = (Spinner) findViewById(R.id.SortbySpinner);
        ArrayAdapter<String> Sort = new ArrayAdapter<String>(Filter.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Sort));
        Sort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sortby.setAdapter(Sort);

        Spefic = (Spinner) findViewById(R.id.Filterbyspinner);
        ArrayAdapter<String> Filterwith = new ArrayAdapter<String>(Filter.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Spefic));
        Filterwith.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spefic.setAdapter(Filterwith);

        Cata = (Spinner) findViewById(R.id.Speficspiner);

        Fil = (Button) findViewById(R.id.FilterSave);

        Aevents = (RadioButton) findViewById(R.id.ALLEVENTSBTN);
        Yevents = (RadioButton) findViewById(R.id.YOUREVENTSBTN);


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
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                shoData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Fil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Sor = Sortby.getSelectedItem().toString();
                String Fill = Spefic.getSelectedItem().toString();
                String Spe;
                if (Aevents.isChecked()) {
                    Spe = "All";
                } else {
                    Spe = "spefic";
                }
                String Sepficst = Cata.getSelectedItem().toString();

                mRootRef.child(USerid).child("Filter").child("Sortby").setValue(Sor);
                mRootRef.child(USerid).child("Filter").child("Filterby").setValue(Fill);
                mRootRef.child(USerid).child("Filter").child("Spefic").setValue(Spe);
                mRootRef.child(USerid).child("Filter").child("SpeficString").setValue(Sepficst);

                startActivity(new Intent(Filter.this,Home.class));

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);


        mSortby.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                int index = 0;
                for (int i = 0; i < Sortby.getCount(); i++) {
                    if (Sortby.getItemAtPosition(i).equals(Temp)) {
                        Sortby.setSelection(i);
                        break;
                    }
                }
            }




            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });

        mFilterby.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                int index = 0;
                for (int i = 0; i < Spefic.getCount(); i++) {
                    if (Spefic.getItemAtPosition(i).equals(Temp)) {
                        Spefic.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mtakeout.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                if (Temp.equals("All")){
                    Aevents.setChecked(true);
                    Yevents.setChecked(false);
                }
                else if (Temp.equals("spefic")){
                    Aevents.setChecked(false);
                    Yevents.setChecked(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mtakeoutstring.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                int x = 0;
                for (int i = 0; i < filterCatgorylist.size(); i++) {
                    String checker = filterCatgorylist.get(i);
                    if (checker.equals(Temp)){
                        x = i;
                        break;
                    }
                }

                Cata.setSelection(x);
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

    private void shoData(DataSnapshot dataSnapshot) {

        filterCatgorylist.clear();

        boolean SArcy     = dataSnapshot.child(USerid).child("Interests").child("sarchery").getValue(boolean.class).booleanValue();
        boolean SBasy     = dataSnapshot.child(USerid).child("Interests").child("sbaseball").getValue(boolean.class).booleanValue();
        boolean SbKy      = dataSnapshot.child(USerid).child("Interests").child("sbasketball").getValue(boolean.class).booleanValue();
        boolean Cycy      = dataSnapshot.child(USerid).child("Interests").child("scycling").getValue(boolean.class).booleanValue();
        boolean Fish      = dataSnapshot.child(USerid).child("Interests").child("sfishing").getValue(boolean.class).booleanValue();
        boolean Footy     = dataSnapshot.child(USerid).child("Interests").child("sfootball").getValue(boolean.class).booleanValue();
        boolean Frisy     = dataSnapshot.child(USerid).child("Interests").child("sfrisbe").getValue(boolean.class).booleanValue();
        boolean SGofy     = dataSnapshot.child(USerid).child("Interests").child("sgolf").getValue(boolean.class).booleanValue();
        boolean Shockeyy  = dataSnapshot.child(USerid).child("Interests").child("shoccey").getValue(boolean.class).booleanValue();
        boolean SHunty    = dataSnapshot.child(USerid).child("Interests").child("shunting").getValue(boolean.class).booleanValue();
        boolean SSKatey   = dataSnapshot.child(USerid).child("Interests").child("sskateboarding").getValue(boolean.class).booleanValue();
        boolean SSnowy    = dataSnapshot.child(USerid).child("Interests").child("ssnowBoarding").getValue(boolean.class).booleanValue();
        boolean Swsy      = dataSnapshot.child(USerid).child("Interests").child("swaterSports").getValue(boolean.class).booleanValue();
        boolean Wrey      = dataSnapshot.child(USerid).child("Interests").child("swrestling").getValue(boolean.class).booleanValue();
        boolean Fesy      = dataSnapshot.child(USerid).child("Interests").child("pfestivles").getValue(boolean.class).booleanValue();
        boolean Housy     = dataSnapshot.child(USerid).child("Interests").child("phouseParites").getValue(boolean.class).booleanValue();
        boolean Nighty    = dataSnapshot.child(USerid).child("Interests").child("pnightClubs").getValue(boolean.class).booleanValue();
        boolean Gacty     = dataSnapshot.child(USerid).child("Interests").child("gaction").getValue(boolean.class).booleanValue();
        boolean Gadvy     = dataSnapshot.child(USerid).child("Interests").child("gadventure").getValue(boolean.class).booleanValue();
        boolean GFpy      = dataSnapshot.child(USerid).child("Interests").child("gfps").getValue(boolean.class).booleanValue();
        boolean Gindy     = dataSnapshot.child(USerid).child("Interests").child("gindies").getValue(boolean.class).booleanValue();
        boolean GMMy      = dataSnapshot.child(USerid).child("Interests").child("gmmo").getValue(boolean.class).booleanValue();
        boolean GpaFy     = dataSnapshot.child(USerid).child("Interests").child("gpartyfamily").getValue(boolean.class).booleanValue();
        boolean GRPy      = dataSnapshot.child(USerid).child("Interests").child("grpg").getValue(boolean.class).booleanValue();
        boolean Gsiy      = dataSnapshot.child(USerid).child("Interests").child("gsimulation").getValue(boolean.class).booleanValue();
        boolean Gspy      = dataSnapshot.child(USerid).child("Interests").child("gsports").getValue(boolean.class).booleanValue();
        boolean GStry     = dataSnapshot.child(USerid).child("Interests").child("gstragy").getValue(boolean.class).booleanValue();
        boolean MCy       = dataSnapshot.child(USerid).child("Interests").child("mcountry").getValue(boolean.class).booleanValue();
        boolean MDRy      = dataSnapshot.child(USerid).child("Interests").child("mdrillrap").getValue(boolean.class).booleanValue();
        boolean MEdy      = dataSnapshot.child(USerid).child("Interests").child("medm").getValue(boolean.class).booleanValue();
        boolean MJzy      = dataSnapshot.child(USerid).child("Interests").child("mjazz").getValue(boolean.class).booleanValue();
        boolean MRpy      = dataSnapshot.child(USerid).child("Interests").child("mrap").getValue(boolean.class).booleanValue();
        boolean Mroy      = dataSnapshot.child(USerid).child("Interests").child("mrock").getValue(boolean.class).booleanValue();
        boolean MRNy      = dataSnapshot.child(USerid).child("Interests").child("mrnb").getValue(boolean.class).booleanValue();
        boolean MScry     = dataSnapshot.child(USerid).child("Interests").child("mscremo").getValue(boolean.class).booleanValue();
        boolean MoActy    = dataSnapshot.child(USerid).child("Interests").child("moAction").getValue(boolean.class).booleanValue();
        boolean MOAniy    = dataSnapshot.child(USerid).child("Interests").child("moAnimation").getValue(boolean.class).booleanValue();
        boolean MOComy    = dataSnapshot.child(USerid).child("Interests").child("moComdey").getValue(boolean.class).booleanValue();
        boolean MODoy     = dataSnapshot.child(USerid).child("Interests").child("moDocumentary").getValue(boolean.class).booleanValue();
        boolean MOFy      = dataSnapshot.child(USerid).child("Interests").child("moFamily").getValue(boolean.class).booleanValue();
        boolean MOHOry    = dataSnapshot.child(USerid).child("Interests").child("moHorror").getValue(boolean.class).booleanValue();
        boolean MoMusy    = dataSnapshot.child(USerid).child("Interests").child("moMusical").getValue(boolean.class).booleanValue();
        boolean MOSiy     = dataSnapshot.child(USerid).child("Interests").child("moSifi").getValue(boolean.class).booleanValue();
        boolean MOSpoy    = dataSnapshot.child(USerid).child("Interests").child("moSports").getValue(boolean.class).booleanValue();
        boolean MOTHrily  = dataSnapshot.child(USerid).child("Interests").child("moThriller").getValue(boolean.class).booleanValue();
        boolean MoWay     = dataSnapshot.child(USerid).child("Interests").child("moWar").getValue(boolean.class).booleanValue();
        boolean TActy     = dataSnapshot.child(USerid).child("Interests").child("taction").getValue(boolean.class).booleanValue();
        boolean TADvy     = dataSnapshot.child(USerid).child("Interests").child("tadventure").getValue(boolean.class).booleanValue();
        boolean TAniy     = dataSnapshot.child(USerid).child("Interests").child("tanimation").getValue(boolean.class).booleanValue();
        boolean TBioy     = dataSnapshot.child(USerid).child("Interests").child("tbiography").getValue(boolean.class).booleanValue();
        boolean TCom      = dataSnapshot.child(USerid).child("Interests").child("tcomedy").getValue(boolean.class).booleanValue();
        boolean TCriy     = dataSnapshot.child(USerid).child("Interests").child("tcrime").getValue(boolean.class).booleanValue();
        boolean TDoy      = dataSnapshot.child(USerid).child("Interests").child("tdocoumentary").getValue(boolean.class).booleanValue();
        boolean TDray     = dataSnapshot.child(USerid).child("Interests").child("tdrama").getValue(boolean.class).booleanValue();
        boolean Tfay      = dataSnapshot.child(USerid).child("Interests").child("tfamily").getValue(boolean.class).booleanValue();
        boolean TGamey    = dataSnapshot.child(USerid).child("Interests").child("tgameShows").getValue(boolean.class).booleanValue();
        boolean THisy     = dataSnapshot.child(USerid).child("Interests").child("thistory").getValue(boolean.class).booleanValue();
        boolean Thory     = dataSnapshot.child(USerid).child("Interests").child("thorror").getValue(boolean.class).booleanValue();
        boolean TMysy     = dataSnapshot.child(USerid).child("Interests").child("tmystery").getValue(boolean.class).booleanValue();
        boolean Trey      = dataSnapshot.child(USerid).child("Interests").child("treality").getValue(boolean.class).booleanValue();
        boolean Tsiy      = dataSnapshot.child(USerid).child("Interests").child("tsitcom").getValue(boolean.class).booleanValue();
        boolean TSpoy     = dataSnapshot.child(USerid).child("Interests").child("tsports").getValue(boolean.class).booleanValue();
        boolean TTalky    = dataSnapshot.child(USerid).child("Interests").child("ttalkShows").getValue(boolean.class).booleanValue();
        boolean Tway      = dataSnapshot.child(USerid).child("Interests").child("twar").getValue(boolean.class).booleanValue();
        boolean Dacty     = dataSnapshot.child(USerid).child("Interests").child("dacting").getValue(boolean.class).booleanValue();
        boolean Dcosy     = dataSnapshot.child(USerid).child("Interests").child("dcosplay").getValue(boolean.class).booleanValue();
        boolean Dlay      = dataSnapshot.child(USerid).child("Interests").child("dlarping").getValue(boolean.class).booleanValue();
        boolean CActy     = dataSnapshot.child(USerid).child("Interests").child("cactionfigures").getValue(boolean.class).booleanValue();
        boolean CCry      = dataSnapshot.child(USerid).child("Interests").child("ccars").getValue(boolean.class).booleanValue();
        boolean Ccinsy    = dataSnapshot.child(USerid).child("Interests").child("ccoins").getValue(boolean.class).booleanValue();
        boolean Ccomy     = dataSnapshot.child(USerid).child("Interests").child("ccomics").getValue(boolean.class).booleanValue();
        boolean CGuny     = dataSnapshot.child(USerid).child("Interests").child("cguns").getValue(boolean.class).booleanValue();
        boolean Ctrcy     = dataSnapshot.child(USerid).child("Interests").child("ctrucks").getValue(boolean.class).booleanValue();


        if (SArcy){
            filterCatgorylist.add("Archery");
        }

        if (SBasy){
            filterCatgorylist.add("Baseball");
        }

        if (SbKy){
            filterCatgorylist.add("Basketball");
        }

        if (Cycy){
            filterCatgorylist.add("Bicycle");
        }

        if (Fish){
            filterCatgorylist.add("Fishing");
        }

        if (Footy){
            filterCatgorylist.add("Football");
        }

        if (Frisy){
            filterCatgorylist.add("Frisbe");
        }

        if (SGofy){
            filterCatgorylist.add("Golf");
        }

        if (Shockeyy){
            filterCatgorylist.add("Hockey");
        }

        if (SHunty){
            filterCatgorylist.add("Hunting");
        }

        if (SSKatey){
            filterCatgorylist.add("Skateboarding");
        }

        if (SSnowy){
            filterCatgorylist.add("Snowboarding");
        }

        if (Swsy){
            filterCatgorylist.add("Water Sports");
        }

        if (Wrey){
            filterCatgorylist.add("Wrestling");
        }

        if (Fesy){
            filterCatgorylist.add("Festival");
        }

        if (Housy){
            filterCatgorylist.add("House Party");
        }

        if (Nighty){
            filterCatgorylist.add("Night Club");
        }

        if (Gacty){
            filterCatgorylist.add("Action Game");
        }

        if (Gadvy){
            filterCatgorylist.add("Adventure Game");
        }

        if (GFpy){
            filterCatgorylist.add("FPS Game");
        }

        if (Gindy){
            filterCatgorylist.add("Indie Game");
        }

        if (GMMy){
            filterCatgorylist.add("MMO Game");
        }

        if (GpaFy){
            filterCatgorylist.add("Party Game");
        }

        if (GRPy){
            filterCatgorylist.add("RPG Game");
        }

        if (Gsiy){
            filterCatgorylist.add("Simulation Game");
        }

        if (Gspy){
            filterCatgorylist.add("Sports Game");
        }

        if (GStry){
            filterCatgorylist.add("Stragey Game");
        }

        if (MCy){
            filterCatgorylist.add("Country Music");
        }

        if (MDRy){
            filterCatgorylist.add("Drill Rap");
        }

        if (MEdy){
            filterCatgorylist.add("EDM");
        }

        if (MJzy){
            filterCatgorylist.add("Jazz");
        }

        if (MRpy){
            filterCatgorylist.add("Rap");
        }

        if (Mroy){
            filterCatgorylist.add("Rock");
        }

        if (MRNy){
            filterCatgorylist.add("RNB");
        }

        if (MScry){
            filterCatgorylist.add("Scremo");
        }

        if (MoActy){
            filterCatgorylist.add("Action Movie");
        }

        if (MOAniy){
            filterCatgorylist.add("Animation Movie");
        }

        if (MOComy){
            filterCatgorylist.add("Comdey Movie");
        }

        if (MODoy){
            filterCatgorylist.add("Documentary Movie");
        }

        if (MOFy){
            filterCatgorylist.add("Family Movie");
        }

        if (MOHOry){
            filterCatgorylist.add("Horror Movie");
        }

        if (MoMusy){
            filterCatgorylist.add("Musical Movie");
        }

        if (MOSiy){
            filterCatgorylist.add("Sifi Movie");
        }

        if (MOSpoy){
            filterCatgorylist.add("Sports Movie");
        }

        if (MOTHrily){
            filterCatgorylist.add("Thriller Movie");
        }

        if (MoWay){
            filterCatgorylist.add("War Movie");
        }

        if (TActy){
            filterCatgorylist.add("Action Shows");
        }

        if (TADvy){
            filterCatgorylist.add("Adventure Shows");
        }

        if (TAniy){
            filterCatgorylist.add("Animation Shows");
        }

        if (TBioy){
            filterCatgorylist.add("Biography Shows");
        }

        if (TCom){
            filterCatgorylist.add("Comedy Shows");
        }

        if (TCriy){
            filterCatgorylist.add("Crime Shows");
        }

        if (TDoy){
            filterCatgorylist.add("Documentary Shows");
        }

        if (TDray){
            filterCatgorylist.add("Drama Shows");
        }

        if (Tfay){
            filterCatgorylist.add("Family Shows");
        }

        if (TGamey){
            filterCatgorylist.add("Game Shows");
        }

        if (THisy){
            filterCatgorylist.add("History Shows");
        }

        if (Thory){
            filterCatgorylist.add("Horror Shows");
        }

        if (TMysy){
            filterCatgorylist.add("Mystery Shows");
        }

        if (Trey){
            filterCatgorylist.add("Reality Shows");
        }

        if (Tsiy){
            filterCatgorylist.add("Sifi Shows");
        }

        if (TSpoy){
            filterCatgorylist.add("Sports Shows");
        }

        if (TTalky){
            filterCatgorylist.add("Talk Shows");
        }

        if (Tway){
            filterCatgorylist.add("War Shows");
        }

        if (Dacty){
            filterCatgorylist.add("Acting");
        }

        if (Dcosy){
            filterCatgorylist.add("Cosplay");
        }

        if (Dlay){
            filterCatgorylist.add("Larping");
        }

        if (CActy){
            filterCatgorylist.add("Action Figures");
        }

        if (CCry){
            filterCatgorylist.add("Cars");
        }

        if (Ccinsy){
            filterCatgorylist.add("Coins");
        }

        if (Ccomy){
            filterCatgorylist.add("Comics");
        }

        if (CGuny){
            filterCatgorylist.add("Guns");
        }

        if (Ctrcy){
            filterCatgorylist.add("Trucks");
        }

        ArrayAdapter<String> Speficwith = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, filterCatgorylist);
        Speficwith.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cata.setAdapter(Speficwith);

    }
}




