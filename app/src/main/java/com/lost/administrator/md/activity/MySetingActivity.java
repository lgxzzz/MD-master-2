package com.lost.administrator.md.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lost.administrator.md.R;
import com.lost.administrator.md.utils.SharedPrefsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MySetingActivity extends AppCompatActivity {
    private String type;
    /**
     * 设置界面
     */
    @BindView(R.id.shangjia)
    LinearLayout shangjia;
    @BindView(R.id.account)
    LinearLayout account;
    @BindView(R.id.tv_name_account)
    TextView tvNameAccount;
    @BindView(R.id.tv_psw_account)
    TextView tvPswAccount;
    @BindView(R.id.pasww)
    LinearLayout pasww;
    @BindView(R.id.iv_back_account)
    ImageView ivBackAccount;
    @BindView(R.id.ll1_1)
    LinearLayout ll11;
    @BindView(R.id.activity_my_account)
    RelativeLayout activityMyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        String username = SharedPrefsUtils.getString(MySetingActivity.this, "user");
        type = SharedPrefsUtils.getString(MySetingActivity.this,"type");
        tvNameAccount.setText(username);
        if (type.equals("商家")){
            shangjia.setVisibility(View.VISIBLE);
            account.setVisibility(View.VISIBLE);
            pasww.setVisibility(View.VISIBLE);
        }else {
            shangjia.setVisibility(View.GONE);
            account.setVisibility(View.VISIBLE);
            pasww.setVisibility(View.VISIBLE);
        }

    }


    @OnClick({R.id.shangjia, R.id.pasww, R.id.iv_back_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shangjia:
                startActivity(new Intent(MySetingActivity.this, ShangjiaActivity.class));
                break;
            case R.id.pasww:
                startActivity(new Intent(MySetingActivity.this, AddPasswordActivity.class));
                break;
            case R.id.iv_back_account:
                finish();
                break;
        }
    }
}
