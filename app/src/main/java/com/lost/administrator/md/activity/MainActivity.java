package com.lost.administrator.md.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioGroup;


import com.lost.administrator.md.R;
import com.lost.administrator.md.adapter.FragmentPagerAdapter;
import com.lost.administrator.md.app.MyAplication;
import com.lost.administrator.md.fragment.HomeFragment;
import com.lost.administrator.md.fragment.HomeUserFragment;
import com.lost.administrator.md.fragment.MineFragment;
import com.lost.administrator.md.fragment.OrderFragment;
import com.lost.administrator.md.utils.SharedPrefsUtils;
import com.lost.administrator.md.widget.MyRadioButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "MainActivity";
    private RadioGroup radioGroup;
    private HomeFragment tab1=new HomeFragment();
    private OrderFragment tab2=new OrderFragment();
    private MineFragment tab3=new MineFragment();
    private HomeUserFragment tab4=new HomeUserFragment();
    private List<Fragment> fragments=new ArrayList<Fragment>();
    private MyRadioButton rb_home;
    private FragmentPagerAdapter adapter;
    private ViewPager viewPager;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyAplication.addActivity(this);
        type= SharedPrefsUtils.getString(MainActivity.this,"type");
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rb_home = (MyRadioButton) findViewById(R.id.rb_home);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        //动态获取权限
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
        radioGroup.check(R.id.rb_home);

        if (type.equals("用户")){
            fragments.add(tab1);
            fragments.add(tab2);
            fragments.add(tab3);
        }else {
            fragments.add(tab4);
            fragments.add(tab2);
            fragments.add(tab3);
        }




        //tab栏适配
        adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        viewPager.setOffscreenPageLimit(2);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected: "+position);
        switch (position){
            case 0:
                //首页
                radioGroup.check(R.id.rb_home);
                break;
            case 1:
                //收藏
                radioGroup.check(R.id.rb_order);
                break;
            case 2:
                //我的
                radioGroup.check(R.id.rb_mine);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        Log.d(TAG, "onCheckedChanged: "+i);
        switch (i){
            //首页
            case R.id.rb_home:
                viewPager.setCurrentItem(0);
                break;
            //收藏
            case R.id.rb_order:
                viewPager.setCurrentItem(1);
                break;
            //我的
            case R.id.rb_mine:
                viewPager.setCurrentItem(2);
                break;
        }


    }

}
