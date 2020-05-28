package com.lost.administrator.md.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lost.administrator.md.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class GoodDetailActivity extends AppCompatActivity {

    @BindView(R.id.ll_title)
    LinearLayout llTitle;
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
    @BindView(R.id.tv_selected)
    TextView tvSelected;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.btn_take_order)
    Button btnTakeOrder;
    @BindView(R.id.btn_take_buy)
    Button btnTakeBuy;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @BindView(R.id.iv_collect1)
    ImageView ivCollect1;
    @BindView(R.id.iv_collect2)
    ImageView ivCollect2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back_share, R.id.btn_take_order, R.id.iv_collect1, R.id.iv_collect2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_share:
                finish();
                break;
            case R.id.btn_take_order:
                break;
            case R.id.iv_collect1:
                //ivCollect1.setVisibility(View.GONE);
                //ivCollect2.setVisibility(View.VISIBLE);
                //Toast.makeText(GoodDetailActivity.this,"收藏成功！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_collect2:
                //ivCollect2.setVisibility(View.GONE);
                //ivCollect1.setVisibility(View.VISIBLE);
                //Toast.makeText(GoodDetailActivity.this,"取消收藏！",Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
