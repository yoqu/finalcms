package test;

import org.yoqu.cms.core.admincontroller.DashBoardController;
import org.yoqu.cms.core.util.SiteTitle;

import java.lang.reflect.Method;

/**
 * Created by yoqu on 2016/4/20 0020.
 */
public class javaTest {

    public static void main(String[] args) {
        Class clazz = DashBoardController.class;
        try {
            Method method = clazz.getMethod("index", null);
            if (method.isAnnotationPresent(SiteTitle.class)) {
                SiteTitle siteTitle = method.getAnnotation(SiteTitle.class);
                System.out.println(siteTitle.value());
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
