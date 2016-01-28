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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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
public class ModuleFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    ListView listModules;
    Spinner spinner;
    private ProgressDialog progressDialog;
    ArrayList<Module> modules = new ArrayList<>();
    ModuleAdapter adapter = null;

    public ModuleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module, container, false);
        ((MainActivity) getActivity()).setToolbarTitle("Modules");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        listModules = (ListView) view.findViewById(R.id.list_modules);
        listModules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Module clicked = (Module) listModules.getItemAtPosition(position);
                ((MainActivity) getActivity()).loadSingleModuleFragment((new Integer(clicked.getScolaryear())).toString(),
                        clicked.getCodemodule(),
                        clicked.getCodeinstance());
            }
        });
        adapter = new ModuleAdapter(getActivity(), modules);
        spinner = (Spinner) view.findViewById(R.id.module_semester_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                                                                    R.array.module_semester_spinner_array,
                                                                    android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        new Task().execute();
        return view;
    }

    private class Task extends AsyncTask<String, String, JsonObject> {

        JsonObject jsonModules;

        @Override
        protected JsonObject doInBackground(String... params) {
            SharedPreferences prefs = getActivity().getPreferences(0);
            String token = prefs.getString("token", "empty");

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/modules?token=" + token)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                jsonModules = new JsonParser().parse(response.body().string()).getAsJsonObject();
                return jsonModules;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JsonObject json) {
            super.onPostExecute(json);
            Type listType = new TypeToken<List<Module>>() {}.getType();
            modules = new Gson().fromJson(json.get("modules"), listType);
            adapter = new ModuleAdapter(getActivity(), modules);
            listModules.setAdapter(adapter);
            progressDialog.dismiss();
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    adapter.getFilter().filter(spinner.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
}