package com.lost.administrator.md.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lost.administrator.md.R;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.User;
import com.lost.administrator.md.utils.SharedPrefsUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddPasswordActivity extends AppCompatActivity {
    private String username;
    @BindView(R.id.iv_back_add)
    ImageView ivBackAdd;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.et_phone)
    TextView etPhone;
    @BindView(R.id.et_password)//原密码
    EditText etPassword;
    @BindView(R.id.et_password2)//新密码
    EditText etPassword2;
    @BindView(R.id.et_password3)//新密码
    EditText etPassword3;
    @BindView(R.id.btn_save)//保存按钮
    Button btnSave;
    @BindView(R.id.activity_add_address)
    LinearLayout activityAddAddress;

    User userBean=new User();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        username=SharedPrefsUtils.getString(AddPasswordActivity.this,"user");
        String[] args = new String[]{username};
        userBean = DbSqliteHelper.getInstance(this).findUser(args);
        etPhone.setText(userBean.getName());
        //etPassword.setText(userBean.getPsw());
    }


    @OnClick({R.id.iv_back_add, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_add:
                finish();
                break;
            case R.id.btn_save:
                if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    Toast.makeText(AddPasswordActivity.this, "请输入原密码!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!userBean.getPsw().equals(etPassword.getText().toString())){
                    Toast.makeText(AddPasswordActivity.this, "原密码错误!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(etPassword2.getText().toString())) {
                    Toast.makeText(AddPasswordActivity.this, "新密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(etPassword3.getText().toString())) {
                    Toast.makeText(AddPasswordActivity.this, "请再次确认密码!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!etPassword3.getText().toString().equals(etPassword2.getText().toString())) {
                    Toast.makeText(AddPasswordActivity.this, "两次密码不一致!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    userBean.setName(etPhone.getText().toString());
                    userBean.setPsw(etPassword2.getText().toString());
                    DbSqliteHelper.getInstance(AddPasswordActivity.this).updateUser(userBean);
                    Toast.makeText(AddPasswordActivity.this, "密码修改成功!", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }
        }
    }
}
