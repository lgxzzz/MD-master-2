package com.lost.administrator.md.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lost.administrator.md.R;
import com.lost.administrator.md.adapter.FragmentPagerAdapter;
import com.lost.administrator.md.entity.Store;
import com.lost.administrator.md.fragment.PingJiaFragment;
import com.lost.administrator.md.fragment.ClothingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClothingActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.iv_back_collect)
    ImageView ivBackCollect;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.rb_allOrder_order)
    RadioButton rbAllOrderOrder;

    @BindView(R.id.rb_pingJia_order)
    RadioButton rbPingJiaOrder;
    @BindView(R.id.radioGroup_order)
    RadioGroup radioGroupOrder;
    @BindView(R.id.viewpager_order)
    ViewPager viewpagerOrder;
    private ClothingFragment tab1 = new ClothingFragment();

    private PingJiaFragment tab2 = new PingJiaFragment();
    private List<Fragment> fragments = new ArrayList<Fragment>();

    private FragmentPagerAdapter adapter;
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuan_gou);
        ButterKnife.bind(this);
        store = (Store) getIntent().getSerializableExtra("DATA");
        radioGroupOrder.check(R.id.rb_home);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DATA", store);
        tab2.setArguments(bundle);
        fragments.add(tab1);
        fragments.add(tab2);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewpagerOrder.setAdapter(adapter);
        viewpagerOrder.setOnPageChangeListener(this);
        radioGroupOrder.setOnCheckedChangeListener(this);
        viewpagerOrder.setOffscreenPageLimit(1);
    }

    @OnClick(R.id.iv_back_collect)
    public void onClick() {
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroupOrder.check(R.id.rb_allOrder_order);
                break;

            case 1:
                radioGroupOrder.check(R.id.rb_pingJia_order);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_allOrder_order:
                viewpagerOrder.setCurrentItem(0);
                break;

            case R.id.rb_pingJia_order:
                viewpagerOrder.setCurrentItem(1);
                break;
        }


    }
}
