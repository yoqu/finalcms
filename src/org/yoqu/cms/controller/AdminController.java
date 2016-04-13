package org.yoqu.cms.controller;

import com.jfinal.core.Controller;

/**
 * Created by yoqu on 2016/4/13 0013.
 */
public class AdminController extends Controller {

    public void index(){
        System.out.println("test");
        renderText("123");
        //render("/admin/login.html");
    }

    public void login(){
        System.out.println("login");
        render("/admin/login.html");
    }
    public void register(){

    }

    public void dologin(){

    }
    public void doRegister(){

    }
}
