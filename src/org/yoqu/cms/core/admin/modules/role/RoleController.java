package org.yoqu.cms.core.admin.modules.role;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.model.Role;
import org.yoqu.cms.core.model.Url;
import org.yoqu.cms.core.util.JSONUtil;
import org.yoqu.cms.core.util.SiteTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yoqu
 * @date 2016/5/3 0003
 * @description
 */
public class RoleController extends Controller {

    @SiteTitle("权限管理")
    public void index() {
        int page = getPara("page") != null ? Integer.parseInt(getPara("page")) : 1;
//        List<Url> urls=RoleInvoke.getInstance().findAllRolePermission();
        List<Record> modules = RoleInvoke.getInstance().findAllRoleModule();
        List<Role> roles = RoleInvoke.getInstance().findAllRole();
        setAttr("roles", roles);
        setAttr("modules", modules);
        render("/admin/role/role.html");
    }


    public void getSelectRole() throws JSONException {
        List<Record> records = RoleInvoke.getInstance().findSelectRole();
        JSONObject obj = new JSONObject();
        JSONArray array=new JSONArray();
        for (Record record : records){
            JSONObject jsonrecord=new JSONObject();
            jsonrecord.put("method",record.getStr("method"));
            jsonrecord.put("module",record.getStr("module"));
            jsonrecord.put("name",record.getStr("name"));
            jsonrecord.put("rid",record.getStr("rid"));
            jsonrecord.put("url",record.getStr("url"));
            array.put(jsonrecord);
        }
        try {
            obj.put("data", array);
            obj.put(Constant.RESULT, Constant.SUCCESS);
        } catch (JSONException e) {
            e.printStackTrace();
            obj.put("result", Constant.FAIL);
        }
        renderJson(obj.toString());
    }
}
