package test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoqu on 16-5-24.
 */
public class MenuArray {
    public List<Menu> menus;

    public MenuArray() {
        init();
    }

    public void init() {
        menus = new ArrayList<>();
    }

    public void insertMenu(Menu menu) {
        if (menu.fid == -1) {
            menus.add(menu);
            return;
        }
        insertChild(this.menus,menu);
    }

    private void insertChild(List<Menu> menuList, Menu menu) {
        menuList.stream().forEach(item -> {
            if (item.id == menu.fid) {
                item.addChild(menu);
                return;
            }
            if (item.menuList!=null) {
                insertChild(item.menuList, menu);
            }
        });
    }

    @Override
    public String toString() {
        return menus.toString();
    }
}
