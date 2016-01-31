package treizieme.com.epinoish;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleProjectFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    private ProgressDialog progressDialog;
    String scolaryear = null;
    String codeinstance = null;
    String codemodule = null;
    String codeacti = null;
    TextView singleProjectTitle = null;
    TextView singleProjectDescription = null;

    public SingleProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_project, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        singleProjectDescription = (TextView) view.findViewById(R.id.single_project_description);
        singleProjectTitle = (TextView) view.findViewById(R.id.single_project_title);
        singleProjectDescription.setMovementMethod(new ScrollingMovementMethod());
        Bundle bundle = this.getArguments();
        scolaryear = bundle.getString("scolaryear");
        codeinstance = bundle.getString("codeinstance");
        codemodule = bundle.getString("codemodule");
        codeacti = bundle.getString("codeacti");

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
                    .url("http://epitech-api.herokuapp.com/project?token=" + token
                    + "&scolaryear=" + scolaryear
                    + "&codemodule=" + codemodule
                    + "&codeinstance=" + codeinstance
                    + "&codeacti=" + codeacti)
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
                JsonObject gObj = new Gson().fromJson(json, JsonObject.class);
                progressDialog.dismiss();
                singleProjectTitle.setText(gObj.get("project_title").toString()
                        .replace("\\n", "\n").replace("\"", ""));
                singleProjectDescription.setText(gObj.get("description").toString()
                        .replace("\\n", "\n").replace("\"", ""));
            }
        }
    }
}
