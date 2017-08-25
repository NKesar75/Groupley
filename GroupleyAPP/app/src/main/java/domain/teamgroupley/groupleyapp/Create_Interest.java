package domain.teamgroupley.groupleyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Create_Interest extends AppCompatActivity {

    private static final String TAG = "Create_Intrest";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private Button SaveIntre;

    private CheckBox SmArchery;
    private CheckBox SmBaseball;
    private CheckBox SBasketball;
    private CheckBox SCycling;
    private CheckBox SFishing;
    private CheckBox SFootball;
    private CheckBox SFrisbe;
    private CheckBox SGolf;
    private CheckBox SHoccey;
    private CheckBox SHunting;
    private CheckBox SSkateboarding;
    private CheckBox SSnowBoarding;
    private CheckBox SWaterSports;
    private CheckBox SWrestling;
    private CheckBox PFestivles;
    private CheckBox PHouseParites;
    private CheckBox PNightClubs;
    private CheckBox GAction;
    private CheckBox GAdventure;
    private CheckBox GFPS;
    private CheckBox GIndies;
    private CheckBox GMMO;
    private CheckBox GPartfam;
    private CheckBox GRPG;
    private CheckBox GSim;
    private CheckBox GSports;
    private CheckBox GStragy;
    private CheckBox MCountry;
    private CheckBox MDrillrap;
    private CheckBox MEDM;
    private CheckBox MJAZZ;
    private CheckBox MRap;
    private CheckBox MRock;
    private CheckBox MRNB;
    private CheckBox MScremo;
    private CheckBox MoAction;
    private CheckBox MoAnimation;
    private CheckBox MoComdey;
    private CheckBox MoDoc;
    private CheckBox MoFam;
    private CheckBox MoHorror;
    private CheckBox MoMusical;
    private CheckBox MoSifi;
    private CheckBox MoSports;
    private CheckBox MoThriller;
    private CheckBox MoWar;
    private CheckBox TAction;
    private CheckBox TAdventure;
    private CheckBox TAnimation;
    private CheckBox TBiography;
    private CheckBox TComedy;
    private CheckBox TCrime;
    private CheckBox TDoc;
    private CheckBox TDrama;
    private CheckBox TFam;
    private CheckBox TGameShows;
    private CheckBox THistory;
    private CheckBox THorror;
    private CheckBox TMystery;
    private CheckBox TReal;
    private CheckBox TSit;
    private CheckBox TSports;
    private CheckBox TTalkShows;
    private CheckBox TWar;
    private CheckBox DActing;
    private CheckBox DCosplay;
    private CheckBox DLarp;
    private CheckBox CActionfigs;
    private CheckBox CCars;
    private CheckBox CCoins;
    private CheckBox CComics;
    private CheckBox CGuns;
    private CheckBox CTrucks;

    public void Setintrest(){
        SaveIntre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int interstcounter = 0;

                boolean SArcy     = SmArchery.isChecked();
                boolean SBasy     = SmBaseball.isChecked();
                boolean SbKy      = SBasketball.isChecked();
                boolean Cycy      = SCycling.isChecked();
                boolean Fish      = SFishing.isChecked();
                boolean Footy     = SFootball.isChecked();
                boolean Frisy     = SFrisbe.isChecked();
                boolean SGofy     = SGolf.isChecked();
                boolean Shockeyy  = SHoccey.isChecked();
                boolean SHunty    = SHunting.isChecked();
                boolean SSKatey   = SSkateboarding.isChecked();
                boolean SSnowy    = SSnowBoarding.isChecked();
                boolean Swsy      = SWaterSports.isChecked();
                boolean Wrey      = SWrestling.isChecked();
                boolean Fesy      = PFestivles.isChecked();
                boolean Housy     = PHouseParites.isChecked();
                boolean Nighty    = PNightClubs.isChecked();
                boolean Gacty     = GAction.isChecked();
                boolean Gadvy     = GAdventure.isChecked();
                boolean GFpy      = GFPS.isChecked();
                boolean Gindy     = GIndies.isChecked();
                boolean GMMy      = GMMO.isChecked();
                boolean GpaFy     = GPartfam.isChecked();
                boolean GRPy      = GRPG.isChecked();
                boolean Gsiy      = GSim.isChecked();
                boolean Gspy      = GSports.isChecked();
                boolean GStry     = GStragy.isChecked();
                boolean MCy       = MCountry.isChecked();
                boolean MDRy      = MDrillrap.isChecked();
                boolean MEdy      = MEDM.isChecked();
                boolean MJzy      = MJAZZ.isChecked();
                boolean MRpy      = MRap.isChecked();
                boolean Mroy      = MRock.isChecked();
                boolean MRNy      = MRNB.isChecked();
                boolean MScry     = MScremo.isChecked();
                boolean MoActy    = MoAction.isChecked();
                boolean MOAniy    = MoAnimation.isChecked();
                boolean MOComy    = MoComdey.isChecked();
                boolean MODoy     = MoDoc.isChecked();
                boolean MOFy      = MoFam.isChecked();
                boolean MOHOry    = MoHorror.isChecked();
                boolean MoMusy    = MoMusical.isChecked();
                boolean MOSiy     = MoSifi.isChecked();
                boolean MOSpoy    = MoSports.isChecked();
                boolean MOTHrily  = MoThriller.isChecked();
                boolean MoWay     = MoWar.isChecked();
                boolean TActy     = TAction.isChecked();
                boolean TADvy     = TAdventure.isChecked();
                boolean TAniy     = TAnimation.isChecked();
                boolean TBioy     = TBiography.isChecked();
                boolean TCom      = TComedy.isChecked();
                boolean TCriy     = TCrime.isChecked();
                boolean TDoy      = TDoc.isChecked();
                boolean TDray     = TDrama.isChecked();
                boolean Tfay      = TFam.isChecked();
                boolean TGamey    = TGameShows.isChecked();
                boolean THisy     = THistory.isChecked();
                boolean Thory     = THorror.isChecked();
                boolean TMysy     = TMystery.isChecked();
                boolean Trey      = TReal.isChecked();
                boolean Tsiy      = TSit.isChecked();
                boolean TSpoy     = TSports.isChecked();
                boolean TTalky    = TTalkShows.isChecked();
                boolean Tway      = TWar.isChecked();
                boolean Dacty     = DActing.isChecked();
                boolean Dcosy     = DCosplay.isChecked();
                boolean Dlay      = DLarp.isChecked();
                boolean CActy     = CActionfigs.isChecked();
                boolean CCry      = CCars.isChecked();
                boolean Ccinsy    = CCoins.isChecked();
                boolean Ccomy     = CComics.isChecked();
                boolean CGuny     = CGuns.isChecked();
                boolean Ctrcy     = CTrucks.isChecked();

                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();

                if (SArcy){++interstcounter;}

                if (SBasy){++interstcounter;}

                if (SbKy){++interstcounter;}

                if (Cycy){++interstcounter;}

                if (Fish){++interstcounter;}

                if (Footy){++interstcounter;}

                if (Frisy){++interstcounter;}

                if (SGofy){++interstcounter;}

                if (Shockeyy){++interstcounter;}

                if (SHunty){++interstcounter;}

                if (SSKatey){++interstcounter;}

                if (SSnowy){++interstcounter;}

                if (Swsy){++interstcounter;}

                if (Wrey){++interstcounter;}

                if (Fesy){++interstcounter;}

                if (Housy){++interstcounter;}

                if (Nighty){++interstcounter;}

                if (Gacty){++interstcounter;}

                if (Gadvy){++interstcounter;}

                if (GFpy){++interstcounter;}

                if (Gindy){++interstcounter;}

                if (GMMy) {++interstcounter;}

                if (GpaFy){++interstcounter;}

                if (GRPy){++interstcounter;}

                if (Gsiy){++interstcounter;}

                if (Gspy){++interstcounter;}

                if (GStry){++interstcounter;}

                if (MCy){++interstcounter;}

                if (MDRy){++interstcounter;}

                if (MEdy){++interstcounter;}

                if (MJzy){++interstcounter;}

                if (MRpy){++interstcounter;}

                if (Mroy){++interstcounter;}

                if (MRNy){++interstcounter;}

                if (MScry){++interstcounter;}

                if (MoActy){++interstcounter;}

                if (MOAniy){++interstcounter;}

                if (MOComy){++interstcounter;}

                if (MODoy){++interstcounter;}

                if (MOFy){++interstcounter;}

                if (MOHOry){++interstcounter;}

                if (MoMusy){++interstcounter;}

                if (MOSiy){++interstcounter;}

                if (MOSpoy){++interstcounter;}

                if (MOTHrily){++interstcounter;}

                if (MoWay){++interstcounter;}

                if (TActy){++interstcounter;}

                if (TADvy){++interstcounter;}

                if (TAniy){++interstcounter;}

                if (TBioy){++interstcounter;}

                if (TCom){++interstcounter;}

                if (TCriy){++interstcounter;}

                if (TDoy){++interstcounter;}

                if (TDray){++interstcounter;}

                if (Tfay){++interstcounter;}

                if (TGamey){++interstcounter;}

                if (THisy){++interstcounter;}

                if (Thory){++interstcounter;}

                if (TMysy){++interstcounter;}

                if (Trey){++interstcounter;}

                if (Tsiy){++interstcounter;}

                if (TSpoy){++interstcounter;}

                if (TTalky){++interstcounter;}

                if (Tway){++interstcounter;}

                if (Dacty){++interstcounter;}

                if (Dcosy){++interstcounter;}

                if (Dlay){++interstcounter;}

                if (CActy){++interstcounter;}

                if (CCry){++interstcounter;}

                if (Ccinsy){++interstcounter;}

                if (Ccomy){++interstcounter;}

                if (CGuny){++interstcounter;}

                if (Ctrcy){++interstcounter;}


                if (interstcounter >= 10) {
                    myRef.child(userID).child("Interests").child("cactionfigures").setValue(CActy);
                    myRef.child(userID).child("Interests").child("ccars").setValue(CCry);
                    myRef.child(userID).child("Interests").child("ccoins").setValue(Ccinsy);
                    myRef.child(userID).child("Interests").child("ccomics").setValue(Ccomy);
                    myRef.child(userID).child("Interests").child("cguns").setValue(CGuny);
                    myRef.child(userID).child("Interests").child("ctrucks").setValue(Ctrcy);
                    myRef.child(userID).child("Interests").child("dacting").setValue(Dacty);
                    myRef.child(userID).child("Interests").child("dcosplay").setValue(Dcosy);
                    myRef.child(userID).child("Interests").child("dlarping").setValue(Dlay);
                    myRef.child(userID).child("Interests").child("gaction").setValue(Gacty);
                    myRef.child(userID).child("Interests").child("gadventure").setValue(Gadvy);
                    myRef.child(userID).child("Interests").child("gfps").setValue(GFpy);
                    myRef.child(userID).child("Interests").child("gindies").setValue(Gindy);
                    myRef.child(userID).child("Interests").child("gmmo").setValue(GMMy);
                    myRef.child(userID).child("Interests").child("gpartyfamily").setValue(GpaFy);
                    myRef.child(userID).child("Interests").child("grpg").setValue(GRPy);
                    myRef.child(userID).child("Interests").child("gsimulation").setValue(Gsiy);
                    myRef.child(userID).child("Interests").child("gsports").setValue(Gspy);
                    myRef.child(userID).child("Interests").child("gstragy").setValue(GStry);
                    myRef.child(userID).child("Interests").child("mcountry").setValue(MCy);
                    myRef.child(userID).child("Interests").child("mdrillrap").setValue(MDRy);
                    myRef.child(userID).child("Interests").child("medm").setValue(MEdy);
                    myRef.child(userID).child("Interests").child("mjazz").setValue(MJzy);
                    myRef.child(userID).child("Interests").child("moAction").setValue(MoActy);
                    myRef.child(userID).child("Interests").child("moAnimation").setValue(MOAniy);
                    myRef.child(userID).child("Interests").child("moComdey").setValue(MOComy);
                    myRef.child(userID).child("Interests").child("moDocumentary").setValue(MODoy);
                    myRef.child(userID).child("Interests").child("moFamily").setValue(MOFy);
                    myRef.child(userID).child("Interests").child("moHorror").setValue(MOHOry);
                    myRef.child(userID).child("Interests").child("moMusical").setValue(MoMusy);
                    myRef.child(userID).child("Interests").child("moSifi").setValue(MOSiy);
                    myRef.child(userID).child("Interests").child("moSports").setValue(MOSpoy);
                    myRef.child(userID).child("Interests").child("moThriller").setValue(MOTHrily);
                    myRef.child(userID).child("Interests").child("moWar").setValue(MoWay);
                    myRef.child(userID).child("Interests").child("mrap").setValue(MRpy);
                    myRef.child(userID).child("Interests").child("mrnb").setValue(MRNy);
                    myRef.child(userID).child("Interests").child("mrock").setValue(Mroy);
                    myRef.child(userID).child("Interests").child("mscremo").setValue(MScry);
                    myRef.child(userID).child("Interests").child("pfestivles").setValue(Fesy);
                    myRef.child(userID).child("Interests").child("phouseParites").setValue(Housy);
                    myRef.child(userID).child("Interests").child("pnightClubs").setValue(Nighty);
                    myRef.child(userID).child("Interests").child("sarchery").setValue(SArcy);
                    myRef.child(userID).child("Interests").child("sbaseball").setValue(SBasy);
                    myRef.child(userID).child("Interests").child("sbasketball").setValue(SbKy);
                    myRef.child(userID).child("Interests").child("scycling").setValue(Cycy);
                    myRef.child(userID).child("Interests").child("sfishing").setValue(Fish);
                    myRef.child(userID).child("Interests").child("sfootball").setValue(Footy);
                    myRef.child(userID).child("Interests").child("sfrisbe").setValue(Frisy);
                    myRef.child(userID).child("Interests").child("sgolf").setValue(SGofy);
                    myRef.child(userID).child("Interests").child("shoccey").setValue(Shockeyy);
                    myRef.child(userID).child("Interests").child("shunting").setValue(SHunty);
                    myRef.child(userID).child("Interests").child("sskateboarding").setValue(SSKatey);
                    myRef.child(userID).child("Interests").child("ssnowBoarding").setValue(SSnowy);
                    myRef.child(userID).child("Interests").child("swaterSports").setValue(Swsy);
                    myRef.child(userID).child("Interests").child("swrestling").setValue(Wrey);
                    myRef.child(userID).child("Interests").child("taction").setValue(TActy);
                    myRef.child(userID).child("Interests").child("tadventure").setValue(TADvy);
                    myRef.child(userID).child("Interests").child("tanimation").setValue(TAniy);
                    myRef.child(userID).child("Interests").child("tbiography").setValue(TBioy);
                    myRef.child(userID).child("Interests").child("tcomedy").setValue(TCom);
                    myRef.child(userID).child("Interests").child("tcrime").setValue(TCriy);
                    myRef.child(userID).child("Interests").child("tdocoumentary").setValue(TDoy);
                    myRef.child(userID).child("Interests").child("tdrama").setValue(TDray);
                    myRef.child(userID).child("Interests").child("tfamily").setValue(Tfay);
                    myRef.child(userID).child("Interests").child("tgameShows").setValue(TGamey);
                    myRef.child(userID).child("Interests").child("thistory").setValue(THisy);
                    myRef.child(userID).child("Interests").child("thorror").setValue(Thory);
                    myRef.child(userID).child("Interests").child("tmystery").setValue(TMysy);
                    myRef.child(userID).child("Interests").child("treality").setValue(Trey);
                    myRef.child(userID).child("Interests").child("tsitcom").setValue(Tsiy);
                    myRef.child(userID).child("Interests").child("tsports").setValue(TSpoy);
                    myRef.child(userID).child("Interests").child("ttalkShows").setValue(TTalky);
                    myRef.child(userID).child("Interests").child("twar").setValue(Tway);

                    final ProgressDialog progressDialog = new ProgressDialog(Create_Interest.this);
                    progressDialog.setMax(100);
                    progressDialog.setMessage("Please Wait.....");
                    progressDialog.setTitle("Creating Account");
                    progressDialog.setIcon(R.mipmap.app_logo);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run()
                        {
                            int progress = 0;
                            while (progress <= 100)
                            {
                                try
                                {
                                    progressDialog.setProgress(progress);
                                    progress++;
                                    Thread.sleep(100);
                                }
                                catch (Exception e)
                                {
                                }
                            }
                            progressDialog.dismiss();

                            Create_Interest.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent changepage = new Intent(Create_Interest.this, Home.class);
                                    startActivity(changepage);
                                    Toast.makeText(Create_Interest.this, "Account Created.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    t.start();
                    progressDialog.show();
                }
                else {
                    Toast.makeText(Create_Interest.this, "Select Atleast 10 Interests", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__interest);
        SaveIntre        = (Button)findViewById(R.id.Create_Account_btn_INT);
        SmArchery        = (CheckBox)findViewById(R.id.Archery_CB_create);
        SmBaseball       = (CheckBox)findViewById(R.id.Base_CB_create);
        SBasketball      = (CheckBox)findViewById(R.id.BASK_CB_create);
        SCycling         = (CheckBox)findViewById(R.id.BIKE_CB_create);
        SFishing         = (CheckBox)findViewById(R.id.Fish_cb_create);
        SFootball        = (CheckBox)findViewById(R.id.Foot_cb_create);
        SFrisbe          = (CheckBox)findViewById(R.id.Fri_CB_create);
        SGolf            = (CheckBox)findViewById(R.id.Golf_CB_create);
        SHoccey          = (CheckBox)findViewById(R.id.Hoc_CB_create);
        SHunting         = (CheckBox)findViewById(R.id.Hunt_CB_create);
        SSkateboarding   = (CheckBox)findViewById(R.id.Skate_CB_create);
        SSnowBoarding    = (CheckBox)findViewById(R.id.Snow_CB_create);
        SWaterSports     = (CheckBox)findViewById(R.id.WS_CB_create);
        SWrestling       = (CheckBox)findViewById(R.id.Wres_CB_create);
        PFestivles       = (CheckBox)findViewById(R.id.Fest_CB_create);
        PHouseParites    = (CheckBox)findViewById(R.id.Hp_CB_create);
        PNightClubs      = (CheckBox)findViewById(R.id.Nc_CB_create);
        GAction          = (CheckBox)findViewById(R.id.Act_cb__create);
        GAdventure       = (CheckBox)findViewById(R.id.Ad_CB_create);
        GFPS             = (CheckBox)findViewById(R.id.Fps_CB_create);
        GIndies          = (CheckBox)findViewById(R.id.Ind_CB_create);
        GMMO             = (CheckBox)findViewById(R.id.Mmo_CB_create);
        GPartfam         = (CheckBox)findViewById(R.id.Pf_CB_create);
        GRPG             = (CheckBox)findViewById(R.id.Rpg_CB_create);
        GSim             = (CheckBox)findViewById(R.id.Sim_CB_create);
        GSports          = (CheckBox)findViewById(R.id.Spor_CB_create);
        GStragy          = (CheckBox)findViewById(R.id.Str_CB_create);
        MCountry         = (CheckBox)findViewById(R.id.Country_CB_create);
        MDrillrap        = (CheckBox)findViewById(R.id.Dr_CB_create);
        MEDM             = (CheckBox)findViewById(R.id.Edm_CB_create);
        MJAZZ            = (CheckBox)findViewById(R.id.Jazz_CB_create);
        MRap             = (CheckBox)findViewById(R.id.Rap_CB_create);
        MRock            = (CheckBox)findViewById(R.id.Rock_CB_create);
        MRNB             = (CheckBox)findViewById(R.id.Rnb_CB_create);
        MScremo          = (CheckBox)findViewById(R.id.Scremo_CB_create);
        MoAction         = (CheckBox)findViewById(R.id.Action_CB_create);
        MoAnimation      = (CheckBox)findViewById(R.id.Animation_CB_create);
        MoComdey         = (CheckBox)findViewById(R.id.Comedy_btn_create);
        MoDoc            = (CheckBox)findViewById(R.id.Documentary_CB_create);
        MoFam            = (CheckBox)findViewById(R.id.Family_CB_create);
        MoHorror         = (CheckBox)findViewById(R.id.Horror_CB_create);
        MoMusical        = (CheckBox)findViewById(R.id.Musical_CB_create);
        MoSifi           = (CheckBox)findViewById(R.id.SIFI_CB_create);
        MoSports         = (CheckBox)findViewById(R.id.Sports_CB_create);
        MoThriller       = (CheckBox)findViewById(R.id.Thriller_CB_create);
        MoWar            = (CheckBox)findViewById(R.id.War_CB_create);
        TAction          = (CheckBox)findViewById(R.id.Act_CB_create);
        TAdventure       = (CheckBox)findViewById(R.id.Addy_CB_create);
        TAnimation       = (CheckBox)findViewById(R.id.Anima_CB_create);
        TBiography       = (CheckBox)findViewById(R.id.Bio_CB_create);
        TComedy          = (CheckBox)findViewById(R.id.Come_CB_create);
        TCrime           = (CheckBox)findViewById(R.id.Crime_CB_create);
        TDoc             = (CheckBox)findViewById(R.id.Docu_CB_create);
        TDrama           = (CheckBox)findViewById(R.id.Drama_CB_create);
        TFam             = (CheckBox)findViewById(R.id.Fam_CB_create);
        TGameShows       = (CheckBox)findViewById(R.id.Game_CB_create);
        THistory         = (CheckBox)findViewById(R.id.History_CB_create);
        THorror          = (CheckBox)findViewById(R.id.Hor_CB_create);
        TMystery         = (CheckBox)findViewById(R.id.Mystry_CB_create);
        TReal            = (CheckBox)findViewById(R.id.Real_CB_create);
        TSit             = (CheckBox)findViewById(R.id.Sitcom_CB_create);
        TSports          = (CheckBox)findViewById(R.id.Spo_CB_create);
        TTalkShows       = (CheckBox)findViewById(R.id.Talk_CB_create);
        TWar             = (CheckBox)findViewById(R.id.W_CB_create);
        DActing          = (CheckBox)findViewById(R.id.Acting_CB_create);
        DCosplay         = (CheckBox)findViewById(R.id.Cosplay_CB_create);
        DLarp            = (CheckBox)findViewById(R.id.Larp_CB_create);
        CActionfigs      = (CheckBox)findViewById(R.id.Actionfigure_CB_create);
        CCars            = (CheckBox)findViewById(R.id.Cars_CB_create);
        CCoins           = (CheckBox)findViewById(R.id.Coins_CB_create);
        CComics          = (CheckBox)findViewById(R.id.Comic_books_CB_create);
        CGuns            = (CheckBox)findViewById(R.id.Guns_CB_create);
        CTrucks          = (CheckBox)findViewById(R.id.Trucks_CB_create);


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

        Setintrest();
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
