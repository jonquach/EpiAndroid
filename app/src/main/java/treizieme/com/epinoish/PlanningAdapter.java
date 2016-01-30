package treizieme.com.epinoish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlanningAdapter extends BaseAdapter implements Filterable {
    private List<Planning> mListPlanning;
    private List<Planning> mListPlanningFiltered;
    private Context mContext;
    private LayoutInflater mInflater;
    private Boolean filteredItem;

    public PlanningAdapter(Context context, List<Planning> aListPlanning) {
        mContext = context;
        mListPlanning = aListPlanning;
        mListPlanningFiltered = aListPlanning;
        mInflater = LayoutInflater.from(mContext);
        filteredItem = false;
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
        TextView planning_token = (TextView)layoutItem.findViewById(R.id.planning_token);

        Planning current = mListPlanningFiltered.get(position);

        if (current.getEvent_registered() != null
                && current.getEvent_registered().equals("registered")
                && !current.getModule_registered().equals(false)
        //        && mListPlanningFiltered.get(position).getAllow_token()
                ) {
            planning_token.setVisibility(View.VISIBLE);
        }
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

    public void setFilteredItem(Boolean item) {
        filteredItem = item;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected Filter.FilterResults performFiltering(CharSequence constraint) {
                Filter.FilterResults results = new Filter.FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    results.values = mListPlanning;
                    results.count = mListPlanning.size();
                } else {
                    ArrayList<Planning> filterResultsData = new ArrayList<>();
                    for (Planning item : mListPlanning) {
                        if (!filteredItem) {
                            if (item.getSemester().toString().equals(constraint)) {
                                filterResultsData.add(item);
                            }
                        } else {
                            if (item.getActi_title().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                filterResultsData.add(item);
                            }
                        }
                    }
                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                mListPlanningFiltered = (List<Planning>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
