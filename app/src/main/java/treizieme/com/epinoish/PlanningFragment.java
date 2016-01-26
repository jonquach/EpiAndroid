package treizieme.com.epinoish;


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
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlanningFragment extends Fragment {
    private final OkHttpClient client = new OkHttpClient();
    ListView listEvents;
    Spinner spinner;
    List<Planning> events = new ArrayList<>();
    PlanningAdapter adapter = null;

    public PlanningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_planning, container, false);
        listEvents = (ListView) view.findViewById(R.id.list_events);
        adapter = new PlanningAdapter(getActivity(), events);
        spinner = (Spinner) view.findViewById(R.id.planning_semester_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.module_semester_spinner_array,
                android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        new Task().execute();
        return view;
    }

    private String getTimeStamp(int nbDays) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdfDate.parse(strDate));
            c.add(Calendar.DATE, nbDays);
            strDate = sdfDate.format(c.getTime());
            return strDate;
        } catch (ParseException e) {
            return null;
        }
    }

    private class Task extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences prefs = getActivity().getPreferences(0);
            String token = prefs.getString("token", "empty");

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/planning?token=" + token
                    + "&start=" + getTimeStamp(0) + "&end=" + getTimeStamp(7))
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
            Type listType = new TypeToken<List<Planning>>() {}.getType();
            events = new Gson().fromJson(json, listType);
            adapter = new PlanningAdapter(getActivity(), events);
            listEvents.setAdapter(adapter);

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
