package org.yoqu.cms.admin.modules.menu;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.JsonKit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yoqu.cms.core.aop.SiteTitle;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.config.FinalBaseController;
import org.yoqu.cms.core.model.Menu;
import org.yoqu.cms.core.model.MenuType;
import org.yoqu.cms.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yoqu
 * @date 2016/5/9 0009
 * @description
 */
public class MenuController extends FinalBaseController {

    public void index() {
        if (getPara() != null) {
            if (!StringUtils.isNumbervalue(getPara())) {
                renderNotFound();
            } else {
                MenuType menuType = MenuType.dao.findById(getParaToInt());
                if (menuType == null) {
                    renderNotFound();
                    return;
                }
                setAttr("menuType", menuType);
                List<Menu> menuList =Menu.dao.findAllMenuByType(menuType.getId());
                if (menuList == null) {
                    setAttr("site_title", "无数据");
                } else {
                    setAttr("site_title", menuType.getName());
                    setAttr("menuList", Menu.dao.sortMenu(menuList));
                }
                render("/admin/menu/menu.html");
            }
        } else {
            List<MenuType> menuTypeList = MenuType.dao.find("select * from menu_type where is_delete=0");
            setAttr("site_title", "菜单管理");
            setAttr("menuTypeList", menuTypeList);
            render("/admin/menu/menuList.html");
        }
    }

    public void loadMenus() {
        MenuType menuType = MenuType.dao.findById(getParaToInt("menuType"));
        List<Menu> menuList = Menu.dao.find("select * from menu where is_delete=0 and type=?", menuType.getId());
        List<Menu> newMenus = Menu.dao.sortMenu(menuList);
        JSONArray array = new JSONArray();
        newMenus.stream().forEach(item -> {
            try {
                JSONObject obj = new JSONObject(JsonKit.toJson(item));
                array.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        renderJson(array.toString());
    }

    @SiteTitle("菜单创建")
    public void create() {
        render("/admin/menu/create.html");
    }

    @Before(POST.class)
    public void doCreate() {
        MenuType menuType = getModel(MenuType.class);
        menuType.setIsDelete(0);
        if (menuType.save()) {
            redirect("/admin/menu");
        } else {
            renderJSONError("保存失败");
        }
    }

    public void doDelete() {
        if (isParaIsNumberBlank()) {
            renderJSONError("必须要带ID进来哟");
            return;
        }
        int id = getParaToInt();
        if (MenuType.dao.deleteById(id)) {
            redirect("/admin/menu");
        } else {
            renderJSONError();
        }
    }

    public void edit() {
        if (isParaIsNumberBlank())
            return;
        int menuTypId = getParaToInt();
        MenuType menuType = MenuType.dao.findByIdFirst(menuTypId);
        setAttr("menuType", menuType);
        setPageTitle(menuType.getName());
        render("/admin/menu/menuEdit.html");
    }

    public void doEdit() {
        MenuType menuType = getModel(MenuType.class);
        if (menuType.getId() != null) {
            menuType.update();
            redirect("/admin/menu");
        } else {
            renderJSONError("更新失败");
        }
    }
    /***************************菜单item操作start*************************/

    /**
     * 保存菜单排序..
     */
    public void sortMenuItem() {
        String jsonData = getPara("data");
        try {
            JSONArray arrays = new JSONArray(jsonData);
            MenuInvoke.getInstance().sortMenuItem(arrays, -1);
        } catch (JSONException e) {
            renderJSONError("json数据格式不正确.");
        }
        String id = getPara("menuType");
        renderJSONSuccess();
    }

    @ActionKey("/admin/menu/item/create")
    @SiteTitle("添加菜单项")
    public void itemCreate(){
        List<MenuType> menuTypes =MenuType.dao.findAllMenuType();
        setAttr("menuTypes",menuTypes);
        render("/admin/menu/menuItemCreate.html");
    }

    @ActionKey("/admin/menu/item/doCreate")
    @Before(POST.class)
    public void itemDoCreate(){
        Menu menu = getModel(Menu.class);
        menu.setIsDelete(0);
        menu.setFid(-1);
        if (MenuInvoke.getInstance().saveMenuItem(menu)){
            redirect("/admin/menu/"+menu.getType());
        }
        else{
            renderJSONError("保存失败");
        }
    }

    @ActionKey("/admin/menu/item/edit")
    public void itemEdit() {
        if (isParaIsNumberBlank())
            return;
        int id = getParaToInt();
        Menu menu = Menu.dao.findById(id);
        List<MenuType> menuTypes = MenuType.dao.findAllMenuType();
        setPageTitle(menu.getName());
        setAttr("menu", menu);
        setAttr("menuTypes", menuTypes);
        render("/admin/menu/menuItemEdit.html");
    }

    @ActionKey("/admin/menu/item/doEdit")
    @Before(POST.class)
    public void doItemEdit() {
        Menu menu = getModel(Menu.class);
        if (menu.getId() != null) {
            MenuInvoke.getInstance().updateMenuItem(menu);
            redirect("/admin/menu/" + menu.getType());
        }
    }
    /***************************菜单item操作end*************************/
}
