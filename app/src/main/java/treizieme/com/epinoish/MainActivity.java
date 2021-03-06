package treizieme.com.epinoish;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    UserData userData = UserData.getInstance();
    Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                MenuItem item = ((NavigationView) findViewById(R.id.nav_view)).getMenu()
                        .findItem(R.id.nav_login_frag);

                if (userData.getToken() != null) {
                    item.setTitle("Logout");
                } else {
                    item.setTitle("Login");
                }
            }

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                MenuItem item = ((NavigationView) findViewById(R.id.nav_view)).getMenu()
                        .findItem(R.id.nav_login_frag);

                if (userData.getToken() != null) {
                    item.setTitle("Logout");
                } else {
                    item.setTitle("Login");
                }
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences sharedPref = getPreferences(0);
        if (sharedPref.getString("token", "failed").equals("failed")) {
            Fragment frag = new LoginFragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, frag);
            ft.commit();
        } else {
            userData.setLogin(sharedPref.getString("login", null));
            userData.setPassword(sharedPref.getString("password", null));
            userData.setToken(sharedPref.getString("token", null));

            Toast.makeText(this, "Already logged", Toast.LENGTH_SHORT).show();

            if (userData.getLogin() != null && userData.getToken() != null) {
                DrawerInfos di = new DrawerInfos((DrawerLayout) findViewById(R.id.drawer_layout));
                di.setDrawerInfos();
            }

            Fragment frag = new HomeFragment();
            toolbar.setTitle("Home");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, frag);
            ft.commit();
        }

        System.out.println("####### ------> TOKEN : " + sharedPref.getString("token", "failed"));
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment frag = null;
        Bundle bundle = new Bundle();

        if (id == R.id.nav_login_frag) {
            frag = new LoginFragment();
        } else if (id == R.id.nav_modules_frag) {
            frag = new ModuleFragment();
        } else if (id == R.id.nav_projects_frag) {
            bundle.putInt("registered", 1);
            frag = new ProjectFragment();
        } else if (id == R.id.nav_all_projects_frag) {
            bundle.putInt("registered", 0);
            frag = new ProjectFragment();
        }  else if (id == R.id.nav_planning_frag) {
            frag = new PlanningFragment();
        } else if (id == R.id.nav_marks_frag) {
            frag = new MarksFragement();
        } else if (id == R.id.nav_home_frag) {
            frag = new HomeFragment();
        } else if (id == R.id.nav_all_modules_frag) {
            frag = new AllModulesFragment();
        } else if (id == R.id.nav_trombi_frag) {
            frag = new TrombiFragment();
        }

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            frag.setArguments(bundle);
            ft.replace(R.id.content_frame, frag);
            ft.addToBackStack(null);
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
        ft.addToBackStack(null);
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
        ft.addToBackStack(null);
        ft.commit();
    }

    public void loadTokenFragment(String scolaryear, String codemodule, String codeinstance, String codeacti, String codeevent) {
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
        ft.addToBackStack(null);
        ft.commit();
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    public void loadPlanningFragment() {
        PlanningFragment fragInfo = new PlanningFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragInfo);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void loadFragment(Fragment toLoad) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, toLoad);
        ft.addToBackStack(null);
        ft.commit();
    }
}
