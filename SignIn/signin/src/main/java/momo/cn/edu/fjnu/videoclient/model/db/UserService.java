package momo.cn.edu.fjnu.videoclient.model.db;

import momo.cn.edu.fjnu.videoclient.pojo.User;

/**
 * 对用户表进行增删该查
 * Created by GaoFei on 2016/3/25.
 */
public class UserService extends AppBeanService<User>{
    @Override
    public boolean isExist(User object) {
        User user = getObjectById(User.class, object.getId());
        return (user != null);
    }

}
