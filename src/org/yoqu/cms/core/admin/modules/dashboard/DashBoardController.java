package org.yoqu.cms.core.admin.modules.dashboard;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import org.yoqu.cms.core.util.SiteTitle;

/**
 * Created by yoqu on 2016/4/18 0018.
 */
public class DashBoardController extends Controller {
    @SiteTitle("主页面板")
    public void index() {
        render("/admin/dashboard.html");
    }

    //配置admin页面
    @ActionKey("/admin")
    public void admin() {
        redirect("/admin/dashboard");
    }

    @SiteTitle("没有权限")
    @ActionKey("/admin/nopermission")
    public void nopermission() {
        System.out.println("test");
        render("/admin/noPermission.html");
    }

}
