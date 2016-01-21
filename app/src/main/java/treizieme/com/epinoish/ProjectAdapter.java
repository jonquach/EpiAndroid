package treizieme.com.epinoish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ProjectAdapter extends BaseAdapter{
    private List<Project> mListProject;
    private Context mContext;
    private LayoutInflater mInflater;

    public ProjectAdapter(Context context, List<Project> aListProject) {
        mContext = context;
        mListProject = aListProject;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListProject.size();
    }

    @Override
    public Object getItem(int position) {
        return mListProject.get(position);
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

        TextView project_acti_title = (TextView)layoutItem.findViewById(R.id.project_acti_title);
        TextView project_end_acti = (TextView)layoutItem.findViewById(R.id.project_end_acti);
        TextView project_begin_acti = (TextView)layoutItem.findViewById(R.id.project_begin_acti);

        project_acti_title.setText(mListProject.get(position).getActi_title());
        project_end_acti.setText(mListProject.get(position).getEnd_acti());
        project_begin_acti.setText(mListProject.get(position).getBegin_acti());
        return layoutItem;
    }
}
