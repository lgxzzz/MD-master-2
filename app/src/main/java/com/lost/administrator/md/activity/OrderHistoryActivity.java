package com.lost.administrator.md.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.lost.administrator.md.R;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.Store;
import com.lost.administrator.md.entity.StoreDingDan;
import com.lost.administrator.md.utils.CommonAdapter;
import com.lost.administrator.md.utils.DensityUtils;
import com.lost.administrator.md.utils.SharedPrefsUtils;
import com.lost.administrator.md.utils.ViewHolder;
import com.lost.administrator.md.widget.MyLoadListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderHistoryActivity extends AppCompatActivity {
    @BindView(R.id.iv_back_more)
    ImageView ivBackMore;
    @BindView(R.id.ll1_1)
    LinearLayout ll11;
    @BindView(R.id.lv_store)
    MyLoadListView lvStore;
    @BindView(R.id.activity_more)
    RelativeLayout activityMore;
    private CommonAdapter<StoreDingDan> adapter_store;
    List<StoreDingDan> mlists;
    private String user;
    private String type;

    /**
     * 历史订单
     *
     * @param savedInstanceState
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderinfo);
        ButterKnife.bind(this);
        user = SharedPrefsUtils.getString(OrderHistoryActivity.this, "user");

        //从数据获取历史订单数据
        mlists = DbSqliteHelper.getInstance(OrderHistoryActivity.this).getAllStoreDingDan(user);

        if (mlists.size() == 0) {
            return;
        }
        adapter_store = new CommonAdapter<StoreDingDan>(OrderHistoryActivity.this, mlists, R.layout.item_order_layout) {
            @Override
            public void convert(ViewHolder holder, final StoreDingDan store) {
                holder.setText(R.id.tv_storeName_order, store.getName())
                        .setText(R.id.tv_price_order, "￥" + store.getprice())
                        .setText(R.id.lv_list_order,  store.getTime());

                ImageView iv = holder.getView(R.id.iv_icon_order);
                Glide.with(OrderHistoryActivity.this).load(store.getpicture()).into(iv);
                holder.getView(R.id.button).setVisibility(View.GONE);

            }
        };
        lvStore.setAdapter(adapter_store);
        DensityUtils.setListViewHeightBasedOnChildren(lvStore);
    }


    @OnClick(R.id.iv_back_more)
    public void onClick() {
        finish();
    }
}
