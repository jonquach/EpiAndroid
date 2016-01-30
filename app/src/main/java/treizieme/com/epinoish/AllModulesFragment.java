package treizieme.com.epinoish;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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
public class AllModulesFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    private ProgressDialog progressDialog;
    EditText searchBar;
    ArrayList<AllModules> modules = new ArrayList<>();
    AllModulesAdapter adapter;
    ListView listModules;

    public AllModulesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_modules, container, false);
        ((MainActivity) getActivity()).setToolbarTitle("All Modules");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        searchBar = (EditText) view.findViewById(R.id.all_modules_search);
        listModules = (ListView) view.findViewById(R.id.list_all_modules);
        listModules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AllModules clicked = (AllModules) listModules.getItemAtPosition(position);
                ((MainActivity) getActivity()).loadSingleModuleFragment((new Integer(clicked.getScolaryear())).toString(),
                        clicked.getCode(),
                        clicked.getCodeinstance());
            }
        });
        adapter = new AllModulesAdapter(getActivity(), modules);
        new Task().execute();
        return view;
    }

    private class Task extends AsyncTask<String, String, JsonObject> {

        JsonObject jsonModules;

        @Override
        protected JsonObject doInBackground(String... params) {
            String token = UserData.getInstance().getToken();

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/allmodules?token=" + token
                    + "&scolaryear=2015"
                    + "&location=FR/PAR&course=bachelor/classic")
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    jsonModules = new JsonParser().parse(response.body().string()).getAsJsonObject();
                    return jsonModules;
                }
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JsonObject json) {
            super.onPostExecute(json);
            if (json != null) {
                Type listType = new TypeToken<List<AllModules>>() {}.getType();
                modules = new Gson().fromJson(json.get("items"), listType);
                adapter = new AllModulesAdapter(getActivity(), modules);
                listModules.setAdapter(adapter);
                progressDialog.dismiss();
                searchBar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        adapter.getFilter().filter(s.toString());
                    }
                });
            }
        }
    }

}
