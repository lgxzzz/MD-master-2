package com.lost.administrator.md.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lost.administrator.md.R;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.EventBusBean;
import com.lost.administrator.md.entity.ShangjiaInfo;
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

public class ShangjiaActivity extends AppCompatActivity {
    /**
     * 商家认证页面
     */


    @BindView(R.id.iv_back_more)
    ImageView ivBackMore;
    @BindView(R.id.ll1_1)
    LinearLayout ll11;
    @BindView(R.id.lv_store)
    MyLoadListView lvStore;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_more)
    RelativeLayout activityMore;
    private CommonAdapter<ShangjiaInfo> adapter_store;
    private List<ShangjiaInfo> list_store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
        list_store = DbSqliteHelper.getInstance(ShangjiaActivity.this).getAllShangjia();

        adapter_store=new CommonAdapter<ShangjiaInfo>(ShangjiaActivity.this,list_store,R.layout.activity_shangjia) {
            @Override
            public void convert(ViewHolder holder, ShangjiaInfo store) {
                holder.setText(R.id.tv_name_store,"店铺:"+store.getShopName())
                        .setText(R.id.tv_distance_store,"负责人:"+store.getName())
                        .setText(R.id.tv_adress,"联系方式:"+store.getAdress())
                        .setText(R.id.tv_canOrder_store,"经营类型:"+store.getType())
                        .setText(R.id.tv_begin_store,"经营类型:"+store.getAdress())
                        .setImgUrl(R.id.push,store.getPicture());
                if (store.getIsExamine().equals("1")){
                    holder.setImageResource(R.id.sheheh,R.mipmap.no_shnehe);
                }else if (store.getIsExamine().equals("2")){
                    holder.setImageResource(R.id.sheheh,R.mipmap.fail);
                }else {
                    holder.setImageResource(R.id.sheheh,R.mipmap.yes_shenhe);
                }
            }
        };
        lvStore.setAdapter(adapter_store);
        lvStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShangjiaActivity.this, AddGoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DATA", list_store.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    @OnClick({R.id.iv_back_more, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_more:
                finish();
                break;
            case R.id.fab:
                startActivity(new Intent(ShangjiaActivity.this,AddGoodActivity.class));
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(EventBusBean event) {

        if (event.getTag().equals("2")) {
            list_store = DbSqliteHelper.getInstance(ShangjiaActivity.this).getAllShangjia();
            adapter_store.updateAll(list_store);

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
    }
}
