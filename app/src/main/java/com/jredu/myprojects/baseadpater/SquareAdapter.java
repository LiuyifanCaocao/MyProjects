package com.jredu.myprojects.baseadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jredu.myprojects.R;
import com.jredu.myprojects.entity.Square;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class SquareAdapter extends BaseAdapter {
    Context context;
    List<Square> list;
    public SquareAdapter(Context context, List<Square> list) {
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
    public int getItemViewType(int position) {
        int i = list.get(position).getType();
        if (i == 0){
            return 0;
        }else if(i == 1){
            return 1;
        }else{
            return 2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int i = list.get(position).getType();
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        ViewHolder3 viewHolder3 = null;
        if (convertView == null || convertView.getTag() == null) {
            switch (i) {
                case 0:
                    convertView = LayoutInflater.from(context).inflate(R.layout.squareitemlayout, null);
                    viewHolder1 = new ViewHolder1();
                    viewHolder1.img = (ImageView) convertView.findViewById(R.id.square_img);
                    viewHolder1.title = (TextView) convertView.findViewById(R.id.square_title);
                    viewHolder1.content = (TextView) convertView.findViewById(R.id.square_content);
                    convertView.setTag(viewHolder1);
                    break;
                case 1:
                    convertView = LayoutInflater.from(context).inflate(R.layout.squareitem1layout, null);
                    viewHolder2 = new ViewHolder2();
                    viewHolder2.rightimg = (ImageView) convertView.findViewById(R.id.square_rightimg);
                    viewHolder2.leftimg = (ImageView) convertView.findViewById(R.id.square_leftimg);
                    viewHolder2.centertimg = (ImageView) convertView.findViewById(R.id.square_centerimg);
                    viewHolder2.title = (TextView) convertView.findViewById(R.id.square_title2);
                    convertView.setTag(viewHolder2);
                    break;
                case 2:
                    convertView = LayoutInflater.from(context).inflate(R.layout.squareitem2layout, null);
                    viewHolder3 = new ViewHolder3();
                    viewHolder3.bottomimg = (ImageView) convertView.findViewById(R.id.square_bottomimg);
                    viewHolder3.title = (TextView) convertView.findViewById(R.id.square_title3);
                    convertView.setTag(viewHolder3);
                    break;
            }
        } else {
            switch (i) {
                case 0:
                    viewHolder1 = (ViewHolder1) convertView.getTag();
                    break;
                case 1:
                    viewHolder2 = (ViewHolder2) convertView.getTag();
                    break;
                case 2:
                    viewHolder3 = (ViewHolder3) convertView.getTag();
                    break;
            }
        }
        switch (i) {
            case 0:
                viewHolder1.img.setImageResource(list.get(position).getImg());
                viewHolder1.title.setText(list.get(position).getTitle());
                viewHolder1.content.setText(list.get(position).getContent());
                break;
            case 1:
                viewHolder2.rightimg.setImageResource(list.get(position).getRightimg());
                viewHolder2.centertimg.setImageResource(list.get(position).getCenterimg());
                viewHolder2.leftimg.setImageResource(list.get(position).getLeftimg());
                viewHolder2.title.setText(list.get(position).getTitle());
                break;
            case 2:
                viewHolder3.bottomimg.setImageResource(list.get(position).getBottomimg());
                viewHolder3.title.setText(list.get(position).getTitle());
                break;
        }
        return convertView;
    }

    public class ViewHolder1 {
        ImageView img;
        TextView title;
        TextView content;
    }

    public class ViewHolder2 {
        ImageView centertimg;
        ImageView leftimg;
        ImageView rightimg;
        TextView title;
    }

    public class ViewHolder3 {
        ImageView bottomimg;
        TextView title;
    }

}
