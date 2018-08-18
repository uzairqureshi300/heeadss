package com.app.heads.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.heads.Models.DomainModel;
import com.app.heads.R;

import java.util.ArrayList;

/**
 * Created by uzair qureshi on 7/29/2018.
 */

public class DomainBaseAdapter extends BaseAdapter{

 ArrayList<DomainModel> domainModels =new ArrayList<>();
    Context mcontext;
 public DomainBaseAdapter(Context context, ArrayList<DomainModel> domainModels){
     this.mcontext=context;
     this.domainModels =domainModels;
 }

    @Override
    public int getCount() {
        return domainModels.size();
    }

    @Override
    public Object getItem(int i) {
        return domainModels.get(i);
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

            convertView = mInflater.inflate(R.layout.section_child , null);
            viewHolder = new ViewHolder();
            viewHolder.textView=convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(domainModels.get(position).getName());
        return convertView;
    }
    public void setList(ArrayList<DomainModel> list) {
        this.domainModels=list;
        notifyDataSetChanged();
    }
    public class ViewHolder{

     public TextView textView;

    }
}
