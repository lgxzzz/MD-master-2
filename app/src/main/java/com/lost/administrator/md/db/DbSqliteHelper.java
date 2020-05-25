package com.lost.administrator.md.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.lost.administrator.md.entity.CollectionBean;
import com.lost.administrator.md.entity.PingJiaBean;
import com.lost.administrator.md.entity.ShangjiaInfo;
import com.lost.administrator.md.entity.Store;
import com.lost.administrator.md.entity.StoreDingDan;
import com.lost.administrator.md.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


/**
 * 数据库工具类
 */
public class DbSqliteHelper extends SQLiteOpenHelper {

    public DbSqliteHelper(Context ctx) {
        super(ctx, "MT", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //用户数据表
        db.execSQL("CREATE TABLE if not exists user(id integer PRIMARY KEY autoincrement,"
                + "name integer, pass text,type text,url text)");
        //用户收藏数据表
        db.execSQL("CREATE TABLE if not exists usercollect(id integer PRIMARY KEY autoincrement,"
                + "phone integer,name  text, type text,money text, start text, ends text,bianhao text)");//area-原bianhao
        //商品信息数据表
        db.execSQL("CREATE TABLE if not exists store(id integer PRIMARY KEY autoincrement,"
                + "name  text, type text,money text, start text, ends text,area text,indexs text)");//area-原bianhao
        //商品订单信息数据表
        db.execSQL("CREATE TABLE if not exists storedingdan(id integer PRIMARY KEY autoincrement,"
                + "user text,name  text, type text,money text, start text, ends text,bianhao text,time text)");//area-原bianhao
        //评价信息数据表
        db.execSQL("CREATE TABLE if not exists pingjia(id integer PRIMARY KEY autoincrement,"
                + "user  text, goodName text,comment text, ratbar integer, time text)");

        //商家信息数据表
        db.execSQL("CREATE TABLE if not exists shangjia(id integer PRIMARY KEY autoincrement,"
                + " shopName text,name text, phone text, adress text,picture text,type text,createtime text,isExamine text)");

        //商品收藏信息数据表
        db.execSQL("CREATE TABLE if not exists collection(id integer PRIMARY KEY autoincrement,"
                + "user text,name  text, type text,money text, start text, ends text,area text)");//area-原bianhao
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static DbSqliteHelper dbManager;


    public static DbSqliteHelper getInstance(Context ctx) {
        if (dbManager == null) {
            synchronized (DbSqliteHelper.class) {
                if (dbManager == null) {
                    dbManager = new DbSqliteHelper(ctx);
                }
            }
        }
        return dbManager;
    }

    public boolean saveUser(User bean) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            //注册之前先查询是否重复注册
            Cursor cursor = db.rawQuery("SELECT * FROM user WHERE name = ?", new String[]{bean.getName()});
            boolean hasUser = false;
            if (cursor.moveToNext()) {
                hasUser = true;
            }
            cursor.close();
            if (hasUser) {
                return true;
            }
            //如果不重复则注册
            db.execSQL("INSERT INTO user(name , pass,type,url) " +
                    "VALUES ('" + bean.getName()
                    + "', '" + bean.getPsw()
                    + "', '" + bean.getType()
                    + "', '" + bean.getUrl()
                    + "')");
        }
        return false;
    }


    /**
     * 查找用户（登录操作）
     *
     * @return 用户
     */
    public User findUser(String[] args) {
        User bean = new User();
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            Cursor cursor = db.query("user", null, "name = ?", args, null, null, null);
            if (cursor.moveToNext()) {
                bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bean.setName(cursor.getString(cursor.getColumnIndex("name")));
                bean.setPsw(cursor.getString(cursor.getColumnIndex("pass")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));
                bean.setUrl(cursor.getString(cursor.getColumnIndex("url")));

            }
            cursor.close();

        }
        return bean;
    }

    /**
     * 更新用户信息
     *
     * @param record
     */
    public void updateUser(User record) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", record.getId());
        contentValues.put("name", record.getName());
        contentValues.put("pass", record.getPsw());
        contentValues.put("type", record.getType());
        contentValues.put("url", record.getUrl());
        if (db != null) {
            db.update("user", contentValues, "id = ?", new String[]{record.getId()+""});
        }
    }


    /**
     * 删除用户
     *
     * @param id
     */
    public void deleteUser(int id) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("DELETE FROM user WHERE id = " + id);
        }
    }
    //存储优惠劵数据
    public void saveStore(Store bean) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO store(name, type,money, start, ends,area,indexs) " +
                "VALUES ('" + bean.getName()
                + "', '" + bean.getType()
                + "', '" + bean.getMoney()
                + "', '" + bean.getprice()
                + "', '" + bean.getpicture()
                + "', '" + bean.getBianhao()
                + "', '" + bean.getIndex()
                + "')");


    }
    /**
     * 获取所有优惠劵数据
     *
     * @return
     */
    public List<Store> getAllStore() {
        List<Store> records = new ArrayList<Store>();
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM store ", null);
            while (cursor.moveToNext()) {
                Store bean = new Store();
                bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bean.setName(cursor.getString(cursor.getColumnIndex("name")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));

                bean.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                bean.setprice(cursor.getString(cursor.getColumnIndex("start")));
                bean.setpicture(cursor.getString(cursor.getColumnIndex("ends")));
                bean.setBianhao(cursor.getString(cursor.getColumnIndex("area")));//area-原bianhao
                bean.setIndex(cursor.getString(cursor.getColumnIndex("indexs")));

                records.add(bean);
            }
            cursor.close();
        }
        return records;
    }

    /**
     * 查询商品数据
     *
     * @return
     */
    public List<Store> getStore(String type) {
        List<Store> jiQiBeanList = new ArrayList<Store>();
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            //查询记录
            String[] selectioinArgs = {"%"+type+"%"};//注意：这里没有单引号
            String sql = "SELECT * FROM store" + " where type like ?";
            Cursor cursor = db.rawQuery(sql, selectioinArgs);
            while (cursor.moveToNext()) {
                Store bean = new Store();
                bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bean.setName(cursor.getString(cursor.getColumnIndex("name")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));
                bean.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                bean.setprice(cursor.getString(cursor.getColumnIndex("start")));
                bean.setpicture(cursor.getString(cursor.getColumnIndex("ends")));
                bean.setBianhao(cursor.getString(cursor.getColumnIndex("area")));//area-原bianhao
                bean.setIndex(cursor.getString(cursor.getColumnIndex("indexs")));

                jiQiBeanList.add(bean);
            }
            cursor.close();



        }
        return jiQiBeanList;
    }


    public List<Store> getStores(String name) {
        List<Store> jiQiBeanList = new ArrayList<Store>();
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            //查询记录
            String[] selectioinArgs = {"%"+name+"%"};//注意：这里没有单引号
            String sql = "SELECT * FROM store" + " where name like ?";
            Cursor cursor = db.rawQuery(sql, selectioinArgs);
            while (cursor.moveToNext()) {
                Store bean = new Store();
                bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bean.setName(cursor.getString(cursor.getColumnIndex("name")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));
                bean.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                bean.setprice(cursor.getString(cursor.getColumnIndex("start")));
                bean.setpicture(cursor.getString(cursor.getColumnIndex("ends")));
                bean.setBianhao(cursor.getString(cursor.getColumnIndex("area")));//area-原bianhao
                bean.setIndex(cursor.getString(cursor.getColumnIndex("indexs")));

                jiQiBeanList.add(bean);
            }
            cursor.close();



        }
        return jiQiBeanList;
    }
    /**
     * 更新优惠劵信息
     *
     * @param store
     */
    public void updateStore(Store store) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", store.getId());
        contentValues.put("name", store.getName());
        contentValues.put("type", store.getType());
        contentValues.put("money", store.getMoney());
        contentValues.put("start", store.getprice());
        contentValues.put("ends", store.getpicture());
        contentValues.put("area", store.getBianhao());//area-原bianhao
        contentValues.put("indexs", store.getIndex());
        if (db != null) {
            db.update("store", contentValues, "id = ?", new String[]{store.getId()+""});
        }
    }





    /**
     * 删除优惠劵信息
     *
     * @param id
     */
    public void deleteStore(int id) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("DELETE FROM store WHERE id = " + id);
        }
    }
    //存储评价数据
    public void savePingJia (PingJiaBean bean) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO pingjia(user, goodName,comment, ratbar, time) " +
                "VALUES ('" + bean.getUser()
                + "', '" + bean.getGoodName()
                + "', '" + bean.getComment()
                + "', '" + bean.getRatbar()
                + "', '" + bean.getTime()
                + "')");


    }
    /**
     * 获取所有评价数据
     *
     * @return
     */
    public List<PingJiaBean> getAllPingJia(String user) {
        List<PingJiaBean> records = new ArrayList<PingJiaBean>();
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM pingjia WHERE user = ?", new String[]{user});
            while (cursor.moveToNext()) {
                PingJiaBean bean = new PingJiaBean();
                bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bean.setUser(cursor.getString(cursor.getColumnIndex("user")));
                bean.setGoodName(cursor.getString(cursor.getColumnIndex("goodName")));
                bean.setComment(cursor.getString(cursor.getColumnIndex("comment")));
                bean.setRatbar(cursor.getString(cursor.getColumnIndex("ratbar")));
                bean.setTime(cursor.getString(cursor.getColumnIndex("time")));


                records.add(bean);
            }
            cursor.close();
        }
        return records;
    }


    /**
     * 更新优惠劵订单信息
     *
     * @param store
     */
    public void updateOrder(Store store) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", store.getId());
        contentValues.put("name", store.getName());
        contentValues.put("type", store.getType());
        contentValues.put("money", store.getMoney());
        contentValues.put("start", store.getprice());
        contentValues.put("ends", store.getpicture());
        contentValues.put("bianhao", store.getBianhao());//area-原bianhao

        if (db != null) {
            db.update("dingdan", contentValues, "id = ?", new String[]{store.getId()+""});
        }
    }





    /**
     * 删除优惠劵订单信息
     *
     * @param id
     */
    public void deleteOrder(int id) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("DELETE FROM dingdan WHERE id = " + id);
        }
    }


    //存储商家数据
    public void saveShangjia (ShangjiaInfo bean) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO shangjia(shopName ,name , phone , adress ,picture ,type ,createtime,isExamine) " +
                "VALUES ('" + bean.getShopName()
                + "', '" + bean.getName()
                + "', '" + bean.getPhone()
                + "', '" + bean.getAdress()
                + "', '" + bean.getPicture()
                + "', '" + bean.getType()
                + "', '" + bean.getCreatetime()
                + "', '" + bean.getIsExamine()
                + "')");


    }
    /**
     * 获取所有商家数据
     *
     * @return
     */
    public List<ShangjiaInfo> getAllShangjia() {
        List<ShangjiaInfo> records = new ArrayList<ShangjiaInfo>();
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM shangjia ", null);
            while (cursor.moveToNext()) {
                ShangjiaInfo bean = new ShangjiaInfo();
                bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bean.setShopName(cursor.getString(cursor.getColumnIndex("shopName")));
                bean.setName(cursor.getString(cursor.getColumnIndex("name")));

                bean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                bean.setAdress(cursor.getString(cursor.getColumnIndex("adress")));
                bean.setPicture(cursor.getString(cursor.getColumnIndex("picture")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));
                bean.setCreatetime(cursor.getString(cursor.getColumnIndex("createtime")));
                bean.setIsExamine(cursor.getString(cursor.getColumnIndex("isExamine")));


                records.add(bean);
            }
            cursor.close();
        }
        return records;
    }
    /**
     * 更新商家信息
     *
     * @param store
     */
    public void updateShangjia(ShangjiaInfo store) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", store.getId());
        contentValues.put("shopName", store.getShopName());
        contentValues.put("name", store.getName());
        contentValues.put("phone", store.getPhone());
        contentValues.put("adress", store.getAdress());
        contentValues.put("picture", store.getPicture());
        contentValues.put("type", store.getType());
        contentValues.put("createtime", store.getCreatetime());
        contentValues.put("isExamine", store.getIsExamine());
        if (db != null) {
            db.update("shangjia", contentValues, "id = ?", new String[]{store.getId()+""});
        }
    }



    //存储商品订单数据
    public void saveStoreDingDan(StoreDingDan bean) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO storedingdan(user,name, type,money, start, ends,bianhao,time) " +
                "VALUES ('" + bean.getUser()
                + "', '" + bean.getName()
                + "', '" + bean.getType()
                + "', '" + bean.getMoney()
                + "', '" + bean.getprice()
                + "', '" + bean.getpicture()
                + "', '" + bean.getBianhao()//area-原bianhao
                + "', '" + bean.getTime()
                + "')");


    }
    /**
     * 获取所有商品订单数据
     *
     * @return
     */
    public List<StoreDingDan> getAllStoreDingDan(String user) {
        List<StoreDingDan> records = new ArrayList<StoreDingDan>();
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            Cursor cursor =null;
            if (TextUtils.isEmpty(user)){
                cursor = db.rawQuery("SELECT * FROM storedingdan", null);

            }else {
                cursor = db.rawQuery("SELECT * FROM storedingdan WHERE user = ?", new String[]{user});

            }
            while (cursor.moveToNext()) {
                StoreDingDan bean = new StoreDingDan();
                bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bean.setUser(cursor.getString(cursor.getColumnIndex("user")));

                bean.setName(cursor.getString(cursor.getColumnIndex("name")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));

                bean.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                bean.setprice(cursor.getString(cursor.getColumnIndex("start")));
                bean.setpicture(cursor.getString(cursor.getColumnIndex("ends")));
                bean.setBianhao(cursor.getString(cursor.getColumnIndex("bianhao")));//area-原bianhao
                bean.setTime(cursor.getString(cursor.getColumnIndex("time")));

                records.add(bean);
            }
            cursor.close();
        }
        return records;
    }
    //存储收藏商品数据
    public boolean saveCollection(CollectionBean bean) {
        SQLiteDatabase db = getWritableDatabase();


        if (db != null) {
            //注册之前先查询是否重复注册
            Cursor cursor = db.rawQuery("SELECT * FROM collection WHERE name = ? AND user = ?", new String[]{bean.getName(),bean.getUser()});
            boolean hasUser = false;
            if (cursor.moveToNext()) {
                hasUser = true;
            }
            cursor.close();
            if (hasUser) {
                return true;
            }
            //如果不重复则注册
            db.execSQL("INSERT INTO collection(user,name, type,money, start, ends,area) " +
                    "VALUES ('" + bean.getUser()
                    + "', '" + bean.getName()
                    + "', '" + bean.getType()
                    + "', '" + bean.getMoney()
                    + "', '" + bean.getprice()
                    + "', '" + bean.getpicture()
                    + "', '" + bean.getBianhao()
                    + "')");
        }
        return false;

    }
    /**
     * 获取所有收藏商品数据
     *
     * @return
     */
    public List<CollectionBean> getAllCollection() {
        List<CollectionBean> records = new ArrayList<CollectionBean>();
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM collection ", null);
            while (cursor.moveToNext()) {
                CollectionBean bean = new CollectionBean();
                bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bean.setUser(cursor.getString(cursor.getColumnIndex("user")));

                bean.setName(cursor.getString(cursor.getColumnIndex("name")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));

                bean.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                bean.setprice(cursor.getString(cursor.getColumnIndex("start")));
                bean.setpicture(cursor.getString(cursor.getColumnIndex("ends")));
                bean.setBianhao(cursor.getString(cursor.getColumnIndex("area")));//area-原bianhao

                records.add(bean);
            }
            cursor.close();
        }
        return records;
    }


    public CollectionBean findColl(String name,String user) {
        CollectionBean bean = new CollectionBean();
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM collection WHERE name = ? AND user = ?", new String[]{name,user});
            if (cursor.moveToNext()) {
                bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bean.setUser(cursor.getString(cursor.getColumnIndex("user")));

                bean.setName(cursor.getString(cursor.getColumnIndex("name")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));

                bean.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                bean.setprice(cursor.getString(cursor.getColumnIndex("start")));
                bean.setpicture(cursor.getString(cursor.getColumnIndex("ends")));
                bean.setBianhao(cursor.getString(cursor.getColumnIndex("area")));//area-原bianhao

            }
            cursor.close();

        }
        return bean;
    }

    /**
     * 删除收藏信息
     *
     * @param id
     */
    public void deleteCollection(int id) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("DELETE FROM collection WHERE id = " + id);
        }
    }
}
