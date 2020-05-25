package com.lost.administrator.md.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lost.administrator.md.R;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.EventBusBean;
import com.lost.administrator.md.entity.PingJiaBean;
import com.lost.administrator.md.entity.Store;
import com.lost.administrator.md.utils.SharedPrefsUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MyPingJiaActivity extends AppCompatActivity {

    @BindView(R.id.iv_back_myPingJia)
    ImageView ivBackMyPingJia;
    @BindView(R.id.tv_title_myPingJia)
    TextView tvTitleMyPingJia;
    @BindView(R.id.ll_title_myPingJia)
    LinearLayout llTitleMyPingJia;
    @BindView(R.id.ev_pingyu)
    EditText evPingyu;
    @BindView(R.id.ratbar)
    RatingBar ratbar;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.activity_my_ping_jia)
    LinearLayout activityMyPingJia;
    private Store store;
    private String user;
    private float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ping_jia);
        ButterKnife.bind(this);
        user= SharedPrefsUtils.getString(MyPingJiaActivity.this,"user");
        store = (Store) getIntent().getSerializableExtra("DATA");
        ratbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating=rating;
            }
        });
    }


    @OnClick({R.id.iv_back_myPingJia, R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_myPingJia:
                finish();
                break;
            case R.id.save:
                if (TextUtils.isEmpty(evPingyu.getText().toString())) {
                    Toast.makeText(MyPingJiaActivity.this, "评语不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                PingJiaBean pingJiaBean = new PingJiaBean();
                pingJiaBean.setUser(user);
                pingJiaBean.setGoodName(store.getName());
                pingJiaBean.setComment(evPingyu.getText().toString());
                pingJiaBean.setRatbar(rating+"");
                pingJiaBean.setTime(longToDate());
                DbSqliteHelper.getInstance(MyPingJiaActivity.this).savePingJia(pingJiaBean);
                EventBus.getDefault().post(
                        new EventBusBean("4"));
                finish();
                break;
        }
    }

    public static String longToDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }
}
