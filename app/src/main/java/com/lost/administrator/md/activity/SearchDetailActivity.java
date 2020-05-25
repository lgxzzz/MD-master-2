package com.lost.administrator.md.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lost.administrator.md.R;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.CollectionBean;
import com.lost.administrator.md.entity.EventBusBean;
import com.lost.administrator.md.entity.Store;
import com.lost.administrator.md.entity.StoreDingDan;
import com.lost.administrator.md.utils.SharedPrefsUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchDetailActivity extends AppCompatActivity {

    @BindView(R.id.goodname)
    TextView goodname;
    @BindView(R.id.guige)
    TextView guige;
    @BindView(R.id.dian_price)
    TextView dianPrice;
    @BindView(R.id.tuan_price)
    TextView tuanPrice;
    @BindView(R.id.good_picture)
    ImageView goodPicture;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.ccc)
    TextView ccc;
    @BindView(R.id.tv_selected)
    TextView tvSelected;
    @BindView(R.id.bbb)
    TextView bbb;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.iv_collect1)
    ImageView ivCollect1;
    @BindView(R.id.iv_collect2)
    ImageView ivCollect2;
    @BindView(R.id.btn_take_order)
    Button btnTakeOrder;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    Store store;
private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        user = SharedPrefsUtils.getString(SearchDetailActivity.this, "user");
        store = (Store) getIntent().getSerializableExtra("DATA");
        goodname.setText(store.getName());
        guige.setText("1份/" + store.getMoney());
        dianPrice.setText("￥" + store.getMoney());
        tuanPrice.setText("￥" + store.getprice());
        Glide.with(SearchDetailActivity.this).load(store.getpicture()).into(goodPicture);
        ccc.setText("￥" + store.getprice());
        bbb.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        bbb.setText("￥" + store.getMoney());
        CollectionBean collect = DbSqliteHelper.getInstance(SearchDetailActivity.this).findColl(store.getName(), user);
        if (!TextUtils.isEmpty(collect.getName())){
            ivCollect2.setVisibility(View.VISIBLE);
            ivCollect1.setVisibility(View.GONE);
        }else {
            ivCollect2.setVisibility(View.GONE);
            ivCollect1.setVisibility(View.VISIBLE);
        }
    }
    @OnClick(R.id.btn_take_order)
    public void onClick() {
        StoreDingDan storeDingDan = new StoreDingDan();
        storeDingDan.setUser(user);
        storeDingDan.setName(store.getName());
        storeDingDan.setMoney(store.getMoney());
        storeDingDan.setprice(store.getprice());
        storeDingDan.setType(store.getType());
        storeDingDan.setpicture(store.getpicture());
        storeDingDan.setBianhao(store.getBianhao());
        storeDingDan.setTime(longToDate());
        DbSqliteHelper.getInstance(SearchDetailActivity.this).saveStoreDingDan(storeDingDan);
        Toast.makeText(SearchDetailActivity.this, "抢购成功!", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(
                new EventBusBean("3"));
    }

    public static String longToDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }
}
