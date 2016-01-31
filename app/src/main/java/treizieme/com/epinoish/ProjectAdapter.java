package treizieme.com.epinoish;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends BaseAdapter implements Filterable {
    private List<Project> mListProject;
    private List<Project> mListProjectFiltered;
    private Context mContext;
    private LayoutInflater mInflater;
    private Boolean itemTarget = false;

    public ProjectAdapter(Context context, List<Project> aListProject) {
        mContext = context;
        mListProject = aListProject;
        mListProjectFiltered = aListProject;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListProjectFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return mListProjectFiltered.get(position);
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

        project_acti_title.setText(mListProjectFiltered.get(position).getActi_title());
        project_end_acti.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        project_end_acti.setText(mListProjectFiltered.get(position).getEnd_acti());
        project_begin_acti.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        project_begin_acti.setText(mListProjectFiltered.get(position).getBegin_acti());
        return layoutItem;
    }

    public void setItemTarget(Boolean item) {
        itemTarget = item;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    results.values = mListProject;
                    results.count = mListProject.size();
                } else {
                    ArrayList<Project> filterResultsData = new ArrayList<>();
                    if (!itemTarget) {
                        for (Project item : mListProject) {
                            if (item.getRegistered() == Integer.parseInt(constraint.toString())) {
                                filterResultsData.add(item);
                            }
                        }
                    } else {
                        for (Project item : mListProject) {
                            if (item.getActi_title().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                filterResultsData.add(item);
                            }
                        }
                    }
                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                return results;            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListProjectFiltered = (List<Project>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
