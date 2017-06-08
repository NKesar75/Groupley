package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class event_page extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener{

    public ImageButton btn2;
    public void des1()
    {
        btn2 = (ImageButton) findViewById(R.id.btn_1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(event_page.this,descrpition1.class);
                startActivity(changepage2);
            }
        });
    }
    public ImageButton btn;
    public void des2()
    {
        btn = (ImageButton) findViewById(R.id.btn_2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(event_page.this,descrpition2.class);
                startActivity(changepage2);
            }
        });
    }

    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        des1();
        des2();
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
        else if(id==R.id.nav_profile)
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

        }
        else if(id==R.id.nav_Events)
        {

        }
        else if(id == R.id.nav_create_event)
        {
            CreateEventFragment createevent = new CreateEventFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.drawLayout,createevent,createevent.getTag()).commit();
        }

        else if(id==R.id.nav_profile)
        {

        }
        else if(id==R.id.nav_settings)
        {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
