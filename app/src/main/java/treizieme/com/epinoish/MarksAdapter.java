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

public class MarksAdapter extends BaseAdapter implements Filterable{

    private List<Marks> mListMarks;
    private List<Marks> mListMarksFiltered;
    private Context mContext;
    private LayoutInflater mInflater;

    public MarksAdapter(Context context, List<Marks> aListModule) {
        mContext = context;
        mListMarks = aListModule;
        mListMarksFiltered = aListModule;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListMarksFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return mListMarksFiltered.get(position);
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

        //module_title.setText(mListMarksFiltered.get(position).getTitle());
        //module_grade.setText(mListMarksFiltered.get(position).getGrade());
        //module_semester.setText(mListMarksFiltered.get(position).getSemester());
        return layoutItem;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    results.values = mListMarks;
                    results.values = mListMarks.size();
                } else {
                    ArrayList<Marks> filterResultsData = new ArrayList<>();
                    for (Marks item : mListMarks) {
                        //if (item.getSemester().equals(constraint)) {
                            filterResultsData.add(item);
                       //}
                    }
                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListMarksFiltered = (List<Marks>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
