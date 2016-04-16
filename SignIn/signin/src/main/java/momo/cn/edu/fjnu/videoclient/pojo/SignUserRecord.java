package momo.cn.edu.fjnu.videoclient.pojo;

/**
 * 用户签到记录表
 */
public class SignUserRecord {
    private int id;
    private int uid;
    private int sid;
    private int signTime;
    private String course;
    private int persons;
    private int startTime;
    private int endTime;
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
    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    public int getPersons() {
        return persons;
    }
    public void setPersons(int persons) {
        this.persons = persons;
    }
    public int getStartTime() {
        return startTime;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public int getEndTime() {
        return endTime;
    }
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    @Override
    public String toString() {
        return "SignUserRecord [id=" + id + ", uid=" + uid + ", sid=" + sid
                + ", signTime=" + signTime + ", course=" + course
                + ", persons=" + persons + ", startTime=" + startTime
                + ", endTime=" + endTime + "]";
    }


}
