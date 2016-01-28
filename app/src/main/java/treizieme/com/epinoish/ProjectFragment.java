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
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
public class ProjectFragment extends Fragment {


    private final OkHttpClient client = new OkHttpClient();
    ListView listProjects;
    private ProgressDialog progressDialog;
    ArrayList<Project> projects = new ArrayList<>();
    ProjectAdapter adapter = null;


    public ProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        ((MainActivity) getActivity()).setToolbarTitle("Projects");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        listProjects = (ListView) view.findViewById(R.id.list_projects);
        listProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project clicked = (Project) listProjects.getItemAtPosition(position);
                ((MainActivity) getActivity()).loadSingleProjectFragment(clicked.getScolaryear(),
                        clicked.getCodemodule(),
                        clicked.getCodeinstance(),
                        clicked.getCodeacti());
            }
        });
        new Task().execute();
        return view;
    }

    private class Task extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences prefs = getActivity().getPreferences(0);
            String token = prefs.getString("token", "empty");

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/projects?token=" + token)
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
            Type listType = new TypeToken<List<Project>>() {}.getType();
            projects = new Gson().fromJson(json, listType);
            progressDialog.dismiss();
            adapter = new ProjectAdapter(getActivity(), projects);
            listProjects.setAdapter(adapter);
        }
    }
}
