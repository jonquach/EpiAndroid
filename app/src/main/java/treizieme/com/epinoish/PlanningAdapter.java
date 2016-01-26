package treizieme.com.epinoish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class PlanningAdapter extends BaseAdapter implements Filterable {
    private List<Planning> mListPlanning;
    private List<Planning> mListPlanningFiltered;
    private Context mContext;
    private LayoutInflater mInflater;

    public PlanningAdapter(Context context, List<Planning> aListPlanning) {
        mContext = context;
        mListPlanning = aListPlanning;
        mListPlanningFiltered = aListPlanning;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListPlanningFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return mListPlanningFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;

        if (convertView == null) {
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.item_planning, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView planning_title = (TextView)layoutItem.findViewById(R.id.planning_title);
        TextView planning_date = (TextView)layoutItem.findViewById(R.id.planning_date);
        TextView planning_start = (TextView)layoutItem.findViewById(R.id.planning_start);
        TextView planning_end = (TextView)layoutItem.findViewById(R.id.planning_end);
        TextView planning_room = (TextView)layoutItem.findViewById(R.id.planning_room);

        planning_title.setText(mListPlanningFiltered.get(position).getActi_title());
        planning_date.setText(mListPlanningFiltered.get(position).getStart().split(" ")[0]);
        planning_start.setText(mListPlanningFiltered.get(position).getStart().split(" ")[1]);
        planning_end.setText(mListPlanningFiltered.get(position).getEnd().split(" ")[1]);
        if (mListPlanningFiltered.get(position).room != null)
            planning_room.setText(mListPlanningFiltered.get(position).room.get("code"));
        else
            planning_room.setText("-");
        return layoutItem;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
