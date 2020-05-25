package com.lost.administrator.md.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lost.administrator.md.R;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.Store;
import com.lost.administrator.md.utils.CommonAdapter;
import com.lost.administrator.md.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.iv_back_search)
    ImageView ivBackSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_del_search)
    ImageView ivDelSearch;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.lv_history_search)
    ListView lvHistorySearch;
    @BindView(R.id.ll_history_search)
    LinearLayout llHistorySearch;
    @BindView(R.id.activity_search)
    LinearLayout activitySearch;
    /*@BindView(R.id.tv_area)
    TextView tvArea;*/
    private CommonAdapter<Store> adapter_store;
    private List<Store> list_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        /*if (etSearch.length()>=1){
            ivDelSearch.setVisibility(View.VISIBLE);
        }*/

        list_store=new ArrayList<>();
        //查询数据
        adapter_store = new CommonAdapter<Store>(SearchActivity.this, list_store, R.layout.layout_goods_item) {
            @Override
            public void convert(ViewHolder holder, Store store) {
                holder.setText(R.id.tv_product_name, store.getName())
                        .setTexts(R.id.tv_area,store.getBianhao())
                        .setText(R.id.tv_product_pirce, "￥" +store.getprice())
                        .setTexts(R.id.tv_cuxiao_pirce, "￥" +store.getMoney())
                        .setImgUrl(R.id.iv_product_picture, store.getpicture());
            }
        };
        lvHistorySearch.setAdapter(adapter_store);
        lvHistorySearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, SearchDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DATA", list_store.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    @OnClick({R.id.iv_back_search, R.id.iv_search,R.id.iv_del_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_search:
                finish();
                break;
            case R.id.iv_search:
                String search=etSearch.getText().toString();
                if (search.isEmpty()) {
                    Toast.makeText(SearchActivity.this, "内容不能为空!", Toast.LENGTH_SHORT).show();
                }else{
                    list_store = DbSqliteHelper.getInstance(SearchActivity.this).getAllStore();
                   List<Store> list=new ArrayList<>();

                    if (list_store.size()>0){
                       for (int i=0;i<list_store.size();i++){
                           if (search.equals(list_store.get(i).getName())||search.equals(list_store.get(i).getBianhao())||search.equals(list_store.get(i).getType())){
                               list.add(list_store.get(i));
                           }

                       }

                   }

                    adapter_store.updateAll(list);
                    break;
                }
            case R.id.iv_del_search:
                etSearch.setText("");
                break;
        }
    }
}
