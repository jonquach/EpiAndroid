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

import java.util.ArrayList;
import java.util.List;

public class AllModulesAdapter extends BaseAdapter implements Filterable {

    private List<AllModules> mListAllModules;
    private List<AllModules> mListAllModulesFiltered;
    private Context mContext;
    private LayoutInflater mInflater;

    public AllModulesAdapter(Context context, List<AllModules> aListAllModules) {
        mContext = context;
        mListAllModules = aListAllModules;
        mListAllModulesFiltered = aListAllModules;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListAllModulesFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return mListAllModulesFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;

        if (convertView == null) {
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.item_all_modules, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView module_title = (TextView)layoutItem.findViewById(R.id.all_modules_title);
        TextView module_status = (TextView)layoutItem.findViewById(R.id.all_modules_status);
        TextView module_registered = (TextView)layoutItem.findViewById(R.id.all_modules_registered);

        module_title.setText(mListAllModulesFiltered.get(position).getTitle());
        module_status.setText(mListAllModulesFiltered.get(position).getStatus());
        module_registered.setText(mListAllModulesFiltered.get(position).getEnd_register());
        return layoutItem;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    results.values = mListAllModules;
                    results.count = mListAllModules.size();
                } else {
                    ArrayList<AllModules> filterResultsData = new ArrayList<>();
                    for (AllModules item : mListAllModules) {
                        if (item.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filterResultsData.add(item);
                        }
                    }
                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListAllModulesFiltered = (List<AllModules>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
