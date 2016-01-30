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

public class ModuleAdapter extends BaseAdapter implements Filterable{

    private List<Module> mListModule;
    private List<Module> mListModuleFiltered;
    private Context mContext;
    private LayoutInflater mInflater;

    public ModuleAdapter(Context context, List<Module> aListModule) {
        mContext = context;
        mListModule = aListModule;
        mListModuleFiltered = aListModule;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListModuleFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return mListModuleFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;

        if (convertView == null) {
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.item_module, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView module_title = (TextView)layoutItem.findViewById(R.id.module_title);
        TextView module_grade = (TextView)layoutItem.findViewById(R.id.module_grade);
        TextView module_semester = (TextView)layoutItem.findViewById(R.id.module_semester);

        module_title.setText(mListModuleFiltered.get(position).getTitle());
        module_grade.setText(mListModuleFiltered.get(position).getGrade());
        module_semester.setText(mListModuleFiltered.get(position).getSemester());
        return layoutItem;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    results.values = mListModule;
                    results.count = mListModule.size();
                } else {
                    ArrayList<Module> filterResultsData = new ArrayList<>();
                    for (Module item : mListModule) {
                        if (item.getSemester().equals(constraint)) {
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
                mListModuleFiltered = (List<Module>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
