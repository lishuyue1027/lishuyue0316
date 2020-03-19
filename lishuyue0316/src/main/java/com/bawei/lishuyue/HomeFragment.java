package com.bawei.lishuyue;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment{
    private PullToRefreshListView pullToRefreshListView;
    List<MyBase.ResultBean> list=new ArrayList<>();
    int page=1;
    @Override
    protected void initView(View view) {
        pullToRefreshListView=view.findViewById(R.id.pull);
        pullToRefreshListView.setMode(PullToRefreshListView.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list.clear();
                page=1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getData();
            }
        });
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url=list.get(position).getUrl();
                Intent intent=new Intent(getActivity(),NetActivity.class);
                intent.putExtra("key",url);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        if(NetUtil.getInstance().hasNet(getActivity())){
            String http="http://47.94.132.125/baweiapi/gank_android?pageSize=5&page="+page;
            NetUtil.getInstance().doGet(http, new NetUtil.MyCallback() {
                @Override
                public void onDoGetSuccess(String json) {
                    MyBase myBase=new Gson().fromJson(json,MyBase.class);
                    list.addAll(myBase.getResult());
                    MyAdapter myAdapter=new MyAdapter(list);
                    pullToRefreshListView.setAdapter(myAdapter);
                    pullToRefreshListView.onRefreshComplete();
                }

                @Override
                public void onDoGetError() {

                }
            });
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        getData();
    }
}
