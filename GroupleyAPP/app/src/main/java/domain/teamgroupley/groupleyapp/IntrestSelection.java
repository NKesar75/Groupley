package domain.teamgroupley.groupleyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import static domain.teamgroupley.groupleyapp.R.id.Actionfigure_CB;

public class IntrestSelection extends AppCompatActivity {

    private static final String TAG = "IntrestSelection";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mActionfigures = mRootRef.child(USerid).child("Interests").child("cactionfigures");
    DatabaseReference mCars = mRootRef.child(USerid).child("Interests").child("ccars");
    DatabaseReference mCoins = mRootRef.child(USerid).child("Interests").child("ccoins");
    DatabaseReference mComics = mRootRef.child(USerid).child("Interests").child("ccomics");
    DatabaseReference mGuns = mRootRef.child(USerid).child("Interests").child("cguns");
    DatabaseReference mTrucks = mRootRef.child(USerid).child("Interests").child("ctrucks");
    DatabaseReference mDActing = mRootRef.child(USerid).child("Interests").child("dacting");
    DatabaseReference mDcosplay = mRootRef.child(USerid).child("Interests").child("dcosplay");
    DatabaseReference mDlarping = mRootRef.child(USerid).child("Interests").child("dlarping");
    DatabaseReference mGAction = mRootRef.child(USerid).child("Interests").child("gaction");
    DatabaseReference mGAdventure = mRootRef.child(USerid).child("Interests").child("gadventure");
    DatabaseReference mGFps = mRootRef.child(USerid).child("Interests").child("gfps");
    DatabaseReference mGIndies = mRootRef.child(USerid).child("Interests").child("gindies");
    DatabaseReference mGMmo = mRootRef.child(USerid).child("Interests").child("gmmo");
    DatabaseReference mGPartiesFamily = mRootRef.child(USerid).child("Interests").child("gpartyfamily");
    DatabaseReference mGRpg = mRootRef.child(USerid).child("Interests").child("grpg");
    DatabaseReference mGSimulation = mRootRef.child(USerid).child("Interests").child("gsimulation");
    DatabaseReference mGsports = mRootRef.child(USerid).child("Interests").child("gsports");
    DatabaseReference mGStragies = mRootRef.child(USerid).child("Interests").child("gstragy");
    DatabaseReference mCountry = mRootRef.child(USerid).child("Interests").child("mcountry");
    DatabaseReference mDrillrap = mRootRef.child(USerid).child("Interests").child("mdrillrap");
    DatabaseReference mEdm = mRootRef.child(USerid).child("Interests").child("medm");
    DatabaseReference mJazz = mRootRef.child(USerid).child("Interests").child("mjazz");
    DatabaseReference mMOAction = mRootRef.child(USerid).child("Interests").child("moAction");
    DatabaseReference mMOAnimation = mRootRef.child(USerid).child("Interests").child("moAnimation");
    DatabaseReference mMOComdey = mRootRef.child(USerid).child("Interests").child("moComdey");
    DatabaseReference mMODocumentary = mRootRef.child(USerid).child("Interests").child("moDocumentary");
    DatabaseReference mMOFamily = mRootRef.child(USerid).child("Interests").child("moFamily");
    DatabaseReference mMOHorror = mRootRef.child(USerid).child("Interests").child("moHorror");
    DatabaseReference mMOMusical = mRootRef.child(USerid).child("Interests").child("moMusical");
    DatabaseReference mMOSifi = mRootRef.child(USerid).child("Interests").child("moSifi");
    DatabaseReference mMOSports = mRootRef.child(USerid).child("Interests").child("moSports");
    DatabaseReference mMOThriller = mRootRef.child(USerid).child("Interests").child("moThriller");
    DatabaseReference mMOWar = mRootRef.child(USerid).child("Interests").child("moWar");
    DatabaseReference mMRap = mRootRef.child(USerid).child("Interests").child("mrap");
    DatabaseReference mMRnb = mRootRef.child(USerid).child("Interests").child("mrnb");
    DatabaseReference mMRock = mRootRef.child(USerid).child("Interests").child("mrock");
    DatabaseReference mMScremo = mRootRef.child(USerid).child("Interests").child("mscremo");
    DatabaseReference mPFestivels = mRootRef.child(USerid).child("Interests").child("pfestivles");
    DatabaseReference mPHouseParites = mRootRef.child(USerid).child("Interests").child("phouseParites");
    DatabaseReference mPNightClubs = mRootRef.child(USerid).child("Interests").child("pnightClubs");
    DatabaseReference mSArchery = mRootRef.child(USerid).child("Interests").child("sarchery");
    DatabaseReference mSbaseball = mRootRef.child(USerid).child("Interests").child("sbaseball");
    DatabaseReference mSbasketball = mRootRef.child(USerid).child("Interests").child("sbasketball");
    DatabaseReference mSCycling = mRootRef.child(USerid).child("Interests").child("scycling");
    DatabaseReference mSFootball = mRootRef.child(USerid).child("Interests").child("sfootball");
    DatabaseReference mSFrisbe = mRootRef.child(USerid).child("Interests").child("sfrisbe");
    DatabaseReference mSGolf = mRootRef.child(USerid).child("Interests").child("sgolf");
    DatabaseReference mSHoccey = mRootRef.child(USerid).child("Interests").child("shoccey");
    DatabaseReference mSHunting = mRootRef.child(USerid).child("Interests").child("shunting");
    DatabaseReference mSSkateboarding = mRootRef.child(USerid).child("Interests").child("sskateboarding");
    DatabaseReference mSSnowboarding = mRootRef.child(USerid).child("Interests").child("ssnowBoarding");
    DatabaseReference mWatersports = mRootRef.child(USerid).child("Interests").child("swaterSports");
    DatabaseReference mSWrestlings = mRootRef.child(USerid).child("Interests").child("swrestling");
    DatabaseReference mTAction = mRootRef.child(USerid).child("Interests").child("taction");
    DatabaseReference mTAdventure = mRootRef.child(USerid).child("Interests").child("tadventure");
    DatabaseReference mTAnimation = mRootRef.child(USerid).child("Interests").child("tanimation");
    DatabaseReference mTBiography = mRootRef.child(USerid).child("Interests").child("tbiography");
    DatabaseReference mTComedy = mRootRef.child(USerid).child("Interests").child("tcomedy");
    DatabaseReference mTCrime = mRootRef.child(USerid).child("Interests").child("tcrime");
    DatabaseReference mTDocumentary = mRootRef.child(USerid).child("Interests").child("tdocoumentary");
    DatabaseReference mTDrama = mRootRef.child(USerid).child("Interests").child("tdrama");
    DatabaseReference mTFamily = mRootRef.child(USerid).child("Interests").child("tfamily");
    DatabaseReference mTGameshows = mRootRef.child(USerid).child("Interests").child("tgameShows");
    DatabaseReference mTHistory = mRootRef.child(USerid).child("Interests").child("thistory");
    DatabaseReference mTHorror = mRootRef.child(USerid).child("Interests").child("thorror");
    DatabaseReference mTMystery = mRootRef.child(USerid).child("Interests").child("tmystery");
    DatabaseReference mTReality = mRootRef.child(USerid).child("Interests").child("treality");
    DatabaseReference mTSitcom = mRootRef.child(USerid).child("Interests").child("tsitcom");
    DatabaseReference mTSports = mRootRef.child(USerid).child("Interests").child("tsports");
    DatabaseReference mTTalkshows = mRootRef.child(USerid).child("Interests").child("ttalkShows");
    DatabaseReference mTwar = mRootRef.child(USerid).child("Interests").child("twar");



    private Button save;


    private CheckBox SmArchery;
    private CheckBox SmBaseball;
    private CheckBox SBasketball;
    private CheckBox SCycling;
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




    public void savechanges()
    {
        save = (Button) findViewById(R.id.Save_CB);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean SArcy     = SmArchery.isChecked();
                boolean SBasy     = SmBaseball.isChecked();
                boolean SbKy      = SBasketball.isChecked();
                boolean Cycy      = SCycling.isChecked();
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

                mRootRef.child(USerid).child("Interests").child("cactionfigures").setValue(CActy);
                mRootRef.child(USerid).child("Interests").child("ccars").setValue(CCry);
                mRootRef.child(USerid).child("Interests").child("ccoins").setValue(Ccinsy);
                mRootRef.child(USerid).child("Interests").child("ccomics").setValue(Ccomy);
                mRootRef.child(USerid).child("Interests").child("cguns").setValue(CGuny);
                mRootRef.child(USerid).child("Interests").child("ctrucks").setValue(Ctrcy);
                mRootRef.child(USerid).child("Interests").child("dacting").setValue(Dacty);
                mRootRef.child(USerid).child("Interests").child("dcosplay").setValue(Dcosy);
                mRootRef.child(USerid).child("Interests").child("dlarping").setValue(Dlay);
                mRootRef.child(USerid).child("Interests").child("gaction").setValue(Gacty);
                mRootRef.child(USerid).child("Interests").child("gadventure").setValue(Gadvy);
                mRootRef.child(USerid).child("Interests").child("gfps").setValue(GFpy);
                mRootRef.child(USerid).child("Interests").child("gindies").setValue(Gindy);
                mRootRef.child(USerid).child("Interests").child("gmmo").setValue(GMMy);
                mRootRef.child(USerid).child("Interests").child("gpartyfamily").setValue(GpaFy);
                mRootRef.child(USerid).child("Interests").child("grpg").setValue(GRPy);
                mRootRef.child(USerid).child("Interests").child("gsimulation").setValue(Gsiy);
                mRootRef.child(USerid).child("Interests").child("gsports").setValue(Gspy);
                mRootRef.child(USerid).child("Interests").child("gstragy").setValue(GStry);
                mRootRef.child(USerid).child("Interests").child("mcountry").setValue(MCy);
                mRootRef.child(USerid).child("Interests").child("mdrillrap").setValue(MDRy);
                mRootRef.child(USerid).child("Interests").child("medm").setValue(MEdy);
                mRootRef.child(USerid).child("Interests").child("mjazz").setValue(MJzy);
                mRootRef.child(USerid).child("Interests").child("moAction").setValue(MoActy);
                mRootRef.child(USerid).child("Interests").child("moAnimation").setValue(MOAniy);
                mRootRef.child(USerid).child("Interests").child("moComdey").setValue(MOComy);
                mRootRef.child(USerid).child("Interests").child("moDocumentary").setValue(MODoy);
                mRootRef.child(USerid).child("Interests").child("moFamily").setValue(MOFy);
                mRootRef.child(USerid).child("Interests").child("moHorror").setValue(MOHOry);
                mRootRef.child(USerid).child("Interests").child("moMusical").setValue(MoMusy);
                mRootRef.child(USerid).child("Interests").child("moSifi").setValue(MOSiy);
                mRootRef.child(USerid).child("Interests").child("moSports").setValue(MOSpoy);
                mRootRef.child(USerid).child("Interests").child("moThriller").setValue(MOTHrily);
                mRootRef.child(USerid).child("Interests").child("moWar").setValue(MoWay);
                mRootRef.child(USerid).child("Interests").child("mrap").setValue(MRpy);
                mRootRef.child(USerid).child("Interests").child("mrnb").setValue(MRNy);
                mRootRef.child(USerid).child("Interests").child("mrock").setValue(Mroy);
                mRootRef.child(USerid).child("Interests").child("mscremo").setValue(MScry);
                mRootRef.child(USerid).child("Interests").child("pfestivles").setValue(Fesy);
                mRootRef.child(USerid).child("Interests").child("phouseParites").setValue(Housy);
                mRootRef.child(USerid).child("Interests").child("pnightClubs").setValue(Nighty);
                mRootRef.child(USerid).child("Interests").child("sarchery").setValue(SArcy);
                mRootRef.child(USerid).child("Interests").child("sbaseball").setValue(SBasy);
                mRootRef.child(USerid).child("Interests").child("sbasketball").setValue(SbKy);
                mRootRef.child(USerid).child("Interests").child("scycling").setValue(Cycy);
                mRootRef.child(USerid).child("Interests").child("sfootball").setValue(Footy);
                mRootRef.child(USerid).child("Interests").child("sfrisbe").setValue(Frisy);
                mRootRef.child(USerid).child("Interests").child("sgolf").setValue(SGofy);
                mRootRef.child(USerid).child("Interests").child("shoccey").setValue(Shockeyy);
                mRootRef.child(USerid).child("Interests").child("shunting").setValue(SHunty);
                mRootRef.child(USerid).child("Interests").child("sskateboarding").setValue(SSKatey);
                mRootRef.child(USerid).child("Interests").child("ssnowBoarding").setValue(SSnowy);
                mRootRef.child(USerid).child("Interests").child("swaterSports").setValue(Swsy);
                mRootRef.child(USerid).child("Interests").child("swrestling").setValue(Wrey);
                mRootRef.child(USerid).child("Interests").child("taction").setValue(TActy);
                mRootRef.child(USerid).child("Interests").child("tadventure").setValue(TADvy);
                mRootRef.child(USerid).child("Interests").child("tanimation").setValue(TAniy);
                mRootRef.child(USerid).child("Interests").child("tbiography").setValue(TBioy);
                mRootRef.child(USerid).child("Interests").child("tcomedy").setValue(TCom);
                mRootRef.child(USerid).child("Interests").child("tcrime").setValue(TCriy);
                mRootRef.child(USerid).child("Interests").child("tdocoumentary").setValue(TDoy);
                mRootRef.child(USerid).child("Interests").child("tdrama").setValue(TDray);
                mRootRef.child(USerid).child("Interests").child("tfamily").setValue(Tfay);
                mRootRef.child(USerid).child("Interests").child("tgameShows").setValue(TGamey);
                mRootRef.child(USerid).child("Interests").child("thistory").setValue(THisy);
                mRootRef.child(USerid).child("Interests").child("thorror").setValue(Thory);
                mRootRef.child(USerid).child("Interests").child("tmystery").setValue(TMysy);
                mRootRef.child(USerid).child("Interests").child("treality").setValue(Trey);
                mRootRef.child(USerid).child("Interests").child("tsitcom").setValue(Tsiy);
                mRootRef.child(USerid).child("Interests").child("tsports").setValue(TSpoy);
                mRootRef.child(USerid).child("Interests").child("ttalkShows").setValue(TTalky);
                mRootRef.child(USerid).child("Interests").child("twar").setValue(Tway);

                Intent changepage = new Intent(IntrestSelection.this, MenuPage.class);
                startActivity(changepage);
                Toast.makeText(IntrestSelection.this, "Interest Saved.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrest_selection);
        savechanges();
        SmArchery        = (CheckBox)findViewById(R.id.Archery_CB);
        SmBaseball       = (CheckBox)findViewById(R.id.Base_CB);
        SBasketball      = (CheckBox)findViewById(R.id.BASK_CB);
        SCycling         = (CheckBox)findViewById(R.id.BIKE_CB);
        SFootball        = (CheckBox)findViewById(R.id.Foot_cb);
        SFrisbe          = (CheckBox)findViewById(R.id.Fri_CB);
        SGolf            = (CheckBox)findViewById(R.id.Golf_CB);
        SHoccey          = (CheckBox)findViewById(R.id.Hoc_CB);
        SHunting         = (CheckBox)findViewById(R.id.Hunt_CB);
        SSkateboarding   = (CheckBox)findViewById(R.id.Skate_CB);
        SSnowBoarding    = (CheckBox)findViewById(R.id.Snow_CB);
        SWaterSports     = (CheckBox)findViewById(R.id.WS_CB);
        SWrestling       = (CheckBox)findViewById(R.id.Wres_CB);
        PFestivles       = (CheckBox)findViewById(R.id.Fest_CB);
        PHouseParites    = (CheckBox)findViewById(R.id.Hp_CB);
        PNightClubs      = (CheckBox)findViewById(R.id.Nc_CB);
        GAction          = (CheckBox)findViewById(R.id.Act_CB);
        GAdventure       = (CheckBox)findViewById(R.id.Ad_CB);
        GFPS             = (CheckBox)findViewById(R.id.Fps_CB);
        GIndies          = (CheckBox)findViewById(R.id.Ind_CB);
        GMMO             = (CheckBox)findViewById(R.id.Mmo_CB);
        GPartfam         = (CheckBox)findViewById(R.id.Pf_CB);
        GRPG             = (CheckBox)findViewById(R.id.Rpg_CB);
        GSim             = (CheckBox)findViewById(R.id.Sim_CB);
        GSports          = (CheckBox)findViewById(R.id.Spo_CB);
        GStragy          = (CheckBox)findViewById(R.id.Str_CB);
        MCountry         = (CheckBox)findViewById(R.id.Country_CB);
        MDrillrap        = (CheckBox)findViewById(R.id.Dr_CB);
        MEDM             = (CheckBox)findViewById(R.id.Edm_CB);
        MJAZZ            = (CheckBox)findViewById(R.id.Jazz_CB);
        MRap             = (CheckBox)findViewById(R.id.Rap_CB);
        MRock            = (CheckBox)findViewById(R.id.Rock_CB);
        MRNB             = (CheckBox)findViewById(R.id.Rnb_CB);
        MScremo          = (CheckBox)findViewById(R.id.Scremo_CB);
        MoAction         = (CheckBox)findViewById(R.id.Action_CB);
        MoAnimation      = (CheckBox)findViewById(R.id.Animation_CB);
        MoComdey         = (CheckBox)findViewById(R.id.Comedy_btn);
        MoDoc            = (CheckBox)findViewById(R.id.Documentary_CB);
        MoFam            = (CheckBox)findViewById(R.id.Family_CB);
        MoHorror         = (CheckBox)findViewById(R.id.Horror_CB);
        MoMusical        = (CheckBox)findViewById(R.id.Musical_CB);
        MoSifi           = (CheckBox)findViewById(R.id.SIFI_CB);
        MoSports         = (CheckBox)findViewById(R.id.Sports_CB);
        MoThriller       = (CheckBox)findViewById(R.id.Thriller_CB);
        MoWar            = (CheckBox)findViewById(R.id.War_CB);
        TAction          = (CheckBox)findViewById(R.id.TAct_CB);
        TAdventure       = (CheckBox)findViewById(R.id.Addy_CB);
        TAnimation       = (CheckBox)findViewById(R.id.Anima_CB);
        TBiography       = (CheckBox)findViewById(R.id.Bio_CB);
        TComedy          = (CheckBox)findViewById(R.id.Come_CB);
        TCrime           = (CheckBox)findViewById(R.id.Crime_CB);
        TDoc             = (CheckBox)findViewById(R.id.Docu_CB);
        TDrama           = (CheckBox)findViewById(R.id.Drama_CB);
        TFam             = (CheckBox)findViewById(R.id.Fam_CB);
        TGameShows       = (CheckBox)findViewById(R.id.Game_CB);
        THistory         = (CheckBox)findViewById(R.id.History_CB);
        THorror          = (CheckBox)findViewById(R.id.Hor_CB);
        TMystery         = (CheckBox)findViewById(R.id.Mystry_CB);
        TReal            = (CheckBox)findViewById(R.id.Real_CB);
        TSit             = (CheckBox)findViewById(R.id.Sitcom_CB);
        TSports          = (CheckBox)findViewById(R.id.Spor_CB);
        TTalkShows       = (CheckBox)findViewById(R.id.Talk_CB);
        TWar             = (CheckBox)findViewById(R.id.W_CB);
        DActing          = (CheckBox)findViewById(R.id.Acting_CB);
        DCosplay         = (CheckBox)findViewById(R.id.Cosplay_CB);
        DLarp            = (CheckBox)findViewById(R.id.Larp_CB);
        CActionfigs      = (CheckBox)findViewById(R.id.Actionfigure_CB);
        CCars            = (CheckBox)findViewById(R.id.Cars_CB);
        CCoins           = (CheckBox)findViewById(R.id.Coins_CB);
        CComics          = (CheckBox)findViewById(R.id.Comic_books_CB);
        CGuns            = (CheckBox)findViewById(R.id.Guns_CB);
        CTrucks          = (CheckBox)findViewById(R.id.Trucks_CB);

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
                    Toast.makeText(IntrestSelection.this, "Signed Out", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        mSArchery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                SmArchery.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSbaseball.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                SmBaseball.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSbasketball.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                SBasketball.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       mSCycling.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               boolean temp = dataSnapshot.getValue(boolean.class);
               SCycling.setChecked(temp);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
        mSFootball.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                SFootball.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSFrisbe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                SFrisbe.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSGolf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                SGolf.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSHoccey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                SHoccey.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSHunting.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                SHunting.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSSkateboarding.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                SSkateboarding.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSSnowboarding.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                SSnowBoarding.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mWatersports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                SWaterSports.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSWrestlings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                SWrestling.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mPFestivels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                PFestivles.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mPHouseParites.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                PHouseParites.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mPNightClubs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                PNightClubs.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mGAction.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                GAction.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mGAdventure.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                GAdventure.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mGFps.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                GFPS.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mGIndies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                GIndies.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mGMmo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                GMMO.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mGPartiesFamily.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                GPartfam.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mGRpg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                GRPG.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mGSimulation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                GSim.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mGsports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                GSports.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mGStragies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                GStragy.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mCountry.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MCountry.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDrillrap.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MDrillrap.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mEdm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MEDM.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mJazz.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MJAZZ.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMRap.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MRap.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMRock.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MRock.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMRnb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MRNB.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMScremo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MScremo.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMOAction.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MoAction.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMOAnimation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MoAnimation.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMOComdey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MoComdey.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMODocumentary.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MoDoc.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMOFamily.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MoFam.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMOHorror.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MoHorror.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMOMusical.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MoMusical.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMOSifi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MoSifi.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMOSports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MoSports.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMOThriller.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MoThriller.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMOWar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                MoWar.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTAction.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TAction.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTAdventure.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TAdventure.setChecked(temp);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTAnimation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TAnimation.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTBiography.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TBiography.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTComedy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TComedy.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTCrime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TCrime.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTDocumentary.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TDoc.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTDrama.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TDrama.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTFamily.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TFam.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTGameshows.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TGameShows.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                THistory.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTHorror.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                THorror.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTMystery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TMystery.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTReality.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TReal.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTSitcom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TSit.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTSports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TSports.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTTalkshows.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TTalkShows.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTwar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                TWar.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDActing.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                DActing.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDcosplay.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                DCosplay.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDlarping.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                DLarp.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mActionfigures.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                CActionfigs.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mCars.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                CCars.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mCoins.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                CCoins.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mComics.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                CComics.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mGuns.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                CGuns.setChecked(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTrucks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                CTrucks.setChecked(temp);
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
}
