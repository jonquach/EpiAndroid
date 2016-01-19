package treizieme.com.epinoish;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ModuleFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    ListView listModules;
    ArrayList<Module> modules = new ArrayList<>();
    ModuleAdapter adapter = null;

    public ModuleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module, container, false);
        listModules = (ListView) view.findViewById(R.id.list_modules);
        new Task().execute();
        return view;
    }

    private class Task extends AsyncTask<String, String, JSONObject> {

        JSONObject jsonModules;

        @Override
        protected JSONObject doInBackground(String... params) {
            SharedPreferences prefs = getActivity().getPreferences(0);
            String token = prefs.getString("token", "empty");

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/modules?token=" + token)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                jsonModules = new JSONObject(response.body().string());
                return jsonModules;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            super.onPostExecute(json);
                try {
                    JSONArray modulesJSON = json.getJSONArray("modules");
                    for(int i=0; i<modulesJSON.length(); i++) {
                        JSONObject json_data = modulesJSON.getJSONObject(i);
                        Module item = new Module();
                        item.setScolarYear(json_data.getInt("scolaryear"));
                        item.setIdUserHistory(json_data.getString("id_user_history"));
                        item.setCodeModule(json_data.getString("codemodule"));
                        item.setCodeInstance(json_data.getString("codeinstance"));
                        item.setTitle(json_data.getString("title"));
                        item.setIdInstance(json_data.getInt("id_instance"));
                        item.setDateIns(json_data.getString("date_ins"));
                        item.setCycle(json_data.getString("cycle"));
                        item.setGrade(json_data.getString("grade"));
                        item.setFlags(json_data.getString("flags"));
                        item.setCredits(json_data.getInt("credits"));
                        item.setBarrage(json_data.getInt("barrage"));
                        item.setInstanceId(json_data.getInt("instance_id"));
                        item.setModuleRating(json_data.getString("module_rating"));
                        item.setSemester(json_data.getString("semester"));
                        modules.add(item);
                    }
                    adapter = new ModuleAdapter(getActivity(), modules);
                    listModules.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
}