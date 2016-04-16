package momo.cn.edu.fjnu.videoclient.pojo;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 签到记录
 * Created by GaoFei on 2016/4/5.
 */
@Table(name = "SignRecord")
public class SignRecord {
    @Column(name = "id", isId = true, autoGen = false)
    private int id;
    @Column(name = "uid")
    private int uid;
    @Column(name = "sid")
    private int sid;
    @Column(name = "signTime")
    private int signTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getSignTime() {
        return signTime;
    }

    public void setSignTime(int signTime) {
        this.signTime = signTime;
    }

    @Override
    public String toString() {
        return "SignRecord{" +
                "id=" + id +
                ", uid=" + uid +
                ", sid=" + sid +
                ", signTime=" + signTime +
                '}';
    }
}
