package org.yoqu.cms.core.admincontroller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import org.json.JSONException;
import org.yoqu.cms.core.intercepter.AuthManager;
import org.yoqu.cms.core.model.Role;
import org.yoqu.cms.core.model.User;
import org.yoqu.cms.core.util.JSONUtil;
import org.yoqu.cms.core.util.SiteTitle;

import java.util.Date;
import java.util.List;

/**
 * Created by yoqu on 2016/4/20 0020.
 */
public class PeopleController extends Controller {

    @SiteTitle("用户管理")
    public void index() {
        int page = getPara("page") != null ? Integer.parseInt(getPara("page")) : 1;
        Page<User> users = User.dao.findUserByPage(page);
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
        User user = User.dao.findById(id);
        setAttr("user", user);
        render("/admin/people/edit.html");
    }

    @Before(POST.class)
    public void doCreate() {
        User user = getModel(User.class);
        user.setCreateDate(new Date());
        user.setPassword(AuthManager.encryptionString(user.getPassword()));
        user.setLastDate(user.getCreateDate());
        user.setIsDelete(0);
        user.save();
        render("/admin/people/people.html");
    }

    @Before(POST.class)
    public void doUpdate() {
        Integer uid = getParaToInt("id");
        User user = User.dao.findById(uid);
        user.setCreateDate(new Date());
        user.setPassword(AuthManager.encryptionString(user.getPassword()));
        user.setLastDate(user.getCreateDate());
        user.setIsDelete(0);
        user.save();
        render("/admin/people/people.html");
    }

    @Before(POST.class)
    public void doDelete() {
        Integer uid = getParaToInt("id");
        try {
            User.dao.softDelete(uid);
            renderJson(JSONUtil.writeSuccess().toString());
        } catch (Exception ex) {
            try {
                renderJson(JSONUtil.writeFailInformation("删除用户出错").toString());
            } catch (JSONException e) {
                renderText("{result:'json error'}");
            }
        }
    }
}
