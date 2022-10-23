package net.yiliufeng.windows_control;

import android.app.Application;
import android.util.Log;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

public class MyApplication extends Application {
    public DbManager.DaoConfig daoConfig=new DbManager.DaoConfig()
            .setDbName("myapp.db")
            .setDbVersion(2)
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) throws DbException {
                    db.getDatabase().enableWriteAheadLogging();
                }
            })
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) throws DbException {

                }
            })
            .setTableCreateListener(new DbManager.TableCreateListener() {
                @Override
                public void onTableCreated(DbManager db, TableEntity<?> table) {
                    Log.i("JAVA", "onTableCreated：" + table.getName());
                }
            });



    @Override
    public void onCreate() {
        x.Ext.init(this);
        x.Ext.setDebug(false);
        super.onCreate();
    }
}
