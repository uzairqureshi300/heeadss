package com.app.heads.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.heads.Models.RegionsModel;
import com.app.heads.R;

import java.util.ArrayList;

/**
 * Created by uzair qureshi on 7/29/2018.
 */

public class Spinner_Adapter extends BaseAdapter {

    ArrayList<RegionsModel> regionsModels = new ArrayList<>();
    Context mcontext;

    public Spinner_Adapter(Context context, ArrayList<RegionsModel> regionsModels) {
        this.mcontext = context;
        this.regionsModels = regionsModels;
    }

    @Override
    public int getCount() {
        return regionsModels.size();
    }

    @Override
    public Object getItem(int i) {
        return regionsModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.spinner_child, null);

            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(regionsModels.get(position).getName());
        return convertView;
    }

    public void setList(ArrayList<RegionsModel> list) {
    this.regionsModels=list;
    notifyDataSetChanged();
    }

    public class ViewHolder {

        public TextView textView;

    }
}
