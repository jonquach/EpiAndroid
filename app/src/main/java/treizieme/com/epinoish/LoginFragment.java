package treizieme.com.epinoish;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private String log;
    private String pass;
    private EditText login;
    private EditText password;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private final OkHttpClient client = new OkHttpClient();


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (UserData.getInstance().getToken() != null) {
            View view = inflater.inflate(R.layout.fragment_logout, container, false);

            Button btnLogout = (Button) view.findViewById(R.id.btn_logout);

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logout();
                }
            });

            return view;
        }

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ((MainActivity) getActivity()).setToolbarTitle("Login");

        login = (EditText) view.findViewById(R.id.input_login);
        password = (EditText) view.findViewById(R.id.input_password);

        btnLogin = (Button) view.findViewById(R.id.btn_login);

        SharedPreferences sharedPref = getActivity().getPreferences(0);
        String tmp;

        if ((tmp = sharedPref.getString("login", null)) != null) {
            login.setText(tmp);
        } else if ((tmp = sharedPref.getString("password", null)) != null) {
            password.setText(tmp);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        return view;
    }

    private void logout() {
        SharedPreferences sharedPref = getActivity().getPreferences(0);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.remove("token");
        editor.apply();
        UserData.getInstance().setToken(null);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

        Fragment frag = new LoginFragment();

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, frag);
        ft.commit();
    }

    private void login() {

        if (loginValidate()) {

            btnLogin.setEnabled(false);
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Login in progress...");
            progressDialog.show();

            RequestBody body = new FormBody.Builder()
                    .add("login", log)
                    .addEncoded("password", pass)
                    .build();

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/login")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(final Response response) throws IOException {
                    progressDialog.dismiss();

                    if (response.code() == 401) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getBaseContext(), "Bad login/password", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else if (!response.isSuccessful()) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getBaseContext(), "Unexpected code " +
                                                response.code() + " " + response.message(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                        throw new IOException("Unexpected code " + response);
                    } else {
                        try {
                            JSONObject json = new JSONObject(response.body().string());

                            // Save token
                            UserData userData = UserData.getInstance();
                            userData.setLogin(log);
                            userData.setPassword(pass);
                            userData.setToken(json.getString("token"));

                            SharedPreferences sharedPref = getActivity().getPreferences(0);
                            SharedPreferences.Editor editor = sharedPref.edit();

                            editor.putString("login", userData.getLogin());
                            editor.putString("password", userData.getPassword());
                            editor.putString("token", userData.getToken());
                            editor.apply();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getBaseContext(), "Login success", Toast.LENGTH_LONG).show();
                                // End login fragment here call to profile view

                                DrawerInfos di = new DrawerInfos((DrawerLayout) getActivity().findViewById(R.id.drawer_layout));
                                di.setDrawerInfos();

                                Fragment frag = new HomeFragment();

                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, frag);
                                ft.commit();
                            }
                        });
                    }
                }
            });
        }
        btnLogin.setEnabled(true);
    }

    private boolean loginValidate() {

        log = login.getText().toString();
        pass = password.getText().toString();
        boolean isValid = true;

        if (log.isEmpty()) {
            login.setError("Please provide your login");
            isValid = false;
        } else {
            login.setError(null);
        }

        if (pass.isEmpty()) {
            password.setError("Please provide your password");
            isValid = false;
        } else {
            password.setError(null);
        }

        return isValid;
    }
}
