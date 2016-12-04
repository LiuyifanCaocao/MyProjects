package com.jredu.myprojects.baseadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jredu.myprojects.R;
import com.jredu.myprojects.entity.Phone;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class PhoneAdapter  extends BaseAdapter{
    Context context;
    List<Phone> list;

    public PhoneAdapter(Context context, List<Phone> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.phonelayout,null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.phoneName);
            viewHolder.content = (TextView) convertView.findViewById(R.id.phoneNumber);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getName());
        viewHolder.content.setText(list.get(position).getNumber());

        return convertView;
    }

    public class ViewHolder {
        TextView title;
        TextView content;
    }
}
