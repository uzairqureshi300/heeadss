package com.app.heads.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.heads.Models.QuestionsModel;
import com.app.heads.R;

import java.util.ArrayList;

/**
 * Created by uzair qureshi on 7/29/2018.
 */

public class BaseAdap_questions extends BaseAdapter{

 ArrayList<QuestionsModel> questionsModels =new ArrayList<>();
    Context mcontext;
 public BaseAdap_questions(Context context, ArrayList<QuestionsModel> urls){
     this.mcontext=context;
     this.questionsModels =urls;
 }

    @Override
    public int getCount() {
        return questionsModels.size();
    }

    @Override
    public Object getItem(int i) {
        return questionsModels.get(i);
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

            convertView = mInflater.inflate(R.layout.patientquestions_adapter_view , null);
            viewHolder = new ViewHolder();
            viewHolder.textView_questions_no =convertView.findViewById(R.id.question_no);
            viewHolder.textView_question=convertView.findViewById(R.id.question);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int pos=position+1;
        viewHolder.textView_questions_no.setText("Question no. "+ pos);
        viewHolder.textView_question.setText(questionsModels.get(position).getQuestion());
        return convertView;
    }
    public void setList(ArrayList<QuestionsModel> list) {
        this.questionsModels=list;
        notifyDataSetChanged();
    }
    public class ViewHolder{

     public TextView textView_questions_no,textView_question;

    }
}
