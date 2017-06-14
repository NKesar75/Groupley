package domain.teamgroupley.groupleyapp;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static domain.teamgroupley.groupleyapp.R.id.nav_profile;

public class navigation_Content extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation__content);
        HomePageFragment home = new HomePageFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment,home).commit();
        draw = (DrawerLayout)findViewById(R.id.drawLayout);
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
        int id = item.getItemId();
        if(id==R.id.nav_home)
            return true;
        else if(id==R.id.nav_Events)
            return true;
        else if(id==R.id.nav_create_event)
            return true;
        else if(id== nav_profile)
            return true;
        else if(id==R.id.nav_settings)
            return true;
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawLayout);
        if(drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.nav_home)
        {
            HomePageFragment home = new HomePageFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment,home).commit();
        }
        else if(id==R.id.nav_Events)
        {
            RegisteredEventsFragment eventsFragment = new RegisteredEventsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment,eventsFragment).commit();
        }
        else if(id == R.id.nav_create_event)
        {
            CreateEventFragment createevent = new CreateEventFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment,createevent,createevent.getTag()).commit();
        }

        else if(id== nav_profile)
        {
            ProfileFragment profileFragment = new ProfileFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment,profileFragment).commit();

        }
        else if(id==R.id.nav_settings)
        {
            SettingsFragment settingsFragment = new SettingsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment,settingsFragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
