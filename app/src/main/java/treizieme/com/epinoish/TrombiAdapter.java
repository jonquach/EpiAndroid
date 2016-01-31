package treizieme.com.epinoish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class TrombiAdapter extends BaseAdapter {
    private List<Trombi> mTrombiList;
    private Context mContext;
    private LayoutInflater mInflater;

    public TrombiAdapter(Context context, List<Trombi> _trombiList) {
        mContext = context;
        mTrombiList = _trombiList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mTrombiList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTrombiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;

        if (convertView == null) {
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.item_trombi, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView trombiUsername = (TextView) layoutItem.findViewById(R.id.trombi_name);

        trombiUsername.setText(mTrombiList.get(position).getLogin());

        if (mTrombiList.get(position).getPicture() != null &&
                !mTrombiList.get(position).getPicture().contains("null.")) {
            new DownloadImageTask((ImageView) layoutItem.findViewById(R.id.trombi_img))
                    .execute(mTrombiList.get(position).getPicture());
        }

        return layoutItem;
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
