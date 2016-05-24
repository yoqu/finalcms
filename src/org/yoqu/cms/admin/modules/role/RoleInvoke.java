package org.yoqu.cms.admin.modules.role;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.yoqu.cms.core.config.SystemVariable;
import org.yoqu.cms.core.aop.InvokeAfter;
import org.yoqu.cms.core.aop.InvokeBefore;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.model.Role;
import org.yoqu.cms.core.model.RolePermission;
import org.yoqu.cms.core.util.FinalProxy;

import java.util.List;

/**
 * @author yoqu
 * @date 2016/5/3 0003
 * @description
 */
public class RoleInvoke {

    private volatile static RoleInvoke roleHook;

    public static RoleInvoke getInstance() {
        if (roleHook == null) {
            synchronized (RoleInvoke.class) {
                roleHook = (RoleInvoke) new FinalProxy().createProxy(RoleInvoke.class);
            }
        }
        return roleHook;
    }

    @InvokeBefore("FindUserByPage_Before")
    public Page<Role> findUserByPage(int pageNumber) {
        return Role.dao.paginate(pageNumber, Integer.parseInt(SystemVariable.get(Constant.PAGE_SIZE).trim()), "select *", "from role where is_delete=0 order by createDate desc");
    }


    public List<Role> findAllRole() {
        return Role.dao.find("select * " +
                "from role " +
                "where is_delete=0");
    }

    /**
     * 筛选每个模块和方法
     * @return
     */
    public List<Record> findAllRoleModule(){
        return Db.find("select module,GROUP_CONCAT(method) methods " +
                "from url  " +
                "where is_delete=0 " +
                "group by module");
    }

    /**
     * 返回数据列数据名 rid name method module url
     * @param id
     * @return
     */
    public List<Record> findRolePermissionById(int id) {
        return Db.find("select r.rid,role.name,r.module,r.method,u.url " +
                "from role_permission r inner join role on role.id=r.rid  " +
                "inner join url u on r.module=u.module and r.method=u.method " +
                "where role.id=? and role.is_delete=0 and r.is_delete=0 and u.is_delete=0", id);
    }

    public List<Record> findSelectRole(){
        return Db.find("select GROUP_CONCAT(r.rid) rid,GROUP_CONCAT(role.name) name,r.module,r.method,u.url " +
                "from role_permission r inner join role on role.id=r.rid " +
                "inner join url u on r.module=u.module and r.method=u.method  " +
                "where role.is_delete=0 and r.is_delete=0 and u.is_delete=0 " +
                "group by r.method");
    }

    @InvokeBefore("rebuildRolePermission_Before")
    @InvokeAfter("rebuildRolePermission_After")
    public void rebuildRolePermission(List<RolePermission> rolePermissions){
        if(Db.update("delete from role_permission")<0){
            throw new NullPointerException("role permission table not clear..");
        }
        for (RolePermission rolePermission : rolePermissions){
            rolePermission.save();
        }
    }

    public List<RolePermission> findAllRolePermission(){
        return RolePermission.dao.find("select * from role_permission where is_delete=0");
    }

    public List<RolePermission> findRolePermissionByUriRid(String uri,int rid){
        return RolePermission.dao.find("select r.* " +
                "from url u inner join role_permission r on u.module=r.module and u.method=r.method " +
                "where u.url=? and rid=?",uri,rid);
    }
}
