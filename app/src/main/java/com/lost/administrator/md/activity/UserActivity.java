package com.lost.administrator.md.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lost.administrator.md.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends AppCompatActivity {

    @BindView(R.id.btn_user)
    Button btnUser;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_user, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_user:
                type="商家";
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_login:
                type="用户";
                Intent intents = new Intent(UserActivity.this, LoginActivity.class);
                Bundle bundles = new Bundle();
                bundles.putString("type", type);
                intents.putExtras(bundles);
                startActivity(intents);
                finish();
                break;
        }
    }
}
