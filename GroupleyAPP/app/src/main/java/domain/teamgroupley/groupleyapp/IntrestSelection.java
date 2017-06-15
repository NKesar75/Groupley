package domain.teamgroupley.groupleyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class IntrestSelection extends AppCompatActivity {

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
        SmArchery        = (CheckBox)findViewById(R.id.Archery_CB_create);
        SmBaseball       = (CheckBox)findViewById(R.id.Base_CB_create);
        SBasketball      = (CheckBox)findViewById(R.id.BASK_CB_create);
        SCycling         = (CheckBox)findViewById(R.id.BIKE_CB_create);
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
    }
}
