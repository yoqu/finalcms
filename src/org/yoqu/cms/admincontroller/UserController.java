package org.yoqu.cms.admincontroller;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import org.json.JSONException;
import org.yoqu.cms.Constant.Constant;
import org.yoqu.cms.core.AuthManager;
import org.yoqu.cms.core.JSONUtil;
import org.yoqu.cms.model.Role;
import org.yoqu.cms.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by yoqu on 2016/4/13 0013.
 */
public class UserController extends Controller {

    public void index() {
        render("/admin/login.html");
    }

    @Clear
    public void login() {
        render("/admin/login.html");
    }

    @Clear
    public void register() {
        render("/admin/register.html");
    }

    //dologin operate.
    @Before(POST.class)
    @Clear
    public void doLogin() {
        User user = getModel(User.class);
        List<User> users = User.dao.finduserByNamePasswordOrName(user.getName(), user.getPassword());
        if (users.size() == 1) {
            user = users.get(0);
            user.setPassword("");
            getSession().setAttribute(Constant.ONLINE_USER, user);
            try {
                renderJson(JSONUtil.writeSuccess());
                setCookie(Constant.ONLINE_USER, user.getId().toString(), 100000);
                redirect("/admin/dashboard");
            } catch (JSONException e) {
                JSONUtil.writeJSONError(this);
                e.printStackTrace();
            }
        } else {
            try {
                renderJson(JSONUtil.writeFail());
            } catch (JSONException e) {
                renderText("JSon error");
                e.printStackTrace();
            }
        }
    }

    @Clear
    public void doRegister() {
        User user = getModel(User.class);
        List<User> users = User.dao.finduserByNamePasswordOrName(user.getName());
        if (users.size() == 0) {
            try {
                renderJson(JSONUtil.writeFailInformation("User name be registed.").toString());
            } catch (JSONException e) {
                JSONUtil.writeJSONError(this);
            }
            return;
        }
        user.setCreateDate(new Date());
        user.setLastDate(new Date());
        Role role = Role.dao.findAdminRole();
        user.setRole(role.getId());
        if (user.save()) {
            try {
                renderJson(JSONUtil.writeSuccess());
            } catch (JSONException e) {
                JSONUtil.writeJSONError(this);
            }
        }
    }

}
