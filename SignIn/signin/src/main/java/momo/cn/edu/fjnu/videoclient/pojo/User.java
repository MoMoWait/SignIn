package momo.cn.edu.fjnu.videoclient.pojo;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 用户表
 * Created by GaoFei on 2016/3/24.
 */
@Table(name = "User")
public class User {
    @Column(name = "id",isId = true, autoGen = false)
    private int id;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "password")
    private String password;
    @Column(name = "mail")
    private String mail;
    @Column(name = "type")
    private int type;
    @Column(name = "nick_name")
    private String nick_name;
    @Column(name = "head_photo")
    private String head_photo;
    @Column(name = "phone_number")
    private String phone_number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getHead_photo() {
        return head_photo;
    }

    public void setHead_photo(String head_photo) {
        this.head_photo = head_photo;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", type=" + type +
                ", nick_name='" + nick_name + '\'' +
                ", head_photo='" + head_photo + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
