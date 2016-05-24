package org.yoqu.cms.admin.modules.user;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.ext.interceptor.POST;
import org.json.JSONException;
import org.yoqu.cms.core.aop.SiteTitle;
import org.yoqu.cms.core.config.AuthManagerInterceptor;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.config.FinalBaseController;
import org.yoqu.cms.core.model.Role;
import org.yoqu.cms.core.model.User;
import org.yoqu.cms.core.util.JSONUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by yoqu on 2016/4/13 0013.
 */

public class UserController extends FinalBaseController {

    public void index() {
        render("/admin/login.html");
    }

    @Clear
    public void login() {
        setPageTitle("登录");
        injectCommonVariable();
        render("/admin/login.html");
    }

    @Clear
    public void register() {
        setPageTitle("注册");
        injectCommonVariable();
        render("/admin/register.html");
    }

    @SiteTitle("我的资料")
    public void profile() {
        render("admin/profile.html");
    }

    //dologin operate.
    @Before(POST.class)
    @Clear
    public void doLogin() {
        User user = getModel(User.class);
        user = AuthManagerInterceptor.userAuth(user);//user 登录认证
        if (user != null) {
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

    @Before(POST.class)
    @Clear
    public void doRegister() {
        User user = getModel(User.class);
        List<User> users = UserInvoke.getInstance().finduserByNamePasswordOrName(user.getName());
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
        user.setRid(role.getId());
        if (user.save()) {
            try {
                renderJson(JSONUtil.writeSuccess());
            } catch (JSONException e) {
                JSONUtil.writeJSONError(this);
            }
        }
    }

    public void doLogout() {
        setSessionAttr(Constant.ONLINE_USER, null);
        redirect("/admin/user/login");
    }


}
