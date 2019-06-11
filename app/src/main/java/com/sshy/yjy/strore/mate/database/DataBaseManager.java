package com.sshy.yjy.strore.mate.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by 周正尧 on 2018/3/15 0015.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */

public class DataBaseManager {

    private DaoSession mDaoSession = null;
    private UserProfileDao mDao = null;
    private ReleaseOpenHelper helper = null;
    private Context context = null;

    private DataBaseManager(){

    }

    public DataBaseManager init(Context context){
        initDao(context);
        this.context = context;
        return this;
    }


    private static final class Holder{
        private static final DataBaseManager INSTANCE = new DataBaseManager();
    }

    public static DataBaseManager getIntance(){
        return Holder.INSTANCE;
    }

    private void initDao(Context context){
        helper = new ReleaseOpenHelper(context,"fast_ec.db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getDao(){
        return mDao;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase db = helper.getReadableDatabase();
        return db;
    }

    /**
     * 查询用户列表
     */
    public List<UserProfile> queryUserList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserProfileDao userProfileDao = daoSession.getUserProfileDao();
        QueryBuilder<UserProfile> qb = userProfileDao.queryBuilder();
        List<UserProfile> list = qb.list();
        return list;
    }

}
