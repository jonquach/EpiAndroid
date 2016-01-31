package treizieme.com.epinoish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
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

/**
 * Created by Quach on 27/01/16.
 */
public class MessageAdapter extends BaseAdapter {

    private List<Message> _messageList;
    private Context _context;
    private LayoutInflater _inflater;

    public MessageAdapter(Context context, List<Message> messageList) {
        _context = context;
        _messageList = messageList;
        _inflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return _messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return _messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;

        if (convertView == null) {
            layoutItem = (LinearLayout) _inflater.inflate(R.layout.item_message, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView messageTitle = (TextView) layoutItem.findViewById(R.id.message_title);
        TextView messageContent = (TextView) layoutItem.findViewById(R.id.message_content);
        TextView messageDate = (TextView) layoutItem.findViewById(R.id.message_date);
        ImageView userImg = (ImageView) layoutItem.findViewById(R.id.message_user);

        messageTitle.setText(Html.fromHtml(_messageList.get(position).getTitle()));
        messageContent.setText(Html.fromHtml(_messageList.get(position).getContent()));
        messageDate.setText(_messageList.get(position).getDate());
        if (_messageList.get(position).getUser().get("picture") != null) {
            new DownloadImageTask((ImageView) layoutItem.findViewById(R.id.message_user))
                    .execute(_messageList.get(position).getUser().get("picture"));
        } else {
            userImg.setImageResource(R.drawable.nopicture);
        }

        return layoutItem;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {;
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
