package treizieme.com.epinoish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {

    private String log;
    private String pass;
    private EditText login;
    private EditText password;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        login = (EditText) findViewById(R.id.input_login);
        password = (EditText) findViewById(R.id.input_password);

        // Login btn listener
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {

        if (loginValidate()) {

            btnLogin.setEnabled(false);
            progressDialog = new ProgressDialog(this);
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), "Bad login/password", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else if (!response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), "Unexpected code " +
                                                response.code() + " " + response.message(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                        throw new IOException("Unexpected code " + response);
                    } else {
                        try {
                            JSONObject json = new JSONObject(response.body().string());

                            // Save token
                            SharedPreferences sharedPref = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("login", log);
                            editor.putString("password", pass);
                            editor.putString("token", json.getString("token"));
                            editor.apply();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), "Login success", Toast.LENGTH_LONG).show();
                                // End login activity
                                finish();
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
