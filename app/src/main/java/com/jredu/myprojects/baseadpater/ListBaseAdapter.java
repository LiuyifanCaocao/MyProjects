package com.jredu.myprojects.baseadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jredu.myprojects.R;
import com.jredu.myprojects.entity.AppleNews;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class ListBaseAdapter extends BaseAdapter {
    Context context;
    List<AppleNews> list;

    public ListBaseAdapter(Context context, List<AppleNews> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_arrays_list_run_circle, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.run_circle_text1);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.run_circle_img1);
            viewHolder.content = (TextView) convertView.findViewById(R.id.run_circle_text2);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.img.setImageBitmap(list.get(position).getBitmap());
        viewHolder.content.setText(list.get(position).getDescription());

        return convertView;
    }
    public class ViewHolder {
        TextView title;
        TextView content;
        ImageView img;

    }
}
