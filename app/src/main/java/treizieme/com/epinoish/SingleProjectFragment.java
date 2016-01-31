package treizieme.com.epinoish;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    ArrayList<ProjectFiles> files = new ArrayList<>();
    TextView singleProjectTitle = null;
    TextView singleProjectDescription = null;
    TextView singleProjectFiles = null;

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
        singleProjectFiles = (TextView) view.findViewById(R.id.single_project_files);
        singleProjectDescription.setMovementMethod(new ScrollingMovementMethod());
        Bundle bundle = this.getArguments();
        scolaryear = bundle.getString("scolaryear");
        codeinstance = bundle.getString("codeinstance");
        codemodule = bundle.getString("codemodule");
        codeacti = bundle.getString("codeacti");

        if (UserData.getInstance().getToken() != null) {
            new Task().execute();

            if (scolaryear != null && codeacti != null && codeinstance != null
                    && codemodule != null) {
                new TaskFile().execute();
            }

        } else {
            progressDialog.dismiss();
        }

        return view;
    }

    private class TaskFile extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String token = UserData.getInstance().getToken();

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/project/files?token=" + token
                            + "&scolaryear=" + scolaryear
                            + "&codemodule=" + codemodule
                            + "&codeinstance=" + codeinstance
                            + "&codeacti=" + codeacti)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                String res = response.body().string();

                if (response.code() == 200 && res.charAt(0) == '[') {
                    return res;
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
                    Type listType = new TypeToken<List<ProjectFiles>>() {
                    }.getType();
                    System.out.println(json);
                    files = new Gson().fromJson(json, listType);
                    progressDialog.dismiss();

                    String projectFiles = "Files :\n";

                    for (ProjectFiles item : files) {
                        projectFiles += "\t" + item.getTitle() + "\n";
                    }

                    singleProjectFiles.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue));
                    singleProjectFiles.setText(projectFiles);

                } catch (JsonParseException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
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
                try {
                    JsonObject gObj = new Gson().fromJson(json, JsonObject.class);
                    progressDialog.dismiss();
                    singleProjectTitle.setText(gObj.get("project_title").toString()
                            .replace("\\n", "\n").replace("\"", ""));
                    singleProjectDescription.setText(gObj.get("description").toString()
                            .replace("\\n", "\n").replace("\"", ""));
                } catch (JsonParseException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
