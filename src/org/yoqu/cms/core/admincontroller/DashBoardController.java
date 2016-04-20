package org.yoqu.cms.core.admincontroller;

import com.jfinal.core.Controller;
import org.yoqu.cms.core.util.SiteTitle;

/**
 * Created by yoqu on 2016/4/18 0018.
 */
public class DashBoardController extends Controller {
    @SiteTitle("主页面板")
    public void index(){
        render("/admin/dashboard.html");
    }
}
