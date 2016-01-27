package treizieme.com.epinoish;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
        singleModuleTitle = (TextView) view.findViewById(R.id.single_module_title);
        singleModuleDescription = (TextView) view.findViewById(R.id.single_module_description);
        singleModuleGrade = (TextView) view.findViewById(R.id.single_module_grade);
        singleModuleCredits = (TextView) view.findViewById(R.id.single_module_credits);
        Bundle bundle = this.getArguments();
        singleModuleDescription.setMovementMethod(new ScrollingMovementMethod());
        scolaryear = bundle.getString("scolaryear");
        codeinstance = bundle.getString("codeinstance");
        codemodule = bundle.getString("codemodule");
        new Task().execute();
        return view;
    }

    private class Task extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences prefs = getActivity().getPreferences(0);
            String token = prefs.getString("token", "empty");

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/module?token=" + token
                            + "&scolaryear=" + scolaryear
                            + "&codemodule=" + codemodule
                            + "&codeinstance=" + codeinstance)
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
            JsonObject gObj = new Gson().fromJson(json, JsonObject.class);
            progressDialog.dismiss();
            singleModuleTitle.setText(gObj.get("title").toString()
                    .replace("\\n", "\n").replace("\"", ""));
            singleModuleDescription.setText(gObj.get("description").toString()
                    .replace("\\n", "\n").replace("\"", ""));
            singleModuleGrade.setText(gObj.get("student_grade").toString().replace("\\n", "\n").replace("\"", ""));
            singleModuleCredits.setText(gObj.get("credits").toString().replace("\\n", "\n").replace("\"", ""));
        }
    }
}
