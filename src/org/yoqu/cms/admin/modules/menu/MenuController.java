package org.yoqu.cms.admin.modules.menu;

import com.jfinal.kit.JsonKit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
                List<Menu> menuList = Menu.dao.find("select * from menu where is_delete=0 and type=?", menuType.getId());
                if (menuList == null) {
                    setAttr("site_title", "无数据");
                } else {
                    setAttr("site_title", menuType.getName());
                    setAttr("menuList", menuList);
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
        List<Menu> newMenus = sortMenu(menuList);
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
//        try {
//            renderJSONObject(Constant.SUCCESS,newMenus);
//        } catch (JSONException e) {
//            renderJSONError();
//        }
    }


    public void create() {
        if (getPara() == null) {
            renderNotFound();
        }
        if (!StringUtils.isNumbervalue(getPara())) {
            renderNotFound();
        } else {
            Menu menu = Menu.dao.findById(getParaToInt());
            setAttr("menu", menu);
            render("/admin/menu/create.html");
        }
    }

    //menu排序
    private List<Menu> sortMenu(List<Menu> menus) {
        List<Menu> newMenus = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            menus.get(i).setMenuListToAttr();
            if (menus.get(i).getFid() == -1) {
                newMenus.add(menus.get(i));
                continue;
            }
            newMenus = insertChild(newMenus, menus.get(i));
        }
        return newMenus;
    }

    private List<Menu> insertChild(List<Menu> menuList, Menu menu) {
        for (int i = 0; i < menuList.size(); i++) {
            if (menuList.get(i).getId() == menu.getFid()) {
                menuList.get(i).addChild(menu);
                menuList.get(i).setMenuListToAttr();
                return menuList;
            }
            if (menuList.get(i).getMenuList() != null) {
                menuList.get(i).setMenuList(insertChild(menuList.get(i).getMenuList(), menu));
                return menuList;
            }
        }
        return null;
    }

}
