package com.jredu.myprojects.baseadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jredu.myprojects.R;
import com.jredu.myprojects.entity.MyMsg;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class MyMsgAdapter extends BaseAdapter {
    Context context;
    List<MyMsg> list;

    public MyMsgAdapter(Context context, List<MyMsg> list) {
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.mymsgitemlayout, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.mymsg_title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.mymsg_content);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.mymsg_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.content.setText(list.get(position).getContent());
        viewHolder.img.setImageResource(list.get(position).getImg());
        return convertView;
    }

    public class ViewHolder {
        ImageView img;
        TextView title;
        TextView content;
    }

}
