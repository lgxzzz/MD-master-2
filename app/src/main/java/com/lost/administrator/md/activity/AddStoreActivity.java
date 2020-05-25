package com.lost.administrator.md.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.lost.administrator.md.R;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.Store;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

import static me.iwf.photopicker.PhotoPicker.REQUEST_CODE;

public class AddStoreActivity extends AppCompatActivity {

    @BindView(R.id.iv_back_share)
    ImageView ivBackShare;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.ev_one)
    Spinner evOne;
    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.ev_four)
    Spinner evFour;
    @BindView(R.id.ev_two)
    EditText evTwo;
    @BindView(R.id.ev_threee)
    EditText evThreee;
    @BindView(R.id.add_store)
    ImageView addStore;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.title)
    TextView title;
    private String types;
    TimePickerView pvTime;
    private Store store;
    ArrayList<String> photos = null;
    /**
     * 添加服装
     *
     * @param savedInstanceState
     */


    private String onetypes, twotypes;

    String[] twotype = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        ButterKnife.bind(this);
        store = (Store) getIntent().getSerializableExtra("DATA");
        String[] onetype = new String[]{"甜美", "优雅", "百搭", "淑女", "韩版", "民族", "欧美", "学院", "通勤", "中性", "嘻哈", "田园", "朋克", "职业装", "洛丽塔", "街头", "简约", "森系", "日系"};
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, onetype);  //创建一个数组适配器
        //设置下拉列表框的下拉选项样式
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        evOne.setAdapter(adapters);
        evOne.setSelection(0);
        evOne.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                evOne.setSelection(i);
                onetypes = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                evOne.setSelection(0);
            }
        });


        String[] ctype = new String[]{"男上装", "女上装", "男下装", "女下装"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        //设置下拉列表框的下拉选项样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spType.setAdapter(adapter);

        if (store != null) {
            title.setText("修改服装");
            evTwo.setText(store.getMoney());
            evThreee.setText(store.getprice());
            for (int i = 0; i < ctype.length; i++) {
                if (store.getType().equals(ctype[i])) {
                    spType.setSelection(i, true);
                    types = ctype[i];

                }
            }
            Glide.with(AddStoreActivity.this).load(store.getpicture()).into(addStore);

        } else {
            title.setText("添加服装");
            spType.setSelection(0);
        }
        spType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spType.setSelection(i);
                types = adapterView.getItemAtPosition(i).toString();
                if (types.substring(1,3).equals("上装")){
                    twotype = new String[]{"棉服", "毛衣", "开衫毛衣", "大衣", "马甲", "皮衣", "衬衫", "蕾丝衫", "雪纺衫", "T恤", "夹克", "卫衣", "西服", "风衣", "羽绒服", "冲锋衣", "POLO衫", "情侣衣", "短外套", "牛仔外套", "风衣"};
                } else {
                    twotype = new String[]{"西裤", "运动裤", "休闲裤", "牛仔裤", "羽绒裤", "工装裤", "五分裤", "九分裤", "阔腿裤", "小脚", "背带裤", "短裙", "长裙", "连体裤", "连衣裙", "衬衫裙"};

                }
                ArrayAdapter<String> adapterss = new ArrayAdapter<String>(AddStoreActivity.this, android.R.layout.simple_spinner_item, twotype);  //创建一个数组适配器
                //设置下拉列表框的下拉选项样式
                adapterss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //加载适配器
                evFour.setAdapter(adapterss);
                evFour.setSelection(0);
                evFour.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        evFour.setSelection(i);
                        twotypes = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        evFour.setSelection(0);
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spType.setSelection(0);
            }
        });


        if (TextUtils.isEmpty(types)) {
            twotype = new String[]{"棉服", "毛衣", "开衫毛衣", "大衣", "马甲", "皮衣", "衬衫", "蕾丝衫", "雪纺衫", "T恤", "夹克", "卫衣", "西服", "风衣", "羽绒服", "冲锋衣", "POLO衫", "情侣衣", "短外套", "牛仔外套", "风衣"};

        } else {
            if (types.contains("上装")) {
                twotype = new String[]{"棉服", "毛衣", "开衫毛衣", "大衣", "马甲", "皮衣", "衬衫", "蕾丝衫", "雪纺衫", "T恤", "夹克", "卫衣", "西服", "风衣", "羽绒服", "冲锋衣", "POLO衫", "情侣衣", "短外套", "牛仔外套", "风衣"};
            } else {
                twotype = new String[]{"西裤", "运动裤", "休闲裤", "牛仔裤", "羽绒裤", "工装裤", "五分裤", "九分裤", "阔腿裤", "小脚", "背带裤", "短裙", "长裙", "连体裤", "连衣裙", "衬衫裙"};

            }
        }


        ArrayAdapter<String> adapterss = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, twotype);  //创建一个数组适配器
        //设置下拉列表框的下拉选项样式
        adapterss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        evFour.setAdapter(adapterss);
        evFour.setSelection(0);
        evFour.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                evFour.setSelection(i);
                twotypes = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                evFour.setSelection(0);
            }
        });
    }


    private String getTimes(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @OnClick({R.id.iv_back_share, R.id.save, R.id.add_store})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_share:
                finish();
                break;
            case R.id.add_store:
                PhotoPicker.builder()
                        .setPhotoCount(1)//可选择图片数量
                        .setShowCamera(true)//是否显示拍照按钮
                        .setShowGif(true)//是否显示动态图
                        .setPreviewEnabled(true)//是否可以预览
                        .start(AddStoreActivity.this, REQUEST_CODE);
                break;

            case R.id.save:
                //存储到数据库中
                if (TextUtils.isEmpty(onetypes)) {
                    Toast.makeText(AddStoreActivity.this, "服装风格不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(types)) {
                    Toast.makeText(AddStoreActivity.this, "服装类型不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(evTwo.getText().toString())) {
                    Toast.makeText(AddStoreActivity.this, "服装名称不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(evThreee.getText().toString())) {
                    Toast.makeText(AddStoreActivity.this, "服装介绍不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(twotypes)) {
                    Toast.makeText(AddStoreActivity.this, "服装种类不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photos == null) {
                    Toast.makeText(AddStoreActivity.this, "服装图片不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Store stores = new Store();
                stores.setName(onetypes);
                stores.setType(types);
                stores.setMoney(evTwo.getText().toString());
                stores.setprice(evThreee.getText().toString());
                stores.setpicture(photos.get(0));
                stores.setIndex("1");
                stores.setBianhao(twotypes);
                if (store != null) {
                    stores.setId(store.getId());
                    DbSqliteHelper.getInstance(AddStoreActivity.this).updateStore(stores);
                    Toast.makeText(AddStoreActivity.this, "更新成功!", Toast.LENGTH_SHORT).show();
                } else {
                    DbSqliteHelper.getInstance(AddStoreActivity.this).saveStore(stores);
                    Toast.makeText(AddStoreActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent();

                intent.putExtra("data", "key"); //将计算的值回传回去
                // 通过intent对象返回结果，必须要调用一个setResult方法，
                // setResult(888, data);第一个参数表示结果返回码，一般只要大于1就可以
                setResult(666, intent);
                finish();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                Glide.with(AddStoreActivity.this).load(photos.get(0)).into(addStore);


            }
        }

    }

}
