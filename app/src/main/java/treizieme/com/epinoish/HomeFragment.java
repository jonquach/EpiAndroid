package treizieme.com.epinoish;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Quach on 27/01/16.
 */
public class HomeFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    private ProgressDialog progressDialog;
    ListView msgList;
    ArrayList<Message> msg = new ArrayList<>();
    MessageAdapter adapter = null;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).setToolbarTitle("Home");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        msgList = (ListView) view.findViewById(R.id.message_list);
        adapter = new MessageAdapter(getActivity(), msg);

        if (UserData.getInstance().getToken() != null) {
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
                    .url("http://epitech-api.herokuapp.com/messages?token=" + token)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    return response.body().string();
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
                Type listType = new TypeToken<List<Message>>() {}.getType();
                msg = new Gson().fromJson(json, listType);
                adapter = new MessageAdapter(getActivity(), msg);
                msgList.setAdapter(adapter);
                progressDialog.dismiss();
            }
        }
    }
}
