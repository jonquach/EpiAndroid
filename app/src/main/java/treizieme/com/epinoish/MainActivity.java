package treizieme.com.epinoish;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final OkHttpClient client = new OkHttpClient();

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
                Snackbar.make(view, "Your token is " + u.getToken(), Snackbar.LENGTH_SHORT)
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
        if (sharedPref.getString("token", "failed").equals("failed")) {
            Fragment frag = new LoginFragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, frag);
            ft.commit();
        } else {
            UserData userData = UserData.getInstance();
            userData.setLogin(sharedPref.getString("login", null));
            userData.setPassword(sharedPref.getString("password", null));
            userData.setToken(sharedPref.getString("token", null));

            Toast.makeText(this, "Already logged", Toast.LENGTH_SHORT).show();

            if (userData.getLogin() != null) {
                new Task().execute();
            }

            Fragment frag = new HomeFragment();

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment frag = null;

        if (id == R.id.nav_login_frag) {
            frag = new LoginFragment();
        } else if (id == R.id.nav_modules_frag) {
            frag = new ModuleFragment();
        } else if (id == R.id.nav_projects_frag) {
            frag = new ProjectFragment();
        } else if (id == R.id.nav_planning_frag) {
            frag = new PlanningFragment();
        } else if (id == R.id.nav_marks_frag) {
            frag = new MarksFragement();
        } else if (id == R.id.nav_home_frag) {
            frag = new HomeFragment();
        }

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, frag);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private class Task extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            UserData userData = UserData.getInstance();
            String login = userData.getLogin();
            String token = userData.getToken();

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/user?user=" + login + "&token=" + token)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            Type listType = new TypeToken<User>() {
            }.getType();
            User user = new Gson().fromJson(json, listType);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            ImageView userImg = (ImageView) drawer.findViewById(R.id.user_picture);
            TextView username = (TextView) drawer.findViewById(R.id.user_name);
            TextView userInfo = (TextView) drawer.findViewById(R.id.user_info);

            username.setText(user.getFullName());
            String infos = user.getInternal_email() + "\n" +
                    "GPA: " + user.getGpa().get(0).get("gpa") + "\n" +
                    "Credits: " + user.getCredits().toString() + "\n" +
                    "Spices: " + user.getSpice().get("available_spice") + "\n" +
                    "Log time: " + user.getNsstat().get("active").toString() + "h" + "\n";

            userInfo.setText(infos);

            if (!user.getPicture().contains("null.")) {
                new DownloadImageTask((ImageView) drawer.findViewById(R.id.user_picture))
                        .execute(user.getPicture());
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap bm = null;

            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                bm = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bm;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
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
}
