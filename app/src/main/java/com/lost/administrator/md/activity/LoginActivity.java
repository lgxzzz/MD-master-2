package com.lost.administrator.md.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lost.administrator.md.R;
import com.lost.administrator.md.app.MyAplication;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.User;
import com.lost.administrator.md.utils.SharedPrefsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.account_input)
    EditText accountInput;
    @BindView(R.id.password_input)
    EditText passwordInput;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_forget_pass)
    TextView btnForgetPass;
    private String type;//用户类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        MyAplication.addActivity(this);
        type = getIntent().getStringExtra("type");
    }


    @OnClick({R.id.btn_login, R.id.btn_forget_pass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loging();
                break;
            case R.id.btn_forget_pass:
                //注册
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    private void loging() {
        if (TextUtils.isEmpty(accountInput.getText().toString())) {
            Toast.makeText(LoginActivity.this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwordInput.getText().toString())) {
            Toast.makeText(LoginActivity.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        String stuNmNo = accountInput.getText().toString();
        String[] args = new String[]{stuNmNo};
        //从数据里面获取用户数据
        User userBean = DbSqliteHelper.getInstance(this).findUser(args);
        if (TextUtils.isEmpty(userBean.getName())) {
            Toast.makeText(LoginActivity.this, "此用户不存在!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (userBean.getPsw().equals(passwordInput.getText().toString())) {
                if (userBean.getType().equals(type)) {
                    SharedPrefsUtils.putString(LoginActivity.this, "user", userBean.getName());
                    SharedPrefsUtils.putString(LoginActivity.this, "type", userBean.getType());
                    Toast.makeText(LoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号登录类型错误!", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(LoginActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
