package com.lost.administrator.md.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lost.administrator.md.R;
import com.lost.administrator.md.activity.LoginActivity;
import com.lost.administrator.md.activity.MySetingActivity;
import com.lost.administrator.md.activity.MyPingJiaActivity;
import com.lost.administrator.md.activity.UserActivity;
import com.lost.administrator.md.entity.User;
import com.lost.administrator.md.utils.SharedPrefsUtils;

public class MineFragment extends Fragment implements View.OnClickListener {

    private LinearLayout ll_content;
    private LinearLayout ll_pingJia;

    private LinearLayout ll_help;

    private TextView tv_aboutus, title;
    private LinearLayout ll_more_mine;
    private User user = new User();
    private String type;
    private FragmentActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.minefragment_layout, container, false);
        initView(view);
        initEvent();
        String username = SharedPrefsUtils.getString(getActivity(), "user");
        type = SharedPrefsUtils.getString(getActivity(), "type");
        title.setText(username);
        if (type.equals("商家")){

            ll_pingJia.setVisibility(View.GONE);
        }else {
            ll_pingJia.setVisibility(View.GONE);

        }
        return view;
    }

    private void initEvent() {
        ll_content.setOnClickListener(this);
        ll_pingJia.setOnClickListener(this);

        ll_help.setOnClickListener(this);
        tv_aboutus.setOnClickListener(this);
        ll_more_mine.setOnClickListener(this);

    }


    private void initView(View view) {
        ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
        ll_pingJia = (LinearLayout) view.findViewById(R.id.ll_pingJia_mine);

        ll_more_mine = (LinearLayout) view.findViewById(R.id.ll_more_mine);
        ll_help = (LinearLayout) view.findViewById(R.id.ll_help_mine);
        title = (TextView) view.findViewById(R.id.title);
        tv_aboutus = (TextView) view.findViewById(R.id.tv_aboutus);


    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.mContext=getActivity();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_content:
                if (user == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.ll_pingJia_mine:
                startActivity(new Intent(getActivity(), MyPingJiaActivity.class));
                break;

            case R.id.tv_aboutus:
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("联系我");
                builder.setMessage("Email：2258629201@qq.com");
                builder.setNegativeButton("确定",null);
                builder.show();
                break;
            case R.id.ll_help_mine:
                startActivity(new Intent(getActivity(), MySetingActivity.class));
                break;
            case R.id.ll_more_mine:
                startActivity(new Intent(getActivity(),UserActivity.class));
                getActivity().finish();
                break;
        }
    }
}
