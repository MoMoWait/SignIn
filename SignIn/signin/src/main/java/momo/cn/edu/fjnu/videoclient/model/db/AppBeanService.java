package momo.cn.edu.fjnu.videoclient.model.db;

import android.util.Log;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import momo.cn.edu.fjnu.androidutils.base.BaseBeanService;
import momo.cn.edu.fjnu.videoclient.base.VideoApplication;

/**
 * 数据库的增删改查，这里使用泛型方法
 * Created by GaoFei on 2016/3/25.
 */
public abstract class AppBeanService<T> implements BaseBeanService<T>{
    private final String TAG = AppBeanService.class.getSimpleName();
    @Override
    public void save(T object) {
        try {
            VideoApplication.mDBManager.save(object);
        } catch (DbException e) {
            Log.i(TAG, "存储对象发生异常：" + e);
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T object) {
        try {
            VideoApplication.mDBManager.delete(object);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(T object) {
        try {
            VideoApplication.mDBManager.update(object);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public  List<T> getAll(Class<T> tClass) {
       List<T> lists = new ArrayList<>();
        try {
            lists = VideoApplication.mDBManager.findAll(tClass);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return lists;
    }

    @Override
    public T getObjectById(Class<T> tClass, Object id) {
        T object = null;
        try {
            object = VideoApplication.mDBManager.findById(tClass, id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public abstract boolean isExist(T object) ;

    @Override
    public void saveAll(List<T> objects) {
        try {
            VideoApplication.mDBManager.save(objects);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateAll(List<T> objects) {
        try {
            VideoApplication.mDBManager.update(objects);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveOrUpdateAll(List<T> objects) {
        try {
            VideoApplication.mDBManager.saveOrUpdate(objects);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
