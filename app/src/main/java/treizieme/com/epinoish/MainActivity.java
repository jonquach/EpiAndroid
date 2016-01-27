package treizieme.com.epinoish;

import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserData u = UserData.getInstance();
                Snackbar.make(view, "Your token is " + u.getToken(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPref = getPreferences(0);
        System.out.println("####### ------> TOKEN : " + sharedPref.getString("token", "failed"));
        LoginFragment frag = new LoginFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, frag);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment frag = null;

        if (id == R.id.nav_login_frag) {
            frag = new LoginFragment();
        }
        else if (id == R.id.nav_modules_frag) {
            frag = new ModuleFragment();
        } else if (id == R.id.nav_projects_frag) {
            frag = new ProjectFragment();
        } else if (id == R.id.nav_planning_frag) {
            frag = new PlanningFragment();
        } else if (id == R.id.nav_marks_frag) {
            frag =  new MarksFragement();
        }

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, frag);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadSingleProjectFragment(String scolaryear, String codemodule, String codeinstance, String codeacti) {
        Bundle bundle = new Bundle();
        bundle.putString("scolaryear", scolaryear);
        bundle.putString("codemodule", codemodule);
        bundle.putString("codeinstance", codeinstance);
        bundle.putString("codeacti", codeacti);
        SingleProjectFragment fragInfo = new SingleProjectFragment();
        fragInfo.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragInfo);
        ft.commit();
    }

    public void loadSingleModuleFragment(String scolaryear, String codemodule, String codeinstance) {
        Bundle bundle = new Bundle();
        bundle.putString("scolaryear", scolaryear);
        bundle.putString("codemodule", codemodule);
        bundle.putString("codeinstance", codeinstance);
        SingleModuleFragment fragInfo = new SingleModuleFragment();
        fragInfo.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragInfo);
        ft.commit();
    }

    public void loadTokenFragment(String scolaryear,String codemodule, String codeinstance, String codeacti, String codeevent) {
        Bundle bundle = new Bundle();
        bundle.putString("scolaryear", scolaryear);
        bundle.putString("codemodule", codemodule);
        bundle.putString("codeinstance", codeinstance);
        bundle.putString("codeacti", codeacti);
        bundle.putString("codeevent", codeevent);
        TokenFragment fragInfo = new TokenFragment();
        fragInfo.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragInfo);
        ft.commit();
    }
}
