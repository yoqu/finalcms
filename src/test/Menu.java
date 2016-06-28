package test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoqu on 16-5-24.
 */
public class Menu implements Serializable {
    public int id;
    public int fid;
    public String name;
    public List<Menu> menuList;

    public Menu() {

    }

    public Menu(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Menu(int id, String name, int fid) {
        this.id = id;
        this.name = name;
        this.fid = fid;
    }


    public void addChild(Menu menu) {
        if (menuList == null) {
            menuList = new ArrayList<>();
        }
        menuList.add(menu);
    }

    @Override
    public String toString() {
        return "id: " + id + "fid:" + fid + "list:" + menuList;
    }
}
