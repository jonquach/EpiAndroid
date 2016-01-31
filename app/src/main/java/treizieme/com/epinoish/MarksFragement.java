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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarksFragement extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    private ProgressDialog progressDialog;
    ListView listMarks;
    Spinner spinner;
    EditText searchBar;
    ArrayList<Marks> marks = new ArrayList<>();
    MarksAdapter adapter = null;

    public MarksFragement() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marks, container, false);
        ((MainActivity) getActivity()).setToolbarTitle("Marks");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        searchBar = (EditText) view.findViewById(R.id.marks_search);
        listMarks = (ListView) view.findViewById(R.id.list_marks);
        if (getActivity() == null) {
            progressDialog.dismiss();
            return view;
        }
        adapter = new MarksAdapter(getActivity(), marks);

        if (UserData.getInstance().getToken() != null) {
            new Task().execute();
        } else {
            progressDialog.dismiss();
        }

        return view;
    }

    private class Task extends AsyncTask<String, String, JsonObject> {

        JsonObject jsonModules;

        @Override
        protected JsonObject doInBackground(String... params) {
            String token = UserData.getInstance().getToken();

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/marks?token=" + token)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.code() == 200) {
                    jsonModules = new JsonParser().parse(response.body().string()).getAsJsonObject();
                    return jsonModules;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JsonObject json) {
            super.onPostExecute(json);

            if (json != null) {
                try {
                    Type listType = new TypeToken<List<Marks>>() {
                    }.getType();
                    marks = new Gson().fromJson(json.get("notes"), listType);
                    Collections.reverse(marks);
                    if (getActivity() == null) {
                        return;
                    }
                    adapter = new MarksAdapter(getActivity(), marks);
                    listMarks.setAdapter(adapter);
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
                } catch (JsonParseException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

}
