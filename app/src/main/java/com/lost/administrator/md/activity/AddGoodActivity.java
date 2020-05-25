package com.lost.administrator.md.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lost.administrator.md.R;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.EventBusBean;
import com.lost.administrator.md.entity.ShangjiaInfo;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

import static me.iwf.photopicker.PhotoPicker.REQUEST_CODE;

public class AddGoodActivity extends AppCompatActivity {
    /**
     * 添加服装
     */
    @BindView(R.id.iv_back_share)
    ImageView ivBackShare;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.ev_one)
    EditText evOne;
    @BindView(R.id.ev_two)
    EditText evTwo;
    @BindView(R.id.ev_threee)
    EditText evThree;
    @BindView(R.id.add_store)
    ImageView addStore;
    @BindView(R.id.save)
    Button save;

    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.ev_four)
    EditText evFour;
    @BindView(R.id.no_shenhe)
    Button noShenhe;
    @BindView(R.id.title)
    TextView title;

    private String shopname;
    private String types;
    ShangjiaInfo shangjiaInfo;
    ArrayList<String> photos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good);
        ButterKnife.bind(this);
        photos=new ArrayList<>();
        shangjiaInfo = (ShangjiaInfo) getIntent().getSerializableExtra("DATA");

        shopname = getIntent().getStringExtra("ShopName");
        String[] ctype = new String[]{"男装", "女装"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        //设置下拉列表框的下拉选项样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spType.setAdapter(adapter);

        if (shangjiaInfo != null) {
            title.setText("审核商家");
            evOne.setText(shangjiaInfo.getShopName());
            evTwo.setText(shangjiaInfo.getName());
            evThree.setText(shangjiaInfo.getPhone());
            evFour.setText(shangjiaInfo.getAdress());
            photos.add(shangjiaInfo.getPicture());
            Glide.with(AddGoodActivity.this).load(shangjiaInfo.getPicture()).into(addStore);
            for (int i = 0; i < ctype.length; i++) {
                if (shangjiaInfo.getType().equals(ctype[i])) {
                    spType.setSelection(i, true);
                    types = ctype[i];

                }
            }
            if (shangjiaInfo.getIsExamine().equals("1")) {
                //未审核的可以查看，修改
                evOne.setEnabled(false);
                evTwo.setEnabled(false);
                evThree.setEnabled(false);
                evFour.setEnabled(false);

                save.setText("通过");
                noShenhe.setText("不通过");
                save.setVisibility(View.VISIBLE);
                noShenhe.setVisibility(View.VISIBLE);
            } else {
                //审核通过的只能查看
                evOne.setEnabled(false);
                evTwo.setEnabled(false);
                evThree.setEnabled(false);
                evFour.setEnabled(false);
                save.setVisibility(View.GONE);
            }


        } else {

            save.setVisibility(View.VISIBLE);
            spType.setSelection(0);

        }

        addStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPicker.builder()
                        .setPhotoCount(1)//可选择图片数量
                        .setShowCamera(true)//是否显示拍照按钮
                        .setShowGif(true)//是否显示动态图
                        .setPreviewEnabled(true)//是否可以预览
                        .start(AddGoodActivity.this, REQUEST_CODE);
            }
        });
        spType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spType.setSelection(i);
                types = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spType.setSelection(0);
            }
        });
    }

    @OnClick({R.id.iv_back_share, R.id.save, R.id.no_shenhe})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_share:
                finish();
                break;
            case R.id.no_shenhe:
                ShangjiaInfo goodsInfo = new ShangjiaInfo();
                goodsInfo.setShopName(evOne.getText().toString());
                goodsInfo.setName(evTwo.getText().toString());
                goodsInfo.setPhone(evThree.getText().toString());
                goodsInfo.setType(types);
                goodsInfo.setAdress(evFour.getText().toString());
                goodsInfo.setPicture(shangjiaInfo.getPicture());
                goodsInfo.setIsExamine("2");
                goodsInfo.setCreatetime(longToDate());
                goodsInfo.setId(shangjiaInfo.getId());
                DbSqliteHelper.getInstance(AddGoodActivity.this).updateShangjia(goodsInfo);
                EventBus.getDefault().post(
                        new EventBusBean("2"));
                finish();

                break;
            case R.id.save:
                if (TextUtils.isEmpty(evOne.getText().toString())) {
                    Toast.makeText(AddGoodActivity.this, "商家名称不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(evTwo.getText().toString())) {
                    Toast.makeText(AddGoodActivity.this, "负责人不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(evThree.getText().toString())) {
                    Toast.makeText(AddGoodActivity.this, "联系方式不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(types)) {
                    Toast.makeText(AddGoodActivity.this, "经营类型不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(evFour.getText().toString())) {
                    Toast.makeText(AddGoodActivity.this, "经营地址不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photos==null) {
                    Toast.makeText(AddGoodActivity.this, "营业执照不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ShangjiaInfo goodsInfos = new ShangjiaInfo();

                goodsInfos.setShopName(evOne.getText().toString());
                goodsInfos.setName(evTwo.getText().toString());
                goodsInfos.setPhone(evThree.getText().toString());
                goodsInfos.setType(types);
                goodsInfos.setAdress(evFour.getText().toString());


                goodsInfos.setCreatetime(longToDate());
                if (shangjiaInfo != null) {
                    if (photos!=null){
                        goodsInfos.setPicture(photos.get(0));
                    }else {
                        goodsInfos.setPicture(shangjiaInfo.getPicture());
                    }

                    goodsInfos.setIsExamine("3");
                    goodsInfos.setId(shangjiaInfo.getId());
                    DbSqliteHelper.getInstance(AddGoodActivity.this).updateShangjia(goodsInfos);

                } else {
                    goodsInfos.setPicture(photos.get(0));
                    goodsInfos.setIsExamine("1");
                    DbSqliteHelper.getInstance(AddGoodActivity.this).saveShangjia(goodsInfos);

                }
                Toast.makeText(AddGoodActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(
                        new EventBusBean("2"));
                finish();
                break;
        }
    }

    public static String longToDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                Glide.with(AddGoodActivity.this).load(photos.get(0)).into(addStore);


            }
        }

    }
}
