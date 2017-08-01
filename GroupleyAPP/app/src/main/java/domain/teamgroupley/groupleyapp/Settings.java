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
import android.view.MenuItem;

import static domain.teamgroupley.groupleyapp.R.id.nav_profile;

public class Settings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        draw = (DrawerLayout)findViewById(R.id.activity_settings);
        toggle = new ActionBarDrawerToggle(this,draw,R.string.open,R.string.close);

        draw.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigation = (NavigationView)findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_settings);
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
            startActivity(new Intent(Settings.this,Home.class));
        }
        else if(id==R.id.nav_Events)
        {
            startActivity(new Intent(Settings.this,Registered_Events.class));
        }
        else if(id == R.id.nav_create_event)
        {
            startActivity(new Intent(Settings.this,Create_Event.class));
        }

        else if(id== nav_profile)
        {
            startActivity(new Intent(Settings.this,Profile.class));

        }
        else if(id==R.id.nav_settings)
        {
            DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_settings);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(id==R.id.nav_your_event)
        {
            startActivity(new Intent(Settings.this,CreatedEventList.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_settings);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
