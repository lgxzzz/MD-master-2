package com.lost.administrator.md.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.lost.administrator.md.R;
import com.lost.administrator.md.activity.ClothingActivity;
import com.lost.administrator.md.activity.CollectionActivity;
import com.lost.administrator.md.activity.FindActivity;
import com.lost.administrator.md.adapter.RecyclerViewCollAdapter;
import com.lost.administrator.md.adapter.RecyclerViewContentAdapter;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.CollectionBean;
import com.lost.administrator.md.entity.EventBusBean;
import com.lost.administrator.md.entity.Store;
import com.lost.administrator.md.entity.StoreDingDan;
import com.lost.administrator.md.entity.User;
import com.lost.administrator.md.utils.CommonAdapter;
import com.lost.administrator.md.utils.DensityUtils;
import com.lost.administrator.md.utils.ViewHolder;
import com.lost.administrator.md.widget.MyLoadListView;
import com.lost.administrator.md.widget.RoundCornerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created
 */
public class OrderFragment extends Fragment {


    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.lv_store)
    RecyclerView lvStore;
    Unbinder unbinder;
    List<CollectionBean> list_store;

    RecyclerViewCollAdapter recyclerViewCollAdapter;
    User mUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderfragment_layout, null);
        unbinder = ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
        mUser = DbSqliteHelper.mCurrentUser;
        if (mUser.getType().equals("商家")){
            list_store = DbSqliteHelper.getInstance(getActivity()).getAllCollection();
        }else{
            list_store = DbSqliteHelper.getInstance(getActivity()).getCollectionsByUser(mUser.getName());
        }
        lvStore.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        setContentCommonadapter();
        return view;

    }

    private void setContentCommonadapter() {
        recyclerViewCollAdapter = new RecyclerViewCollAdapter(getActivity(), list_store);
        lvStore.setAdapter(recyclerViewCollAdapter);
        recyclerViewCollAdapter.setOnItemClickListener(new RecyclerViewCollAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewCollAdapter.ViewHolder holder, CollectionBean store) {

                Store store1=new Store();
                store1.setName(store.getName());
                store1.setType(store.getType());
                store1.setpicture(store.getpicture());
                store1.setprice(store.getprice());
                store1.setBianhao(store.getBianhao());
                store1.setMoney(store.getMoney());
                Intent intent = new Intent(getContext(), ClothingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DATA", store1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerViewCollAdapter.setOnItemLongClickListener(new RecyclerViewCollAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(RecyclerViewCollAdapter.ViewHolder holder, CollectionBean store) {
                showDeleteDialog(store);
            }
        });

    }

    @Subscribe
    public void onEventMainThread(EventBusBean event) {

        if (event.getTag().equals("3")) {
            list_store = DbSqliteHelper.getInstance(getActivity()).getAllCollection();
            recyclerViewCollAdapter.updateAll(list_store);

        }
    }

    private void showDeleteDialog(final CollectionBean store) {
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
                DbSqliteHelper.getInstance(getActivity()).deleteCollection(store.getId());
                list_store = DbSqliteHelper.getInstance(getActivity()).getAllCollection();
                recyclerViewCollAdapter.updateAll(list_store);
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
    }

}
