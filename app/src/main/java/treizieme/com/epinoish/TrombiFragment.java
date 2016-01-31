package treizieme.com.epinoish;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Quach on 27/01/16.
 */
public class TrombiFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    private ProgressDialog progressDialog;
    GridView gridView;
    Spinner spinnerLocation;
    Spinner spinnerYear;
    String location;
    String year;
    String loginSearch;
    View view;
    List<Trombi> trombi = new ArrayList<>();
    TrombiAdapter adapter = null;

    public TrombiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trombi, container, false);
        ((MainActivity) getActivity()).setToolbarTitle("Trombi");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        gridView = (GridView) view.findViewById(R.id.trombi_grid);
        if (getActivity() == null) {
            return view;
        }
        adapter = new TrombiAdapter(getActivity(), trombi);
        spinnerLocation = (Spinner) view.findViewById(R.id.trombi_location_spinner);
        spinnerYear = (Spinner) view.findViewById(R.id.trombi_year_spinner);

        ArrayAdapter<CharSequence> spinnerLocationAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.trombi_location, android.R.layout.simple_spinner_item);
        spinnerLocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(spinnerLocationAdapter);

        ArrayAdapter<CharSequence> spinnerYearAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.trombi_year, android.R.layout.simple_spinner_item);
        spinnerYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(spinnerYearAdapter);

        Button btnSearch = (Button) view.findViewById(R.id.trombi_btn);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText login = (EditText) view.findViewById(R.id.trombi_search);
                view.findViewById(R.id.trombi_user_not_found).setVisibility(View.GONE);

                if (login.getText() != null && !login.getText().toString().equals("")) {
                    loginSearch = login.getText().toString().toLowerCase();

                    view.findViewById(R.id.trombi_user).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.trombi_grid_include).setVisibility(View.GONE);
                    new TaskUser().execute();

                } else {

                    year = spinnerYear.getSelectedItem().toString();
                    String[] split = spinnerLocation.getSelectedItem().toString().split("/");
                    location = split[split.length - 1];

                    view.findViewById(R.id.trombi_user).setVisibility(View.GONE);
                    view.findViewById(R.id.trombi_grid_include).setVisibility(View.VISIBLE);
                    new Task().execute();
                }
            }
        });

        if (UserData.getInstance().getToken() != null) {
            year = spinnerYear.getSelectedItem().toString();
            String[] split = spinnerLocation.getSelectedItem().toString().split("/");
            location = split[split.length - 1];
            new Task().execute();
        } else {
            progressDialog.dismiss();
        }
        return view;
    }

    private class TaskUser extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String token = UserData.getInstance().getToken();

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/user?token=" + token +
                            "&user=" + loginSearch)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String res = response.body().string();
                JsonObject obj = new JsonParser().parse(res).getAsJsonObject();

                if (response.code() == 200 && obj.get("error") == null) {
                    return res;
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.findViewById(R.id.trombi_user).setVisibility(View.GONE);
                            view.findViewById(R.id.trombi_user_not_found).setVisibility(View.VISIBLE);

                            TextView text = (TextView) view.findViewById(R.id.trombi_user_not_found);

                            text.setText("User: " + loginSearch + " not found");
                        }
                    });
                }
                return null;
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
                    Type listType = new TypeToken<User>() {
                    }.getType();
                    User user = new Gson().fromJson(json, listType);
                    TextView userText = (TextView) view.findViewById(R.id.trombi_user_text);

                    String infos = user.getFullInfos();
                    userText.setText(infos);

                    if (user.getPicture() != null && !user.getPicture().contains("null.")) {
                        new DownloadImageTask((ImageView) view.findViewById(R.id.trombi_user_img))
                                .execute(user.getPicture());
                    }

                    progressDialog.dismiss();

                } catch (JsonParseException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap bm = null;

            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                bm = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bm;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private class Task extends AsyncTask<String, String, JsonObject> {

        JsonObject items;

        @Override
        protected JsonObject doInBackground(String... params) {
            String token = UserData.getInstance().getToken();

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/trombi?token=" + token +
                            "&year=" + year + "&location=FR/" + location)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    items = new JsonParser().parse(response.body().string()).getAsJsonObject();
                    return items;
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
                try {
                    Type listType = new TypeToken<List<Trombi>>() {
                    }.getType();
                    trombi = new Gson().fromJson(json.get("items"), listType);
                    if (getActivity() == null) {
                        return;
                    }
                    adapter = new TrombiAdapter(getActivity(), trombi);
                    gridView.setAdapter(adapter);
                    progressDialog.dismiss();
                } catch (JsonParseException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
