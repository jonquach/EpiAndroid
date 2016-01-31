package treizieme.com.epinoish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.List;

public class ProjectFilesAdapter extends BaseAdapter {
    private List<ProjectFiles> mListProjectFiles;
    private Context mContext;
    private LayoutInflater mInflater;
    private Boolean itemTarget = false;

    public ProjectFilesAdapter(Context context, List<ProjectFiles> aListProjectFiles) {
        mContext = context;
        mListProjectFiles = aListProjectFiles;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListProjectFiles.size();
    }

    @Override
    public Object getItem(int position) {
        return mListProjectFiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;

        if (convertView == null) {
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.item_project, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

//        TextView project_acti_title = (TextView) layoutItem.findViewById(R.id.project_acti_title);
//        TextView project_end_acti = (TextView) layoutItem.findViewById(R.id.project_end_acti);
//        TextView project_begin_acti = (TextView) layoutItem.findViewById(R.id.project_begin_acti);

//        project_acti_title.setText(mListProjectFiles.get(position).getActi_title());
//        project_end_acti.setTextColor(ContextCompat.getColor(mContext, R.color.red));
//        project_end_acti.setText(mListProjectFiles.get(position).getEnd_acti());
//        project_begin_acti.setTextColor(ContextCompat.getColor(mContext, R.color.green));
//        project_begin_acti.setText(mListProjectFiles.get(position).getBegin_acti());
        return layoutItem;
    }
}
