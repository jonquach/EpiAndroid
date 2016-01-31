package treizieme.com.epinoish;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
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
public class ProjectFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    ListView listProjects;
    EditText searchBar;
    private ProgressDialog progressDialog;
    ArrayList<Project> projects = new ArrayList<>();
    ProjectAdapter adapter = null;
    private int registered;


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
        searchBar = (EditText) view.findViewById(R.id.all_projects_search);
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

        if (UserData.getInstance().getToken() != null) {
            Bundle bundle = this.getArguments();
            registered = bundle.getInt("registered");
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
                    .url("http://epitech-api.herokuapp.com/projects?token=" + token)
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
                    Type listType = new TypeToken<List<Project>>() {
                    }.getType();
                    projects = new Gson().fromJson(json, listType);
                    progressDialog.dismiss();
                    if (getActivity() == null) {
                        return;
                    }
                    adapter = new ProjectAdapter(getActivity(), projects);
                    listProjects.setAdapter(adapter);
                    if (registered == 1) {
                        adapter.setItemTarget(false);
                        adapter.getFilter().filter("1");
                    }
                    searchBar.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            adapter.setItemTarget(true);
                            adapter.getFilter().filter(s.toString());
                        }
                    });
                } catch (JsonParseException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
