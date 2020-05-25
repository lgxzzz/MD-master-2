package com.lost.administrator.md.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lost.administrator.md.R;
import com.lost.administrator.md.activity.ClothingActivity;
import com.lost.administrator.md.activity.FindActivity;
import com.lost.administrator.md.activity.MapActivity;
import com.lost.administrator.md.activity.SearchActivity;
import com.lost.administrator.md.adapter.RecyclerViewContentAdapter;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.EventBusBean;
import com.lost.administrator.md.entity.Menu;
import com.lost.administrator.md.entity.Store;
import com.lost.administrator.md.utils.CommonAdapter;
import com.lost.administrator.md.utils.ViewHolder;
import com.lost.administrator.md.widget.ObservableScrollView;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created
 */
public class HomeFragment extends Fragment implements ObservableScrollView.OnScrollChangeListener {


    Unbinder unbinder;
    @BindView(R.id.adress)
    TextView adress;
    private ObservableScrollView scrollView;
    private LinearLayout search_goods;
    private static final int REQUESTCODE = 0x0011;
    private List<Integer> list_img = new ArrayList<Integer>();
    private Banner banner;
    //定位控件所在布局
    private LinearLayout ll_dingWei;
    //经纬度
    private double latitude;
    private double longitude;
    //显示定位地点的textView
    private TextView tv_address;


    private String aoiName = "";


    //定位请求码
    public static final int REQUEST_LOCATION = 0x11;

    //加载时的动画
    private AnimationDrawable anim;
    private ImageView iv_anim;
    private EditText follow_edit;
    private LinearLayout ll_animation;
    private ImageView ll_search_home;
    private ImageView ll_location;
    //分类菜单
    private GridView gridView;
    private List<Menu> list_menu = new ArrayList<Menu>();
    private CommonAdapter<Menu> adapter_menu;


    private List<Store> list_store;

    private RecyclerView listView_store;
    FrameLayout serrchlay;
    //是否加载完毕全部商家
    private boolean isOver = false;
    private int loadTime = 0;
    RecyclerViewContentAdapter mRecyclerViewContentCommonadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.homefragment_layout, null);
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
        initView(view);
        initData();

        anim.start();

//        anim.stop();
//        //加载完成后停止动画
//        ll_animation.setVisibility(View.GONE);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }


    private void initData() {
        loadTime = 0;

        serrchlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        ll_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapActivity.class));
            }
        });
        //初始化定位
        anim = (AnimationDrawable) iv_anim.getDrawable();

        //分类菜单
        list_menu.add(new Menu(R.drawable.xc1, "男上装"));
        list_menu.add(new Menu(R.drawable.tp1, "女上装"));
        list_menu.add(new Menu(R.drawable.yp1, "男下装"));
        list_menu.add(new Menu(R.drawable.yl1, "女下装"));
        adapter_menu = new CommonAdapter<Menu>(getActivity(), list_menu, R.layout.item_menu_layout) {
            @Override
            public void convert(ViewHolder holder, Menu menu) {
                holder.setImageResource(R.id.iv_menu, menu.getImg())
                        .setText(R.id.tv_menu, menu.getName());
            }
        };
        gridView.setAdapter(adapter_menu);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), FindActivity.class);
                intent.putExtra("type", list_menu.get(position).getName());
                startActivity(intent);
            }
        });
        list_store = DbSqliteHelper.getInstance(getActivity()).getAllStore();
        //推荐算法，根据收藏,点击次数计算
        Collections.sort(list_store, new Comparator<Store>() {

            @Override
            public int compare(Store o1, Store o2) {
                return Integer.parseInt(o2.getIndex())-Integer.parseInt(o1.getIndex());

            }
        });

        listView_store.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setContentCommonadapter();
        follow_edit.addTextChangedListener(new TextWatcher() {
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
    }

    private void setContentCommonadapter() {
        mRecyclerViewContentCommonadapter = new RecyclerViewContentAdapter(getActivity(), list_store);
        listView_store.setAdapter(mRecyclerViewContentCommonadapter);
        mRecyclerViewContentCommonadapter.setOnItemClickListener(new RecyclerViewContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewContentAdapter.ViewHolder holder, Store store) {
                int index = Integer.parseInt(store.getIndex());
                store.setIndex(String.valueOf(index+1));
                DbSqliteHelper.getInstance(getActivity()).updateStore(store);
                initData();
                Intent intent = new Intent(getContext(), ClothingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DATA", store);
                intent.putExtras(bundle);
                startActivity(intent);
            }


        });
    }

    @SuppressLint("WrongViewCast")
    private void initView(View view) {
        banner = (Banner) view.findViewById(R.id.banner);
        serrchlay = (FrameLayout) view.findViewById(R.id.serrchlay);
        scrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        search_goods = (LinearLayout) view.findViewById(R.id.search_goods);

        iv_anim = (ImageView) view.findViewById(R.id.iv_animation);
        ll_animation = (LinearLayout) view.findViewById(R.id.ll_animation);
        gridView = (GridView) view.findViewById(R.id.gv_menu);
        listView_store = (RecyclerView) view.findViewById(R.id.lv_store);

        ll_search_home = (ImageView) view.findViewById(R.id.ll_search_home);//搜索
        follow_edit = (EditText) view.findViewById(R.id.follow_edit);//输入框
        ll_location = (ImageView) view.findViewById(R.id.ll_location);//定位
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION) {
            if (resultCode == getActivity().RESULT_OK) {
                aoiName = data.getStringExtra("address");
                adress.setText(aoiName);
            }
        }
    }

    //重写滑动方法
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_location, R.id.ll_search_home, R.id.follow_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_location:
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivityForResult(intent, REQUESTCODE);
                break;
            case R.id.ll_search_home:
                startActivity(new Intent(getActivity(), SearchActivity.class));//跳转搜索界面
                break;
            case R.id.follow_edit:
                // startActivity(new Intent(getActivity(), SearchActivity.class));//跳转搜索界面
                break;
        }
    }


    @Subscribe
    public void onEventMainThread(EventBusBean event) {

        if (event.getTag().equals("1")) {
            list_store = DbSqliteHelper.getInstance(getActivity()).getAllStore();
            mRecyclerViewContentCommonadapter.updateAll(list_store);

        } else if (event.getTag().equals("5")) {
            adress.setText(event.getCity());
        }else if (event.getTag().equals("6")) {
            int index = Integer.parseInt(event.getStore().getIndex());
            event.getStore().setIndex(String.valueOf(index+1));
            DbSqliteHelper.getInstance(getActivity()).updateStore(event.getStore());
            initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
    }
}


