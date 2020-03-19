package com.bawei.lishuyue;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private DrawerLayout drawerLayout;
    private ListView listView;
    private ImageView imageView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    List<Mylist> mylists=new ArrayList<>();
    List<String> titlelist=new ArrayList<>();
    List<Fragment> fragments=new ArrayList<>();
    @Override
    protected void initData() {
        titlelist.add("Android");
        titlelist.add("IOS");
        titlelist.add("前端");
        titlelist.add("休息视频");
        titlelist.add("拓展资源");
        fragments.add(new HomeFragment());
        fragments.add(new OtherFragment());
        fragments.add(new OtherFragment());
        fragments.add(new OtherFragment());
        fragments.add(new OtherFragment());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titlelist.get(position);
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initView() {
        drawerLayout=findViewById(R.id.dra);
        imageView=findViewById(R.id.img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,100);
            }
        });
        listView=findViewById(R.id.list);
        mylists.add(new Mylist(R.mipmap.nav_gank,"新闻热点"));
        mylists.add(new Mylist(R.mipmap.nav_image,"每日看图"));
        mylists.add(new Mylist(R.mipmap.nav_news,"干货"));
        mylists.add(new Mylist(R.mipmap.nav_zhimei,"妹纸"));
        MylistAdapter mylistAdapter=new MylistAdapter(mylists,this);
        listView.setAdapter(mylistAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers();
            }
        });
        tabLayout=findViewById(R.id.table);
        viewPager=findViewById(R.id.viewp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri=data.getData();
        imageView.setImageURI(uri);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }
}
