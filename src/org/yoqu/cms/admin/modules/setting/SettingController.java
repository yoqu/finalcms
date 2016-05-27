package org.yoqu.cms.admin.modules.setting;

import org.yoqu.cms.admin.modules.menu.MenuInvoke;
import org.yoqu.cms.core.aop.SiteTitle;
import org.yoqu.cms.core.config.FinalBaseController;
import org.yoqu.cms.core.config.SystemVariable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yoqu
 * @date 2016/5/16 0016
 * @description
 */
public class SettingController extends FinalBaseController {

    @SiteTitle("内容设置")
    public void node() {

    }

    @SiteTitle("其他设置")
    public void other() {

    }

    @SiteTitle("系统变量设置")
    public void systemVariable() {
        if (getPara() == null) {
            HashMap<String, String> variables = SystemVariable.getMap();

            setAttr("variables", variables);
            render("/admin/setting/variable.html");
            return;
        }
        switch (getPara()){
            case "save":
                Map<String, String[]> result = getParaMap();
                if(SystemVariableInvoke.getInstance().saveVariable(result)){

                    redirect("/admin/setting/systemVariable");
                }
                else{
                    renderJSONError("保存失败");
                }
                break;
            default:
                renderNotFound();
                break;
        }


    }
}
