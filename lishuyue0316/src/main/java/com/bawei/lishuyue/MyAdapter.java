package com.bawei.lishuyue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<MyBase.ResultBean> list;

    public MyAdapter(List<MyBase.ResultBean> list) {

        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
            holder.imageView=convertView.findViewById(R.id.img);
            holder.textView=convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        MyBase.ResultBean resultBean = list.get(position);
        holder.textView.setText(resultBean.getDesc());
        List<String> images = resultBean.getImages();
        if(images!=null){
            holder.imageView.setVisibility(View.VISIBLE);
            NetUtil.getInstance().doGetPhone(images.get(0),holder.imageView);
        }else {
            holder.imageView.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
