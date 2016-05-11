package org.yoqu.cms.core.admin.modules.menu;

import com.jfinal.core.*;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.model.Menu;
import org.yoqu.cms.core.model.MenuType;
import org.yoqu.cms.core.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author yoqu
 * @date 2016/5/9 0009
 * @description
 */
public class MenuController extends Controller {

    public void index() {
        if (getPara() != null) {
            if (!StringUtils.isNumbervalue(getPara())) {
                render(Constant.RENDER_ACCESS_NOT_FOUND);
            } else {
                MenuType menuType=MenuType.dao.findById(getParaToInt());
                if(menuType==null){
                    render(Constant.RENDER_ACCESS_NOT_FOUND);
                    return;
                }
                setAttr("menuType", menuType);
                List<Menu> menuList = Menu.dao.find("select * form menu where type=?",menuType.getId());
                if (menuList == null) {
                    setAttr("site_title", "无数据");
                }else{
                    setAttr("site_title", menuType.getName());
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

    public void create() {
        if (getPara() == null) {
            render(Constant.RENDER_ACCESS_NOT_FOUND);
        }
        if (!StringUtils.isNumbervalue(getPara())) {
            render(Constant.RENDER_ACCESS_NOT_FOUND);
        } else {
            Menu menu = Menu.dao.findById(getParaToInt());
            setAttr("menu", menu);
            render("/admin/menu/create.html");
        }
    }

}
