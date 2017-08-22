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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static domain.teamgroupley.groupleyapp.R.id.nav_profile;

public class Registered_Events extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Registered_Events";

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();

    public static int regEventTitle;

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
        setContentView(R.layout.activity_registered__events);
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
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        stubList = (ViewStub)findViewById(R.id.stub_reg_list);
        stubGrid = (ViewStub)findViewById(R.id.stub_reg_grid);

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

        draw = (DrawerLayout)findViewById(R.id.activity_reg_events);
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
            regEventTitle = position + 1;

            startActivity(new Intent(Registered_Events.this,RegisteredEventDesc.class));
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
                startActivity(new Intent(Registered_Events.this, registered_filter.class));
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

        for (DataSnapshot ds: dataSnapshot.child(USerid).child("RegisteredEvents").getChildren())
        {
            // Product value = ds.getValue(Product.class);
            // value.setImageid(R.mipmap.ic_launcher_round);
            String tit = dataSnapshot.child(USerid).child("RegisteredEvents").child(Event+count).child("Title").getValue(String.class).toString();
            String Dat = dataSnapshot.child(USerid).child("RegisteredEvents").child(Event+count).child("Date").getValue(String.class).toString();
            String Cat = dataSnapshot.child(USerid).child("RegisteredEvents").child(Event+count).child("Category").getValue(String.class).toString();
            String Img = dataSnapshot.child(USerid).child("RegisteredEvents").child(Event+count).child("Image").child("url").getValue(String.class).toString();
            ++count;
            productList.add(new Product(tit, Dat, Cat,Img));
        }

        String tempTilte = dataSnapshot.child(USerid).child("Filter").child("Sortby").getValue(String.class).toString();
        String tempspefic = dataSnapshot.child(USerid).child("Filter").child("Spefic").getValue(String.class).toString();
        String tempkey = dataSnapshot.child(USerid).child("Filter").child("SpeficString").getValue(String.class).toString();

        boolean FilterTitle = false;
        boolean FilterDate = false;
        boolean FilterCategory = false;


        boolean FilterAllEvents = false;
        boolean FilterYourInterest = false;
        boolean FilterSpefic = false;
        String FilterSpefickeyword = "false";


        if (tempTilte.equals("DATE")) {
            FilterTitle = false;
            FilterDate = true;
            FilterCategory = false;

        } else if (tempTilte.equals("TITLE")) {
            FilterTitle = true;
            FilterDate = false;
            FilterCategory = false;

        } else if (tempTilte.equals("CATEGORY")) {
            FilterTitle = false;
            FilterDate = false;
            FilterCategory = true;
        }

        if (tempspefic.equals("All")) {
            FilterAllEvents = true;
            FilterYourInterest = false;
            FilterSpefic = false;
            FilterSpefickeyword = " ";
        } else if (tempspefic.equals("Yours")) {
            FilterAllEvents = false;
            FilterSpefic = false;
            FilterSpefickeyword = " ";
            FilterYourInterest = true;
        } else if (tempspefic.equals("spefic")) {
            FilterAllEvents = false;
            FilterYourInterest = false;
            FilterSpefic = true;
            FilterSpefickeyword = tempkey;
        }

        Calendar cal = Calendar.getInstance();
        int curYear = cal.get(Calendar.YEAR);
        int curMonth = cal.get(Calendar.MONTH ) + 1;
        int curDay = cal.get(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < productList.size(); ++i) {

            String[] parts1 = productList.get(i).getDate().split("/");

            if (curYear > Integer.parseInt(parts1[2])) {
                productList.remove(i);
                --i;
            } else if (curYear <= Integer.parseInt(parts1[2])) {
                if (curMonth > Integer.parseInt(parts1[0])) {
                    productList.remove(i);
                    --i;
                } else if (curMonth == Integer.parseInt(parts1[0])) {
                    if (curDay > Integer.parseInt(parts1[1])) {
                        productList.remove(i);
                        --i;
                    } else {
                        continue;
                    }
                } else if (curMonth < Integer.parseInt(parts1[0])) {
                    continue;
                }
            }
        }

        Product compare[] = new Product[productList.size()];

        for (int i = 0; i < compare.length; ++i) {
            compare[i] = productList.get(i);
        }

        if (FilterTitle) {
            for (int i = 0; i < compare.length; ++i) {
                for (int j = 0; j < compare.length; ++j) {
                    if (compare[i].getTitle().compareToIgnoreCase(compare[j].getTitle()) < 0) {
                        Product temp = compare[i];
                        compare[i] = compare[j];
                        compare[j] = temp;
                    }
                }
            }
        }else if (FilterDate) {


            for (int i = 0; i < compare.length; ++i) {
                for (int j = 0; j < compare.length; ++j) {

                    if (i == j) {
                        continue;
                    }

                    String[] parts1 = compare[i].getDate().split("/");

                    String[] parts2 = compare[j].getDate().split("/");

                    if (Integer.parseInt(parts1[2]) == Integer.parseInt(parts2[2])) {
                        if (Integer.parseInt(parts1[0]) == Integer.parseInt(parts2[0])) {
                            if (Integer.parseInt(parts1[1]) == Integer.parseInt(parts2[1])) {
                                continue;
                            } else if (Integer.parseInt(parts1[1]) < Integer.parseInt(parts2[1])) {
                                Product temp = compare[i];
                                compare[i] = compare[j];
                                compare[j] = temp;
                            }

                        } else if (Integer.parseInt(parts1[0]) < Integer.parseInt(parts2[0])) {
                            Product temp = compare[i];
                            compare[i] = compare[j];
                            compare[j] = temp;
                        }
                    } else if (Integer.parseInt(parts1[2]) < Integer.parseInt(parts2[2])) {

                        Product temp = compare[i];
                        compare[i] = compare[j];
                        compare[j] = temp;

                    }
                }
            }

        } else if (FilterCategory) {
            for (int i = 0; i < compare.length; ++i) {
                for (int j = 0; j < compare.length; ++j) {
                    if (compare[i].getCategory().compareToIgnoreCase(compare[j].getCategory()) < 0) {
                        Product temp = compare[i];
                        compare[i] = compare[j];
                        compare[j] = temp;
                    }
                }
            }
        }


        if (FilterYourInterest) {
            List<String> filterCatgorylistforhome = new ArrayList<>();

            boolean SArcy = dataSnapshot.child(USerid).child("Interests").child("sarchery").getValue(boolean.class).booleanValue();
            boolean SBasy = dataSnapshot.child(USerid).child("Interests").child("sbaseball").getValue(boolean.class).booleanValue();
            boolean SbKy = dataSnapshot.child(USerid).child("Interests").child("sbasketball").getValue(boolean.class).booleanValue();
            boolean Cycy = dataSnapshot.child(USerid).child("Interests").child("scycling").getValue(boolean.class).booleanValue();
            boolean Fish = dataSnapshot.child(USerid).child("Interests").child("sfishing").getValue(boolean.class).booleanValue();
            boolean Footy = dataSnapshot.child(USerid).child("Interests").child("sfootball").getValue(boolean.class).booleanValue();
            boolean Frisy = dataSnapshot.child(USerid).child("Interests").child("sfrisbe").getValue(boolean.class).booleanValue();
            boolean SGofy = dataSnapshot.child(USerid).child("Interests").child("sgolf").getValue(boolean.class).booleanValue();
            boolean Shockeyy = dataSnapshot.child(USerid).child("Interests").child("shoccey").getValue(boolean.class).booleanValue();
            boolean SHunty = dataSnapshot.child(USerid).child("Interests").child("shunting").getValue(boolean.class).booleanValue();
            boolean SSKatey = dataSnapshot.child(USerid).child("Interests").child("sskateboarding").getValue(boolean.class).booleanValue();
            boolean SSnowy = dataSnapshot.child(USerid).child("Interests").child("ssnowBoarding").getValue(boolean.class).booleanValue();
            boolean Swsy = dataSnapshot.child(USerid).child("Interests").child("swaterSports").getValue(boolean.class).booleanValue();
            boolean Wrey = dataSnapshot.child(USerid).child("Interests").child("swrestling").getValue(boolean.class).booleanValue();
            boolean Fesy = dataSnapshot.child(USerid).child("Interests").child("pfestivles").getValue(boolean.class).booleanValue();
            boolean Housy = dataSnapshot.child(USerid).child("Interests").child("phouseParites").getValue(boolean.class).booleanValue();
            boolean Nighty = dataSnapshot.child(USerid).child("Interests").child("pnightClubs").getValue(boolean.class).booleanValue();
            boolean Gacty = dataSnapshot.child(USerid).child("Interests").child("gaction").getValue(boolean.class).booleanValue();
            boolean Gadvy = dataSnapshot.child(USerid).child("Interests").child("gadventure").getValue(boolean.class).booleanValue();
            boolean GFpy = dataSnapshot.child(USerid).child("Interests").child("gfps").getValue(boolean.class).booleanValue();
            boolean Gindy = dataSnapshot.child(USerid).child("Interests").child("gindies").getValue(boolean.class).booleanValue();
            boolean GMMy = dataSnapshot.child(USerid).child("Interests").child("gmmo").getValue(boolean.class).booleanValue();
            boolean GpaFy = dataSnapshot.child(USerid).child("Interests").child("gpartyfamily").getValue(boolean.class).booleanValue();
            boolean GRPy = dataSnapshot.child(USerid).child("Interests").child("grpg").getValue(boolean.class).booleanValue();
            boolean Gsiy = dataSnapshot.child(USerid).child("Interests").child("gsimulation").getValue(boolean.class).booleanValue();
            boolean Gspy = dataSnapshot.child(USerid).child("Interests").child("gsports").getValue(boolean.class).booleanValue();
            boolean GStry = dataSnapshot.child(USerid).child("Interests").child("gstragy").getValue(boolean.class).booleanValue();
            boolean MCy = dataSnapshot.child(USerid).child("Interests").child("mcountry").getValue(boolean.class).booleanValue();
            boolean MDRy = dataSnapshot.child(USerid).child("Interests").child("mdrillrap").getValue(boolean.class).booleanValue();
            boolean MEdy = dataSnapshot.child(USerid).child("Interests").child("medm").getValue(boolean.class).booleanValue();
            boolean MJzy = dataSnapshot.child(USerid).child("Interests").child("mjazz").getValue(boolean.class).booleanValue();
            boolean MRpy = dataSnapshot.child(USerid).child("Interests").child("mrap").getValue(boolean.class).booleanValue();
            boolean Mroy = dataSnapshot.child(USerid).child("Interests").child("mrock").getValue(boolean.class).booleanValue();
            boolean MRNy = dataSnapshot.child(USerid).child("Interests").child("mrnb").getValue(boolean.class).booleanValue();
            boolean MScry = dataSnapshot.child(USerid).child("Interests").child("mscremo").getValue(boolean.class).booleanValue();
            boolean MoActy = dataSnapshot.child(USerid).child("Interests").child("moAction").getValue(boolean.class).booleanValue();
            boolean MOAniy = dataSnapshot.child(USerid).child("Interests").child("moAnimation").getValue(boolean.class).booleanValue();
            boolean MOComy = dataSnapshot.child(USerid).child("Interests").child("moComdey").getValue(boolean.class).booleanValue();
            boolean MODoy = dataSnapshot.child(USerid).child("Interests").child("moDocumentary").getValue(boolean.class).booleanValue();
            boolean MOFy = dataSnapshot.child(USerid).child("Interests").child("moFamily").getValue(boolean.class).booleanValue();
            boolean MOHOry = dataSnapshot.child(USerid).child("Interests").child("moHorror").getValue(boolean.class).booleanValue();
            boolean MoMusy = dataSnapshot.child(USerid).child("Interests").child("moMusical").getValue(boolean.class).booleanValue();
            boolean MOSiy = dataSnapshot.child(USerid).child("Interests").child("moSifi").getValue(boolean.class).booleanValue();
            boolean MOSpoy = dataSnapshot.child(USerid).child("Interests").child("moSports").getValue(boolean.class).booleanValue();
            boolean MOTHrily = dataSnapshot.child(USerid).child("Interests").child("moThriller").getValue(boolean.class).booleanValue();
            boolean MoWay = dataSnapshot.child(USerid).child("Interests").child("moWar").getValue(boolean.class).booleanValue();
            boolean TActy = dataSnapshot.child(USerid).child("Interests").child("taction").getValue(boolean.class).booleanValue();
            boolean TADvy = dataSnapshot.child(USerid).child("Interests").child("tadventure").getValue(boolean.class).booleanValue();
            boolean TAniy = dataSnapshot.child(USerid).child("Interests").child("tanimation").getValue(boolean.class).booleanValue();
            boolean TBioy = dataSnapshot.child(USerid).child("Interests").child("tbiography").getValue(boolean.class).booleanValue();
            boolean TCom = dataSnapshot.child(USerid).child("Interests").child("tcomedy").getValue(boolean.class).booleanValue();
            boolean TCriy = dataSnapshot.child(USerid).child("Interests").child("tcrime").getValue(boolean.class).booleanValue();
            boolean TDoy = dataSnapshot.child(USerid).child("Interests").child("tdocoumentary").getValue(boolean.class).booleanValue();
            boolean TDray = dataSnapshot.child(USerid).child("Interests").child("tdrama").getValue(boolean.class).booleanValue();
            boolean Tfay = dataSnapshot.child(USerid).child("Interests").child("tfamily").getValue(boolean.class).booleanValue();
            boolean TGamey = dataSnapshot.child(USerid).child("Interests").child("tgameShows").getValue(boolean.class).booleanValue();
            boolean THisy = dataSnapshot.child(USerid).child("Interests").child("thistory").getValue(boolean.class).booleanValue();
            boolean Thory = dataSnapshot.child(USerid).child("Interests").child("thorror").getValue(boolean.class).booleanValue();
            boolean TMysy = dataSnapshot.child(USerid).child("Interests").child("tmystery").getValue(boolean.class).booleanValue();
            boolean Trey = dataSnapshot.child(USerid).child("Interests").child("treality").getValue(boolean.class).booleanValue();
            boolean Tsiy = dataSnapshot.child(USerid).child("Interests").child("tsitcom").getValue(boolean.class).booleanValue();
            boolean TSpoy = dataSnapshot.child(USerid).child("Interests").child("tsports").getValue(boolean.class).booleanValue();
            boolean TTalky = dataSnapshot.child(USerid).child("Interests").child("ttalkShows").getValue(boolean.class).booleanValue();
            boolean Tway = dataSnapshot.child(USerid).child("Interests").child("twar").getValue(boolean.class).booleanValue();
            boolean Dacty = dataSnapshot.child(USerid).child("Interests").child("dacting").getValue(boolean.class).booleanValue();
            boolean Dcosy = dataSnapshot.child(USerid).child("Interests").child("dcosplay").getValue(boolean.class).booleanValue();
            boolean Dlay = dataSnapshot.child(USerid).child("Interests").child("dlarping").getValue(boolean.class).booleanValue();
            boolean CActy = dataSnapshot.child(USerid).child("Interests").child("cactionfigures").getValue(boolean.class).booleanValue();
            boolean CCry = dataSnapshot.child(USerid).child("Interests").child("ccars").getValue(boolean.class).booleanValue();
            boolean Ccinsy = dataSnapshot.child(USerid).child("Interests").child("ccoins").getValue(boolean.class).booleanValue();
            boolean Ccomy = dataSnapshot.child(USerid).child("Interests").child("ccomics").getValue(boolean.class).booleanValue();
            boolean CGuny = dataSnapshot.child(USerid).child("Interests").child("cguns").getValue(boolean.class).booleanValue();
            boolean Ctrcy = dataSnapshot.child(USerid).child("Interests").child("ctrucks").getValue(boolean.class).booleanValue();


            if (SArcy) {
                filterCatgorylistforhome.add("Archery");
            }

            if (SBasy) {
                filterCatgorylistforhome.add("Baseball");
            }

            if (SbKy) {
                filterCatgorylistforhome.add("Basketball");
            }

            if (Cycy) {
                filterCatgorylistforhome.add("Bicycle");
            }

            if (Fish) {
                filterCatgorylistforhome.add("Fishing");
            }

            if (Footy) {
                filterCatgorylistforhome.add("Football");
            }

            if (Frisy) {
                filterCatgorylistforhome.add("Frisbe");
            }

            if (SGofy) {
                filterCatgorylistforhome.add("Golf");
            }

            if (Shockeyy) {
                filterCatgorylistforhome.add("Hockey");
            }

            if (SHunty) {
                filterCatgorylistforhome.add("Hunting");
            }

            if (SSKatey) {
                filterCatgorylistforhome.add("Skateboarding");
            }

            if (SSnowy) {
                filterCatgorylistforhome.add("Snowboarding");
            }

            if (Swsy) {
                filterCatgorylistforhome.add("Water Sports");
            }

            if (Wrey) {
                filterCatgorylistforhome.add("Wrestling");
            }

            if (Fesy) {
                filterCatgorylistforhome.add("Festival");
            }

            if (Housy) {
                filterCatgorylistforhome.add("House Party");
            }

            if (Nighty) {
                filterCatgorylistforhome.add("Night Club");
            }

            if (Gacty) {
                filterCatgorylistforhome.add("Action Game");
            }

            if (Gadvy) {
                filterCatgorylistforhome.add("Adventure Game");
            }

            if (GFpy) {
                filterCatgorylistforhome.add("FPS Game");
            }

            if (Gindy) {
                filterCatgorylistforhome.add("Indie Game");
            }

            if (GMMy) {
                filterCatgorylistforhome.add("MMO Game");
            }

            if (GpaFy) {
                filterCatgorylistforhome.add("Party Game");
            }

            if (GRPy) {
                filterCatgorylistforhome.add("RPG Game");
            }

            if (Gsiy) {
                filterCatgorylistforhome.add("Simulation Game");
            }

            if (Gspy) {
                filterCatgorylistforhome.add("Sports Game");
            }

            if (GStry) {
                filterCatgorylistforhome.add("Stragey Game");
            }

            if (MCy) {
                filterCatgorylistforhome.add("Country Music");
            }

            if (MDRy) {
                filterCatgorylistforhome.add("Drill Rap");
            }

            if (MEdy) {
                filterCatgorylistforhome.add("EDM");
            }

            if (MJzy) {
                filterCatgorylistforhome.add("Jazz");
            }

            if (MRpy) {
                filterCatgorylistforhome.add("Rap");
            }

            if (Mroy) {
                filterCatgorylistforhome.add("Rock");
            }

            if (MRNy) {
                filterCatgorylistforhome.add("RNB");
            }

            if (MScry) {
                filterCatgorylistforhome.add("Scremo");
            }

            if (MoActy) {
                filterCatgorylistforhome.add("Action Movie");
            }

            if (MOAniy) {
                filterCatgorylistforhome.add("Animation Movie");
            }

            if (MOComy) {
                filterCatgorylistforhome.add("Comdey Movie");
            }

            if (MODoy) {
                filterCatgorylistforhome.add("Documentary Movie");
            }

            if (MOFy) {
                filterCatgorylistforhome.add("Family Movie");
            }

            if (MOHOry) {
                filterCatgorylistforhome.add("Horror Movie");
            }

            if (MoMusy) {
                filterCatgorylistforhome.add("Musical Movie");
            }

            if (MOSiy) {
                filterCatgorylistforhome.add("Sifi Movie");
            }

            if (MOSpoy) {
                filterCatgorylistforhome.add("Sports Movie");
            }

            if (MOTHrily) {
                filterCatgorylistforhome.add("Thriller Movie");
            }

            if (MoWay) {
                filterCatgorylistforhome.add("War Movie");
            }

            if (TActy) {
                filterCatgorylistforhome.add("Action Shows");
            }

            if (TADvy) {
                filterCatgorylistforhome.add("Adventure Shows");
            }

            if (TAniy) {
                filterCatgorylistforhome.add("Animation Shows");
            }

            if (TBioy) {
                filterCatgorylistforhome.add("Biography Shows");
            }

            if (TCom) {
                filterCatgorylistforhome.add("Comedy Shows");
            }

            if (TCriy) {
                filterCatgorylistforhome.add("Crime Shows");
            }

            if (TDoy) {
                filterCatgorylistforhome.add("Documentary Shows");
            }

            if (TDray) {
                filterCatgorylistforhome.add("Drama Shows");
            }

            if (Tfay) {
                filterCatgorylistforhome.add("Family Shows");
            }

            if (TGamey) {
                filterCatgorylistforhome.add("Game Shows");
            }

            if (THisy) {
                filterCatgorylistforhome.add("History Shows");
            }

            if (Thory) {
                filterCatgorylistforhome.add("Horror Shows");
            }

            if (TMysy) {
                filterCatgorylistforhome.add("Mystery Shows");
            }

            if (Trey) {
                filterCatgorylistforhome.add("Reality Shows");
            }

            if (Tsiy) {
                filterCatgorylistforhome.add("Sifi Shows");
            }

            if (TSpoy) {
                filterCatgorylistforhome.add("Sports Shows");
            }

            if (TTalky) {
                filterCatgorylistforhome.add("Talk Shows");
            }

            if (Tway) {
                filterCatgorylistforhome.add("War Shows");
            }

            if (Dacty) {
                filterCatgorylistforhome.add("Acting");
            }

            if (Dcosy) {
                filterCatgorylistforhome.add("Cosplay");
            }

            if (Dlay) {
                filterCatgorylistforhome.add("Larping");
            }

            if (CActy) {
                filterCatgorylistforhome.add("Action Figures");
            }

            if (CCry) {
                filterCatgorylistforhome.add("Cars");
            }

            if (Ccinsy) {
                filterCatgorylistforhome.add("Coins");
            }

            if (Ccomy) {
                filterCatgorylistforhome.add("Comics");
            }

            if (CGuny) {
                filterCatgorylistforhome.add("Guns");
            }

            if (Ctrcy) {
                filterCatgorylistforhome.add("Trucks");
            }
            boolean exists;

            List<Product> runitup = new ArrayList<>();

            for (int i = 0; i < compare.length; ++i) {
                runitup.add(i, compare[i]);
            }

            String one;
            String two;
            for (int i = 0; i < runitup.size(); ++i) {
                exists = false;
                one = runitup.get(i).getCategory().toString();
                for (int j = 0; j < filterCatgorylistforhome.size(); ++j) {
                    two = filterCatgorylistforhome.get(j).toString();
                    if (one.equals(two)) {
                        exists = true;
                    }
                }
                if (!exists) {
                    runitup.remove(i);
                    --i;
                }
            }
            compare = new Product[runitup.size()];
            for (int i = 0; i < compare.length; ++i) {
                compare[i] = runitup.get(i);
            }
        } else if (FilterSpefic) {

            boolean exists;
            List<Product> runitup = new ArrayList<>();

            for (int i = 0; i < compare.length; ++i) {
                runitup.add(i, compare[i]);
            }


            String one;
            for (int i = 0; i < runitup.size(); ++i) {
                one = runitup.get(i).getCategory().toString();
                if (!one.equals(FilterSpefickeyword)) {
                    runitup.remove(i);
                    --i;
                }
            }

            if (compare.length != 0) {
                compare = new Product[runitup.size()];
                for (int i = 0; i < compare.length; ++i) {
                    compare[i] = runitup.get(i);
                }
            }
        }

        productList.clear();
        for (int i = 0; i < compare.length; ++i) {
            productList.add(i, new Product(compare[i].getTitle(), "Date: " + compare[i].getDate().toString(), "Category: " + compare[i].getCategory().toString(), compare[i].getImageid().toString(), compare[i].getmEventnumber()));
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
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_reg_events);
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
            startActivity(new Intent(Registered_Events.this,Home.class));
        }
        else if(id==R.id.nav_Events)
        {
            DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_reg_events);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(id == R.id.nav_create_event)
        {
            startActivity(new Intent(Registered_Events.this,Create_Event.class));
        }

        else if(id== nav_profile)
        {
            startActivity(new Intent(Registered_Events.this,Profile.class));

        }
        else if(id==R.id.nav_settings)
        {
            startActivity(new Intent(Registered_Events.this,Settings.class));
        }
        else if(id==R.id.nav_your_event)
        {
            startActivity(new Intent(Registered_Events.this,CreatedEventList.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_reg_events);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

