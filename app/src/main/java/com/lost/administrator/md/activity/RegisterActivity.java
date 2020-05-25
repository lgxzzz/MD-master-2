package com.lost.administrator.md.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lost.administrator.md.R;
import com.lost.administrator.md.app.MyAplication;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.account_input)
    EditText accountInput;
    @BindView(R.id.password_input)
    EditText passwordInput;
    @BindView(R.id.password_input2)
    EditText passwordInput2;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        MyAplication.addActivity(this);
        type = getIntent().getStringExtra("type");

    }


    @OnClick(R.id.btn_login)
    public void onClick() {
        String number=accountInput.getText().toString().trim();
        String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[12378]{1})|([4]{1}[7]{1}))[0-9]{8}$";
        String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1}))[0-9]{8}$";
        String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1}))[0-9]{8}$";
        if (TextUtils.isEmpty(accountInput.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "手机号码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwordInput.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwordInput2.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "请再次确认密码!", Toast.LENGTH_SHORT).show();
            return;
        }
        User userBean = new User();
        userBean.setName(accountInput.getText().toString());
        userBean.setPsw(passwordInput.getText().toString());
        userBean.setType(type);
        boolean hasUser = DbSqliteHelper.getInstance(RegisterActivity.this).saveUser(userBean);
        if (number.length()!=11){
            Toast.makeText(RegisterActivity.this, "请输入有效手机号！", Toast.LENGTH_SHORT).show();
        }else{
            if (number.matches(YD)||number.matches(LT)||number.matches(DX)){//判断手机号格式是否正确
                if (hasUser) {//验证用户是否存在数据库
                    Toast.makeText(RegisterActivity.this, "该用户已存在！", Toast.LENGTH_SHORT).show();
                }else if (!passwordInput2.getText().toString().equals(passwordInput.getText().toString())){//两次输入信息对比
                    Toast.makeText(RegisterActivity.this, "密码不一致！", Toast.LENGTH_SHORT).show();
                }else {//注册
                    SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (!TextUtils.isEmpty(sharedPreferences.getString("phone", null))) {
                        editor.remove("phone");
                    }
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else{
                Toast.makeText(RegisterActivity.this, "请输入有效手机号！", Toast.LENGTH_SHORT).show();
            }
        }
        /*String number=accountInput.getText().toString().trim();
        String pwd=passwordInput.getText().toString().trim();
        String pwd2=passwordInput2.getText().toString().trim();
        String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[12378]{1})|([4]{1}[7]{1}))[0-9]{8}$";
        String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1}))[0-9]{8}$";
        String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1}))[0-9]{8}$";
        if (number.isEmpty()||pwd.isEmpty()||pwd2.isEmpty()){
            if (TextUtils.isEmpty(accountInput.getText().toString())) {
                Toast.makeText(RegisterActivity.this, "手机号码不能为空!", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(passwordInput.getText().toString())) {
                Toast.makeText(RegisterActivity.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(passwordInput2.getText().toString())) {
                Toast.makeText(RegisterActivity.this, "请再次确认密码!", Toast.LENGTH_SHORT).show();
            }
        }else{
            if (number.length()!=11){
                Toast.makeText(RegisterActivity.this, "请输入有效手机号！", Toast.LENGTH_SHORT).show();
            }else{
                User userBean = new User();
                userBean.setName(accountInput.getText().toString());
                userBean.setPsw(passwordInput.getText().toString());
                userBean.setType(type);
                boolean hasUser = DbSqliteHelper.getInstance(RegisterActivity.this).saveUser(userBean);
                if (number.matches(YD)||number.matches(LT)||number.matches(DX)){//判断手机号格式是否正确
                    if (hasUser) {//验证用户是否存在数据库
                        Toast.makeText(RegisterActivity.this, "用户已存在,请直接登录！", Toast.LENGTH_SHORT).show();
                    }else if (!passwordInput2.getText().toString().equals(passwordInput.getText().toString())){//两次密码对比
                        Toast.makeText(RegisterActivity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                    }else {//注册
                        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (!TextUtils.isEmpty(sharedPreferences.getString("phone", null))) {
                            editor.remove("phone");
                        }
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "请输入有效手机号！", Toast.LENGTH_SHORT).show();
                }
            }
        }*/
    }
}
