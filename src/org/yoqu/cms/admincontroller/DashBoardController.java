package org.yoqu.cms.admincontroller;

import com.jfinal.core.Controller;

/**
 * Created by yoqu on 2016/4/18 0018.
 */
public class DashBoardController extends Controller {
    public void index(){
        render("/admin/dashboard.html");
    }
}
