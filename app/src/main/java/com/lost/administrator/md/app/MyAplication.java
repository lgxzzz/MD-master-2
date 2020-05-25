package com.lost.administrator.md.app;

import android.app.Activity;
import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.lost.administrator.md.R;
import com.lost.administrator.md.activity.AddStoreActivity;
import com.lost.administrator.md.db.DbSqliteHelper;
import com.lost.administrator.md.entity.Store;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created
 */

public class MyAplication extends Application {
    private static ArrayList<Activity> list = new ArrayList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);

//        //存放初始数据
//        List<Store> list_store = DbSqliteHelper.getInstance(getApplicationContext()).getAllStore();
//        if (list_store.size()==0){
//            Store store1=new Store();
//            store1.setName("狗不理");
//            store1.setType("美食劵");
//            store1.setMoney("30");
//            store1.setBianhao("ZH123498555");
//            store1.setprice("2020-2-10");
//            store1.setpicture("2020-3-10");
//
//            DbSqliteHelper.getInstance(getApplicationContext()).saveStore(store1);
//
//            Store store2=new Store();
//            store2.setName("永辉");
//            store2.setType("百货劵");
//            store2.setMoney("30");
//            store2.setBianhao("ZH1234545555");
//            store2.setprice("2020-2-10");
//            store2.setpicture("2020-3-10");
//            DbSqliteHelper.getInstance(getApplicationContext()).saveStore(store2);
//
//            Store store3=new Store();
//            store3.setName("果果家");
//            store3.setType("水果劵");
//            store3.setMoney("30");
//            store3.setBianhao("ZH123454555");
//            store3.setprice("2020-2-10");
//            store3.setpicture("2020-3-10");
//            DbSqliteHelper.getInstance(getApplicationContext()).saveStore(store3);
//
//            Store store4=new Store();
//            store4.setName("必胜客");
//            store4.setType("热饮劵");
//            store4.setMoney("10");
//            store4.setBianhao("ZH123445555");
//            store4.setpicture("2020-2-10");
//            store4.setpicture("2020-3-10");
//            DbSqliteHelper.getInstance(getApplicationContext()).saveStore(store4);
//
//
//            Store store5=new Store();
//            store5.setName("大鸭梨");
//            store5.setType("优选劵");
//            store5.setMoney("30");
//            store5.setBianhao("ZH12345555");
//            store5.setprice("2020-2-10");
//            store5.setpicture("2020-3-10");
//            DbSqliteHelper.getInstance(getApplicationContext()).saveStore(store5);
//
//            Store store6=new Store();
//            store6.setName("闪送");
//            store6.setType("专送劵");
//            store6.setMoney("30");
//            store6.setBianhao("ZH1234545555");
//            store6.setpicture("2020-2-10");
//            store6.setpicture("2020-3-10");
//            DbSqliteHelper.getInstance(getApplicationContext()).saveStore(store6);
//
//            Store store7=new Store();
//            store7.setName("北京烤鸭");
//            store7.setType("蔬菜劵");
//            store7.setMoney("30");
//            store7.setBianhao("ZH23445555");
//            store7.setprice("2020-2-10");
//            store7.setpicture("2020-3-10");
//            DbSqliteHelper.getInstance(getApplicationContext()).saveStore(store7);
//
//            Store store8=new Store();
//            store8.setName("麻花");
//            store8.setType("小吃劵");
//            store8.setMoney("30");
//            store8.setBianhao("ZH12345234");
//            store8.setprice("2020-2-10");
//            store8.setpicture("2020-3-10");
//            DbSqliteHelper.getInstance(getApplicationContext()).saveStore(store8);
//
 //       }

    }

    /**
     * 向Activity列表中添加Activity对象
     */
    public static void addActivity(Activity a) {
        list.add(a);
    }

    /**
     * 关闭Activity列表中的所有Activity
     */
    public static void finishActivity() {
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        // 杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
