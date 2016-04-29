package org.yoqu.cms.core.config;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.yoqu.cms.core.admin.modules.user.UserHook;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.admin.config.InjectManager;
import org.yoqu.cms.core.model.User;
import org.yoqu.cms.core.util.FinalProxy;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * Created by yoqu on 2016/4/19 0019.
 */
public class AuthManagerInterceptor implements Interceptor {

    /**
     * 对用户进行登陆验证
     *
     * @param controller
     * @return
     */
    public static boolean webServiceAuth(Controller controller) {
        if (controller.getSessionAttr(Constant.ONLINE_USER) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * MD5加密处理字符串
     *
     * @param str 待加密字符串
     * @return 加密后的结果
     */
    public static String encryptionString(String str) {
        try {
            java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
            alg.update(str.getBytes());
            byte[] lastStr = alg.digest();
            return byte2hex(lastStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String byte2hex(byte[] b) // 二行制转字符串
    {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;
            if (n < b.length - 1) hs = hs + ":";
        }
        return hs.toUpperCase();
    }

    /**
     * 对user进行认证，认证通过返回认证后的user对象，否则返回null;
     *
     * @param user
     * @return
     */
    public static User userAuth(User user) {
        String newPassword = encryptionString(user.getPassword());
        List<User> users = UserHook.getInstance().finduserByNamePasswordOrName(user.getName(), newPassword);
        if (users.size() == 1) {
            user = users.get(0);
            user.setLastDate(new Date());//用户认证通过修改用户最后一次登录时间
            user.update();
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void intercept(Invocation inv) {
        String uri = inv.getActionKey();
        if (uri.startsWith("/admin")) {//compare user access back-end page or front page.
            HookConstant.injectManager.injectAnnotation(inv.getMethod(), inv.getController());//inject
            HookConstant.injectManager.injectCommonVariable(inv.getController());
            if (webServiceAuth(inv.getController())) {
                inv.invoke();
                HookConstant.injectManager.injectPersonalVariable(inv.getController());//inject user variable into page.
            } else {
                inv.getController().redirect("/admin/user/login");
            }
        } else {
            inv.invoke();
        }
    }

}
