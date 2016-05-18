package org.yoqu.cms.core.config;

import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;

/**
 * Created by yoqu on 2016/4/21 0021.
 */
public class StartFinalCMS {
    private static int START_PORT = 80;//项目启动端口

    //项目启动
    public static void main(String[] args) {
        JFinal.start(detectWebAppDir(), START_PORT, "/", 5);
    }

    private static String detectWebAppDir() {
        String rootClassPath = PathKit.getRootClassPath();
        String[] temp = null;
        if (rootClassPath.indexOf("\\WEB-INF\\") != -1)
            temp = rootClassPath.split("\\\\");
        else if (rootClassPath.indexOf("/WEB-INF/") != -1)
            temp = rootClassPath.split("/");
        else
            throw new RuntimeException("WEB-INF directory not found.");
        return temp[temp.length - 3];
    }
}
