package com.app.heads.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.heads.R;

import java.util.ArrayList;

/**
 * Created by uzair qureshi on 7/29/2018.
 */

public class BaseAdap_esources extends BaseAdapter{

 ArrayList<String> url=new ArrayList<>();
    Context mcontext;
 public BaseAdap_esources(Context context, ArrayList<String> urls){
     this.mcontext=context;
     this.url=urls;
 }

    @Override
    public int getCount() {
        return url.size();
    }

    @Override
    public Object getItem(int i) {
        return url.get(i);
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

            convertView = mInflater.inflate(R.layout.resources_child , null);
            viewHolder = new ViewHolder();

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public class ViewHolder{

     public TextView textView;

    }
}
