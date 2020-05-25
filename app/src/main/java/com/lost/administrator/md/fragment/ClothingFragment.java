package com.lost.administrator.md.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import butterknife.Unbinder;

public class ClothingFragment extends Fragment {
    //服装详情页面

    Unbinder unbinder;
    Store store;
    @BindView(R.id.goodname)
    TextView goodname;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.good_picture)
    ImageView goodPicture;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.btn_take_order)
    Button btnTakeOrder;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;

    private String user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_good_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        store = (Store) getActivity().getIntent().getSerializableExtra("DATA");
        user = SharedPrefsUtils.getString(getActivity(), "user");

        goodname.setText(store.getMoney());
        type.setText(store.getName() + store.getType() + store.getBianhao());
        Glide.with(getActivity()).load(store.getpicture()).into(goodPicture);
        detail.setText(store.getprice());

        final CollectionBean collect = DbSqliteHelper.getInstance(getActivity()).findColl(store.getName(), SharedPrefsUtils.getString(getActivity(), "user"));
        if (!TextUtils.isEmpty(collect.getName())) {
            btnTakeOrder.setText("已收藏");
            

        } else {
            btnTakeOrder.setText("收藏");
        
        }

        btnTakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收藏
                if (btnTakeOrder.getText().toString().equals("收藏")){
                CollectionBean collectionBean = new CollectionBean();
                collectionBean.setUser(SharedPrefsUtils.getString(getActivity(), "user"));
                collectionBean.setName(store.getName());
                collectionBean.setMoney(store.getMoney());
                collectionBean.setpicture(store.getpicture());
                collectionBean.setprice(store.getprice());
                collectionBean.setType(store.getType());
                collectionBean.setBianhao(store.getBianhao());
                boolean hasColl = DbSqliteHelper.getInstance(getActivity()).saveCollection(collectionBean);

                if (hasColl) {
                    EventBus.getDefault().post(
                            new EventBusBean("6",store));
                    Toast.makeText(getActivity(), "已收藏!", Toast.LENGTH_SHORT).show();
                } else {
                    btnTakeOrder.setText("已收藏");
                    EventBus.getDefault().post(
                            new EventBusBean("3"));
                    Toast.makeText(getActivity(), "收藏成功!", Toast.LENGTH_SHORT).show();

                }
                }else {
                    //取消收藏
                    DbSqliteHelper.getInstance(getActivity()).deleteCollection(collect.getId());
                    btnTakeOrder.setText("收藏");
                    EventBus.getDefault().post(
                            new EventBusBean("3"));
                }
                
                
                
            }
        });
  
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public static String longToDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }
}
