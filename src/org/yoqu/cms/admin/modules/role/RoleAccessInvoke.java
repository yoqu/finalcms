package org.yoqu.cms.admin.modules.role;

import org.yoqu.cms.core.model.RoleAccess;
import org.yoqu.cms.core.util.FinalProxy;

import java.util.List;

/**
 * file name : RoleAccessInvoke.java
 * created at : 21:39 2016-08-06.
 * created by 970655147 [970655147]
 */
public class RoleAccessInvoke {

    // single
    private volatile static RoleAccessInvoke singleton;
    public static RoleAccessInvoke getInstance() {
        if (singleton == null) {
            synchronized (RoleAccessInvoke.class) {
                if(singleton == null) {
                    singleton = (RoleAccessInvoke) new FinalProxy().createProxy(RoleAccessInvoke.class);
                }
            }
        }
        return singleton;
    }

    /**
     * @Name: findRoleAccessByRoleId
     * @Description: 根据给定的roleId查询到其可以访问的页面的相关数据[role数量有限, 请注意缓存相关数据]
     * @Param [roleId]
     * @Return java.util.List<org.yoqu.cms.core.model.RoleAccess>
     * @Create at 2016-08-06 22:35 by '970655147'
     */
    public List<RoleAccess> findRoleAccessByRoleId(int roleId) {
        return RoleAccess.dao.find("select * " +
                "from role_access " +
                "where is_delete=0 and rId=?", roleId);
    }


}
