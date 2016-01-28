package treizieme.com.epinoish;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Quach on 28/01/16.
 */
public class DrawerInfos extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    private DrawerLayout drawer;

    public DrawerInfos(DrawerLayout viewById) {
        drawer = viewById;
    }

    public void setDrawerInfos() {
        new Task().execute();
    }

    private class Task extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            UserData userData = UserData.getInstance();
            String login = userData.getLogin();
            String token = userData.getToken();

            Request request = new Request.Builder()
                    .url("http://epitech-api.herokuapp.com/user?user=" + login + "&token=" + token)
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

            Type listType = new TypeToken<User>() {}.getType();
            User user = new Gson().fromJson(json, listType);

            TextView username = (TextView) drawer.findViewById(R.id.user_name);
            TextView userInfo = (TextView) drawer.findViewById(R.id.user_info);

            username.setText(user.getFullName());
            String infos = user.getInternal_email() + "\n" +
                    "GPA: " + user.getGpa().get(0).get("gpa") + "\n" +
                    "Credits: " + user.getCredits().toString() + "\n" +
                    "Spices: " + user.getSpice().get("available_spice") + "\n";

            if (user.getNsstat() != null) {
                infos += "Log time: " + user.getNsstat().get("active").toString() + "h" + "\n";
            } else {
                infos += "Log time: 0h\n";
            }

            userInfo.setText(infos);

            if (!user.getPicture().contains("null.")) {
                new DownloadImageTask((ImageView) drawer.findViewById(R.id.user_picture))
                        .execute(user.getPicture());
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
}
