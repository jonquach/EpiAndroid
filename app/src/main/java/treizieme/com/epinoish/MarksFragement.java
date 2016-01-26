package treizieme.com.epinoish;

import android.content.Context;
import android.net.Uri;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class MarksFragement extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    ListView listMarks;
    Spinner spinner;
    ArrayList<Marks> marks = new ArrayList<>();
    MarksAdapter adapter = null;

    public MarksFragement() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marks, container, false);
        listMarks = (ListView) view.findViewById(R.id.list_marks);

        adapter = new MarksAdapter(getActivity(), marks);
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
                    .url("http://epitech-api.herokuapp.com/marks?token=" + token)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                jsonModules = new JsonParser().parse(response.body().string()).getAsJsonObject();
                System.out.println(jsonModules);
                return jsonModules;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JsonObject json) {
            super.onPostExecute(json);
            Type listType = new TypeToken<List<Marks>>() {}.getType();
            marks = new Gson().fromJson(json.get("notes"), listType);
            adapter = new MarksAdapter(getActivity(), marks);
            listMarks.setAdapter(adapter);

        }
    }

}
