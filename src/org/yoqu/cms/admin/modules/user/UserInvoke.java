package org.yoqu.cms.admin.modules.user;

import org.yoqu.cms.core.config.SystemVariable;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import org.yoqu.cms.core.aop.InvokeAfter;
import org.yoqu.cms.core.aop.InvokeBefore;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.model.User;
import org.yoqu.cms.core.util.FinalProxy;

import java.util.List;

/**
 * @author yoqu
 * @date 2016/4/29 0029
 * @description
 */

public class UserInvoke {
    private volatile static UserInvoke userHook;

    public static UserInvoke getInstance() {
        if (userHook == null) {
            synchronized (UserInvoke.class) {
                userHook = (UserInvoke) new FinalProxy().createProxy(UserInvoke.class);
            }
        }
        return userHook;
    }

    @InvokeBefore("SaveUser_Before")
    public void saveUser(User user) {
        user.save();
    }

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
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

    @InvokeBefore("FindUserByPage_Before")
    public Page<User> findUserByPage(int pageNumber) {
        return User.dao.paginate(pageNumber, Integer.parseInt(SystemVariable.get(Constant.PAGE_SIZE).trim()), "select *", "from user where is_delete=0 order by createDate desc");
    }

    @InvokeBefore("SoftDelete")
    public boolean softDelete(int uid) {
        if (Db.update("update user set is_delete=1 where id=?", uid) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @InvokeBefore("FindUserById_Before")
    @InvokeAfter("FindUserById_After")
    public User findUserById(int id){
        return User.dao.findById(id);
    }
}
