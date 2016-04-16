package momo.cn.edu.fjnu.videoclient.base;

import android.util.Log;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.BitmapAjaxCallback;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import momo.cn.edu.fjnu.androidutils.base.BaseApplication;
import momo.cn.edu.fjnu.videoclient.data.AppConst;

/**
 * 整个APP实例
 * Created by GaoFei on 2016/3/8.
 */
public class VideoApplication extends BaseApplication {
    public static DbManager mDBManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("VideoApplication", "VideoApplication实例化");
        initApp();
    }

    /**
     * 初始化APP
     */
    public void initApp() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initAquery();
        initDB();
    }

    /**
     * 初始化数据库
     */
    public void initDB() {
        DbManager.DaoConfig dbConfig = new DbManager.DaoConfig().setDbDir(new File(AppConst.DB_DIRECTORY))
                .setDbName(AppConst.DB_NAME).setDbVersion(AppConst.DB_VERSION)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        //db.getDatabase().enableWriteAheadLogging();
                    }
                }).setDbUpgradeListener(null);
        if (null == mDBManager)
            mDBManager = x.getDb(dbConfig);
    }

    /**
     * 初始化Aquery框架
     */
    public void initAquery(){
        //设置网络最大连接数
        AjaxCallback.setNetworkLimit(8);
        //设置缓存图标(<50px)的最大数量
        BitmapAjaxCallback.setIconCacheLimit(20);
        //设置可以在内存中缓存的最大图片数量
        BitmapAjaxCallback.setCacheLimit(50);
        //设置可以缓存的最大图片尺寸
        BitmapAjaxCallback.setPixelLimit(512 * 512);
        //设置图片最大缓存像素
        BitmapAjaxCallback.setMaxPixelLimit(2621440);
    }
}
