package com.lost.administrator.md.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lost.administrator.md.R;
import com.lost.administrator.md.adapter.RecyclerViewContentAdapter;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.EventBusBean;
import com.lost.administrator.md.entity.Store;
import com.lost.administrator.md.utils.CommonAdapter;
import com.lost.administrator.md.utils.ViewHolder;
import com.lost.administrator.md.widget.MyLoadListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FindActivity extends AppCompatActivity {


    @BindView(R.id.iv_back_find)
    ImageView ivBackFind;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.lv_store)
    RecyclerView listView_store;
    @BindView(R.id.titles)
    TextView titles;
    RecyclerViewContentAdapter mRecyclerViewContentCommonadapter;
    private List<Store> list_store;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
        type = getIntent().getStringExtra("type");
        titles.setText(type);
        list_store = DbSqliteHelper.getInstance(FindActivity.this).getStore(type);

        listView_store.setLayoutManager(new GridLayoutManager(FindActivity.this, 3));
        setContentCommonadapter();
    }
    private void setContentCommonadapter() {
        mRecyclerViewContentCommonadapter = new RecyclerViewContentAdapter(FindActivity.this, list_store);
        listView_store.setAdapter(mRecyclerViewContentCommonadapter);
        mRecyclerViewContentCommonadapter.setOnItemClickListener(new RecyclerViewContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewContentAdapter.ViewHolder holder, Store store) {

                Intent intent = new Intent(FindActivity.this, ClothingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DATA", store);
                intent.putExtras(bundle);
                startActivity(intent);
            }


        });
    }
    @Subscribe
    public void onEventMainThread(EventBusBean event) {

        if (event.getTag().equals("1")) {
            list_store = DbSqliteHelper.getInstance(FindActivity.this).getAllStore();
            mRecyclerViewContentCommonadapter.updateAll(list_store);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.iv_back_find)
    public void onClick() {
        finish();
    }
}
