package com.bawei.lishuyue;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class MylistAdapter extends BaseAdapter {
    private List<Mylist> list;
    private Context context;

    public MylistAdapter(List<Mylist> list, Context context) {
        this.list = list;
        this.context = context;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context,R.layout.listlayout,null);
            holder.imageView=convertView.findViewById(R.id.imgv);
            holder.textView=convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.imageView.setImageResource(list.get(position).getImg());
        holder.textView.setText(list.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
