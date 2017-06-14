package domain.teamgroupley.groupleyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.CheckBox;

public class Create_Interest extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__interest);

        SmArchery        = (CheckBox)findViewById(R.id.Archery_CB_create);
        SmBaseball       = (CheckBox)findViewById(R.id.Base_CB_create);
        SBasketball      = (CheckBox)findViewById(R.id.BASK_CB_create);
        SCycling         = (CheckBox)findViewById(R.id.BIKE_CB_create);
        SFootball        = (CheckBox)findViewById(R.id.Foot_cb_create)
        SFrisbe          = (CheckBox)findViewById(R.id.Fri_CB_create)
        SGolf            = (CheckBox)findViewById(R.id.Golf_CB_create)
        SHoccey          = (CheckBox)findViewById(R.id.Hoc_CB_create);
        SHunting         = (CheckBox)findViewById(R.id.Hunt_CB_create);
        SSkateboarding   = (CheckBox)findViewById(R.id.Skate_CB_create)
        SSnowBoarding    = (CheckBox)findViewById(R.id.Snow_CB_create)
        SWaterSports     = (CheckBox)findViewById(R.id.WS_CB_create)
        SWrestling       = (CheckBox)findViewById(R.id.Wres_CB_create)
        PFestivles       = (CheckBox)findViewById(R.id.Fest_CB_create)
        PHouseParites    = (CheckBox)findViewById(R.id.Hp_CB_create)
        PNightClubs      = (CheckBox)findViewById(R.id.Nc_CB_create)
        GAction          = (CheckBox)findViewById(R.id.Act_cb__create)
        GAdventure       = (CheckBox)findViewById(R.id.Ad_CB_create)
        GFPS             = (CheckBox)findViewById(R.id.)
        GIndies          = (CheckBox)findViewById(R.id.)
        GMMO             = (CheckBox)findViewById(R.id.)
        GPartfam         = (CheckBox)findViewById(R.id.)
        GRPG             = (CheckBox)findViewById(R.id.)
        GSim             = (CheckBox)findViewById(R.id.)
        GSports          = (CheckBox)findViewById(R.id.)
        GStragy          = (CheckBox)findViewById(R.id.)
        MCountry         = (CheckBox)findViewById(R.id.)
        MDrillrap        = (CheckBox)findViewById(R.id.)
        MEDM             = (CheckBox)findViewById(R.id.)
        MJAZZ            = (CheckBox)findViewById(R.id.)
        MRap             = (CheckBox)findViewById(R.id.)
        MRock            = (CheckBox)findViewById(R.id.)
        MRNB             = (CheckBox)findViewById(R.id.)
        MScremo          = (CheckBox)findViewById(R.id.)
        MoAction         = (CheckBox)findViewById(R.id.)
        MoAnimation      = (CheckBox)findViewById(R.id.)
        MoComdey         = (CheckBox)findViewById(R.id.)
        MoDoc            = (CheckBox)findViewById(R.id.)
        MoFam            = (CheckBox)findViewById(R.id.)
        MoHorror         = (CheckBox)findViewById(R.id.)
        MoMusical        = (CheckBox)findViewById(R.id.)
        MoSifi           = (CheckBox)findViewById(R.id.)
        MoSports         = (CheckBox)findViewById(R.id.)
        MoThriller       = (CheckBox)findViewById(R.id.)
        MoWar            = (CheckBox)findViewById(R.id.)
        TAction          = (CheckBox)findViewById(R.id.)
        TAdventure       = (CheckBox)findViewById(R.id.)
        TAnimation       = (CheckBox)findViewById(R.id.)
        TBiography       = (CheckBox)findViewById(R.id.)
        TComedy          = (CheckBox)findViewById(R.id.)
        TCrime           = (CheckBox)findViewById(R.id.)
        TDoc             = (CheckBox)findViewById(R.id.)
        TDrama           = (CheckBox)findViewById(R.id.)
        TFam             = (CheckBox)findViewById(R.id.)
        TGameShows       = (CheckBox)findViewById(R.id.)
        THistory         = (CheckBox)findViewById(R.id.)
        THorror          = (CheckBox)findViewById(R.id.)
        TMystery         = (CheckBox)findViewById(R.id.)
        TReal            = (CheckBox)findViewById(R.id.)
        TSit             = (CheckBox)findViewById(R.id.)
        TSports          = (CheckBox)findViewById(R.id.)
        TTalkShows       = (CheckBox)findViewById(R.id.)
        TWar             = (CheckBox)findViewById(R.id.)
        DActing          = (CheckBox)findViewById(R.id.)
        DCosplay         = (CheckBox)findViewById(R.id.)
        DLarp            = (CheckBox)findViewById(R.id.)
        CActionfigs      = (CheckBox)findViewById(R.id.)
        CCars            = (CheckBox)findViewById(R.id.)
        CCoins           = (CheckBox)findViewById(R.id.)
        CComics          = (CheckBox)findViewById(R.id.)
        CGuns            = (CheckBox)findViewById(R.id.)
        CTrucks          = (CheckBox)findViewById(R.id.)
    }
}
