package treizieme.com.epinoish;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleModuleFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    private ProgressDialog progressDialog;
    String scolaryear = null;
    String codeinstance = null;
    String codemodule = null;
    TextView singleModuleTitle = null;
    TextView singleModuleDescription = null;
    TextView singleModuleGrade = null;
    TextView singleModuleCredits = null;
    Button sub = null;
    Integer subscribed;

    public SingleModuleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_module, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        sub = (Button) view.findViewById(R.id.single_module_sub);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SubTask().execute();
            }
        });
        singleModuleTitle = (TextView) view.findViewById(R.id.single_module_title);
        singleModuleDescription = (TextView) view.findViewById(R.id.single_module_description);
        singleModuleGrade = (TextView) view.findViewById(R.id.single_module_grade);
        singleModuleCredits = (TextView) view.findViewById(R.id.single_module_credits);
        Bundle bundle = this.getArguments();
        singleModuleDescription.setMovementMethod(new ScrollingMovementMethod());
        scolaryear = bundle.getString("scolaryear");
        codeinstance = bundle.getString("codeinstance");
        codemodule = bundle.getString("codemodule");

        if (UserData.getInstance().getToken() != null) {
            new Task().execute();
        } else {
            progressDialog.dismiss();
        }

        return view;
    }

    private class Task extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String token = UserData.getInstance().getToken();

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/module?token=" + token
                            + "&scolaryear=" + scolaryear
                            + "&codemodule=" + codemodule
                            + "&codeinstance=" + codeinstance)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.code() == 200) {
                    return response.body().string();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            if (json != null) {
                try {
                    JsonObject gObj = new Gson().fromJson(json, JsonObject.class);
                    progressDialog.dismiss();
                    singleModuleTitle.setText(gObj.get("title").toString()
                            .replace("\\n", "\n").replace("\"", ""));
                    singleModuleDescription.setText(gObj.get("description").toString()
                            .replace("\\n", "\n").replace("\"", ""));
                    singleModuleGrade.setText(gObj.get("student_grade").toString().replace("\\n", "\n").replace("\"", ""));
                    singleModuleCredits.setText(gObj.get("credits").toString().replace("\\n", "\n").replace("\"", ""));
                    subscribed = gObj.get("student_registered").getAsInt();
                    if (gObj.get("student_registered").getAsInt() == 1) {
                        sub.setText("unsubscribe");
                    }
                } catch (JsonParseException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }


    private class SubTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String token = UserData.getInstance().getToken();

            RequestBody body = new FormBody.Builder()
                    .add("token", token)
                    .add("scolaryear", scolaryear)
                    .add("codemodule", codemodule)
                    .add("codeinstance", codeinstance)
                    .build();

            Request sub = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/module")
                    .post(body)
                    .build();

            Request delete = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/module")
                    .delete(body)
                    .build();

            try {
                Response response;
                if (subscribed != 1) {
                    response = client.newCall(sub).execute();
                } else {
                    response = client.newCall(delete).execute();
                }

                if (response.code() == 200) {
                    return response.body().string();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            if (subscribed != 1) {
                subscribed = 1;
                sub.setText("Unsubscribed");
            } else {
                subscribed = 0;
                sub.setText("Subscribed");
            }

        }
    }
}
