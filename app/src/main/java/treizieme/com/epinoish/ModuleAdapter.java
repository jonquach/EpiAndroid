package treizieme.com.epinoish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ModuleAdapter extends BaseAdapter {

    private List<Module> mListModule;
    private Context mContext;
    private LayoutInflater mInflater;

    public ModuleAdapter(Context context, List<Module> aListModule) {
        mContext = context;
        mListModule = aListModule;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListModule.size();
    }

    @Override
    public Object getItem(int position) {
        return mListModule.get(position);
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

        module_title.setText(mListModule.get(position).getTitle());
        module_grade.setText(mListModule.get(position).getGrade());
        module_semester.setText(mListModule.get(position).getSemester());
        return layoutItem;
    }
}
