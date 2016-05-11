package org.yoqu.cms.core.admin.modules.people;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import org.json.JSONException;
import org.yoqu.cms.core.admin.modules.user.UserInvoke;
import org.yoqu.cms.core.config.AuthManagerInterceptor;
import org.yoqu.cms.core.config.FinalCMS;
import org.yoqu.cms.core.model.Role;
import org.yoqu.cms.core.model.User;
import org.yoqu.cms.core.util.JSONUtil;
import org.yoqu.cms.core.aop.SiteTitle;

import java.util.Date;
import java.util.List;

/**
 * Created by yoqu on 2016/4/20 0020.
 */
public class PeopleController extends FinalCMS {

    @SiteTitle("用户管理")
    public void index() {
        int page = getPara("page") != null ? Integer.parseInt(getPara("page")) : 1;
        Page<User> users = UserInvoke.getInstance().findUserByPage(page);
        setAttr("userList", users);
        render("/admin/people/people.html");
    }

    @SiteTitle("添加用户")
    public void create() {
        List<Role> roleList = Role.dao.findAllRole();
        setAttr("roleList", roleList);
        render("create.html");
    }

    @SiteTitle("修改用户")
    public void edit() {
        Integer id = getParaToInt("id");
        if (id == null) {
            render("/admin/404.html");
            return;
        }
        List<Role> roleList = Role.dao.findAllRole();
        setAttr("roleList", roleList);
        User user =UserInvoke.getInstance().findUserById(id);
        setAttr("user", user);
        render("/admin/people/edit.html");
    }

    @Before(POST.class)
    public void doCreate() {
        User user = getModel(User.class);
        user.setCreateDate(new Date());
        user.setPassword(AuthManagerInterceptor.encryptionString(user.getPassword()));
        user.setLastDate(user.getCreateDate());
        user.setIsDelete(0);
        UserInvoke.getInstance().saveUser(user);
        render("/admin/people/people.html");
    }

    @Before(POST.class)
    public void doUpdate() {
        Integer uid = getParaToInt("id");
        User user = User.dao.findById(uid);
        user.setCreateDate(new Date());
        user.setPassword(AuthManagerInterceptor.encryptionString(user.getPassword()));
        user.setLastDate(user.getCreateDate());
        user.setIsDelete(0);
        UserInvoke.getInstance().saveUser(user);
        render("/admin/people/people.html");
    }

    @Before(POST.class)
    public void doDelete() {
        Integer uid = getParaToInt("id");
        try {
            UserInvoke.getInstance().softDelete(uid);
            renderJson(JSONUtil.writeSuccess().toString());
        } catch (Exception ex) {
            try {
                renderJson(JSONUtil.writeFailInformation("删除用户出错").toString());
            } catch (JSONException e) {
                renderJSONError();
            }
        }
    }

    @Before(POST.class)
    public void validateField(){
        String username = getPara("name");

    }
}
