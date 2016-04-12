package org.yoqu.blog.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
//import org.yoqu.blog.model.Blog;

public class HelloController extends Controller
{
    public void index(){
//        Page<Blog> pages =Blog.dao.pagination();
//        System.out.println(pages);
//        setAttr("blogs",pages);
        render("/blog.html");
    }
}
