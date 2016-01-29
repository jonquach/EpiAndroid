package treizieme.com.epinoish;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TokenFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    Button submit_token = null;
    EditText token = null;
    String scolaryear = null;
    String codeinstance = null;
    String codeacti = null;
    String codemodule = null;
    String codeevent = null;
    String tokencode = null;
    private ProgressDialog progressDialog;

    public TokenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_token, container, false);
        submit_token = (Button) view.findViewById(R.id.planning_submit_token);
        token = (EditText) view.findViewById(R.id.planning_field_token);
        Bundle bundle = this.getArguments();
        scolaryear = bundle.getString("scolaryear");
        codeinstance = bundle.getString("codeinstance");
        codemodule = bundle.getString("codemodule");
        codeacti = bundle.getString("codeacti");
        codeevent = bundle.getString("codeevent");
        submit_token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (token.getText().length() == 8) {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    tokencode = token.getText().toString();
                    new Task().execute();
                }
            }
        });
        return view;
    }

    private class Task extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences prefs = getActivity().getPreferences(0);
            String token = prefs.getString("token", "empty");

            RequestBody body = new FormBody.Builder()
                    .add("scolaryear", scolaryear)
                    .add("codeinstance", codeinstance)
                    .add("codemodule", codemodule)
                    .add("codeacti", codeacti)
                    .add("codeevent", codeevent)
                    .add("tokenvalidationcode", tokencode)
                    .add("token", token)
                    .build();

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/token")
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                System.out.println(response.body().string());
                progressDialog.dismiss();
                ((MainActivity) getActivity()).loadPlanningFragment();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
