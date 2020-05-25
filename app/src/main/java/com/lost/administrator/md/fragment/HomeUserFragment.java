package com.lost.administrator.md.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lost.administrator.md.R;
import com.lost.administrator.md.activity.AddStoreActivity;
import com.lost.administrator.md.activity.ClothingActivity;
import com.lost.administrator.md.adapter.RecyclerViewContentAdapter;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.EventBusBean;
import com.lost.administrator.md.entity.Store;
import com.lost.administrator.md.utils.GlideImageLoader;
import com.lost.administrator.md.widget.RoundCornerDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeUserFragment extends Fragment {//商家
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.follow_edit)
    EditText followEdit;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.lv_store)
    RecyclerView listView_store;
    @BindView(R.id.scrollView)
    LinearLayout scrollView;

    @BindView(R.id.iv_animation)
    ImageView ivAnimation;
    @BindView(R.id.ll_animation)
    LinearLayout llAnimation;

    Unbinder unbinder;
    private List<Store> list_store;
    private List<Integer> list_img = new ArrayList<Integer>();
    RecyclerViewContentAdapter mRecyclerViewContentCommonadapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homemangfragment_layout, container, false);

        unbinder = ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
        initdata();
        list_store = DbSqliteHelper.getInstance(getActivity()).getAllStore();
        listView_store.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setContentCommonadapter();


        followEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    list_store = DbSqliteHelper.getInstance(getActivity()).getStores(s.toString());
                    mRecyclerViewContentCommonadapter.updateAll(list_store);
                } else {
                    list_store = DbSqliteHelper.getInstance(getActivity()).getAllStore();
                    mRecyclerViewContentCommonadapter.updateAll(list_store);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void setContentCommonadapter() {
        mRecyclerViewContentCommonadapter = new RecyclerViewContentAdapter(getActivity(), list_store);

        listView_store.setAdapter(mRecyclerViewContentCommonadapter);
        mRecyclerViewContentCommonadapter.setOnItemClickListener(new RecyclerViewContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewContentAdapter.ViewHolder holder, Store store) {
                Intent intent = new Intent(getContext(), AddStoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DATA", store);
                intent.putExtras(bundle);
                startActivity(intent);
            }


        });
        mRecyclerViewContentCommonadapter.setOnItemLongClickListener(new RecyclerViewContentAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(RecyclerViewContentAdapter.ViewHolder holder, Store store) {
                showDeleteDialog(store);
            }
        });

    }

    private void showDeleteDialog(final Store store) {
        View view = View.inflate(getActivity(), R.layout.dialog_two_btn, null);
        final RoundCornerDialog roundCornerDialog = new RoundCornerDialog(getActivity(), 0, 0, view, R.style.RoundCornerDialog);
        roundCornerDialog.show();
        roundCornerDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        roundCornerDialog.setOnKeyListener(keylistener);//设置点击返回键Dialog不消失

        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        TextView tv_logout_confirm = (TextView) view.findViewById(R.id.tv_logout_confirm);
        TextView tv_logout_cancel = (TextView) view.findViewById(R.id.tv_logout_cancel);
        tv_message.setText("确定删除吗？");

        //确定
        tv_logout_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbSqliteHelper.getInstance(getActivity()).deleteStore(store.getId());
                list_store = DbSqliteHelper.getInstance(getActivity()).getAllStore();
                mRecyclerViewContentCommonadapter.updateAll(list_store);
                roundCornerDialog.dismiss();

            }
        });
        //取消
        tv_logout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundCornerDialog.dismiss();
            }
        });
    }

    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    private void initdata() {
        list_img.add(R.mipmap.five);
        list_img.add(R.mipmap.one);
        list_img.add(R.mipmap.two);
        list_img.add(R.mipmap.three);
        list_img.add(R.mipmap.four);

        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(list_img);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
    }

    @Subscribe
    public void onEventMainThread(EventBusBean event) {

        if (event.getTag().equals("1")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //更新界面
                    list_store = DbSqliteHelper.getInstance(getActivity()).getAllStore();
                    mRecyclerViewContentCommonadapter.updateAll(list_store);
                }
            });


        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.follow_edit, R.id.add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.follow_edit:
                //startActivity(new Intent(getActivity(), SearchActivity.class));

                break;
            case R.id.add:
                Intent intent = new Intent(getActivity(), AddStoreActivity.class);
                startActivityForResult(intent, 666); // requestCode -> 666

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666) {
            if (data != null) {
                if (data.getStringExtra("data").equals("key")) {
                    Log.e("数据", "===" + "测试data");
                    //更新界面

                    list_store = DbSqliteHelper.getInstance(getActivity()).getAllStore();
                    mRecyclerViewContentCommonadapter.updateAll(list_store);

                }
            }


        }
    }
}
