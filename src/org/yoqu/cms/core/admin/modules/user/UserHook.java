package org.yoqu.cms.core.admin.modules.user;

import org.yoqu.cms.core.aop.InvokeAfter;
import org.yoqu.cms.core.aop.InvokeBefore;
import org.yoqu.cms.core.model.User;
import org.yoqu.cms.core.util.FinalProxy;

import java.util.List;

/**
 * @author yoqu
 * @date 2016/4/29 0029
 * @description
 */

public class UserHook {
    private volatile static UserHook userHook;

    public static UserHook getInstance() {
        if (userHook == null) {
            synchronized (UserHook.class) {
                userHook = (UserHook) new FinalProxy().createProxy(UserHook.class);
            }
        }
        return userHook;
    }

    @InvokeBefore("SaveUser_Before")
    public void saveUser(User user) {
        user.save();
    }

    @InvokeBefore("FindUser_Before")
    @InvokeAfter("FindUser_After")
    public List<User> finduserByNamePasswordOrName(String... parameters) {
        if (parameters.length == 2) {
            return User.dao.find("select * from user where name=? and password=? and is_delete=0", parameters);
        } else if (parameters.length == 1) {
            return User.dao.find("select * from user where name=? and is_delete=0", parameters);
        } else if (parameters.length == 0) {
            return User.dao.find("select * from user where is_delete=0");
        } else return null;
    }

}
