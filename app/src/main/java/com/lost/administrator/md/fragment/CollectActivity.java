package com.lost.administrator.md.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lost.administrator.md.R;
import com.lost.administrator.md.activity.CollectionActivity;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.CollectionBean;
import com.lost.administrator.md.utils.CommonAdapter;
import com.lost.administrator.md.utils.DensityUtils;
import com.lost.administrator.md.utils.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectActivity extends AppCompatActivity {
    @BindView(R.id.ll_title)
    LinearLayout title;
    @BindView(R.id.lv_collect)
    ListView collect;
    @BindView(R.id.ll_nothing)
    LinearLayout nothing;
    @BindView(R.id.iv_back_collect)
    ImageView ivBackCollect;
    private List<CollectionBean> list_store;
    private CommonAdapter<CollectionBean> adapter_store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        list_store = DbSqliteHelper.getInstance(CollectActivity.this).getAllCollection();

        adapter_store = new CommonAdapter<CollectionBean>(CollectActivity.this, list_store, R.layout.layout_goods_item) {
            @Override
            public void convert(ViewHolder holder, final CollectionBean store) {
                holder.setText(R.id.tv_product_name, store.getName())
                        .setTexts(R.id.tv_area,store.getBianhao())
                        .setText(R.id.tv_product_pirce, "￥" + store.getprice())
                        .setTexts(R.id.tv_cuxiao_pirce, "￥" + store.getMoney())
                        .setImgUrl(R.id.iv_product_picture, store.getpicture());


            }
        };
        collect.setAdapter(adapter_store);
        collect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CollectActivity.this, CollectionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DATA", list_store.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        DensityUtils.setListViewHeightBasedOnChildren(collect);
    }

    @OnClick(R.id.iv_back_collect)
    public void onClick() {
        finish();
    }
}
