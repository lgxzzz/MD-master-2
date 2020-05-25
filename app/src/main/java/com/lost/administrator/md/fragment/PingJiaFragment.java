package com.lost.administrator.md.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lost.administrator.md.R;
import com.lost.administrator.md.activity.MyPingJiaActivity;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.EventBusBean;
import com.lost.administrator.md.entity.PingJiaBean;
import com.lost.administrator.md.entity.Store;
import com.lost.administrator.md.entity.User;
import com.lost.administrator.md.utils.CommonAdapter;
import com.lost.administrator.md.utils.DensityUtils;
import com.lost.administrator.md.utils.SharedPrefsUtils;
import com.lost.administrator.md.utils.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created
 */
public class PingJiaFragment extends Fragment {

    @BindView(R.id.lv_myPingJia)
    ListView listView;
    Unbinder unbinder;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private LinearLayout ll_title;
    private ScrollView scroll;

    private CommonAdapter<PingJiaBean> adapter;
    private TextView tv_title;
    private ImageView iv_back;
    private Store store;
    private String user;
    private List<PingJiaBean> mlist;
    User userBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pingjia_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
        Bundle bundle = getArguments();
        store = (Store) bundle.getSerializable("DATA");
        user = SharedPrefsUtils.getString(getActivity(), "user");
        String[] args = new String[]{user};
        userBean = DbSqliteHelper.getInstance(getActivity()).findUser(args);
        initData();

        return view;
    }

    private void initData() {
        mlist = DbSqliteHelper.getInstance(getActivity()).getAllPingJia(user);

        adapter = new CommonAdapter<PingJiaBean>(getActivity(), mlist, R.layout.item_mypingjia_layout) {
            @Override
            public void convert(ViewHolder holder, PingJiaBean m) {
                holder.setText(R.id.tv_name_myPingJia, m.getUser())

                        .setRatingBar(R.id.ratingBar_myPingJia, Float.parseFloat(m.getRatbar()))
                        .setText(R.id.conetent, m.getComment())
                        .setText(R.id.tv_time_myPingJia, m.getTime());

                if (TextUtils.isEmpty(userBean.getUrl())||userBean.getUrl().equals("null")) {
                    holder.setImageResource(R.id.iv_icon_myPingJia,R.drawable.mine_image1);

                } else {
                    holder.setImgUrl(R.id.iv_icon_myPingJia,userBean.getUrl());
                }


            }
        };
        listView.setAdapter(adapter);
        DensityUtils.setListViewHeightBasedOnChildren(listView);

    }

    @Subscribe
    public void onEventMainThread(EventBusBean event) {

        if (event.getTag().equals("4")) {
            mlist = DbSqliteHelper.getInstance(getActivity()).getAllPingJia(user);
            adapter.updateAll(mlist);

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.fab)
    public void onClick() {
        Intent intent = new Intent(getContext(), MyPingJiaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DATA", store);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
