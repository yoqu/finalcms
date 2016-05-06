package test;

import org.yoqu.cms.core.admin.modules.dashboard.DashBoardController;
import org.yoqu.cms.core.util.SiteTitle;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoqu on 2016/4/20 0020.
 */
public class javaTest {

    public static void main(String[] args) {
        TestTwo testTwo =new TestTwo();
        testTwo.init(() -> {System.out.println("hasd");});
        testTwo.sayHello();
        List<String> stringas=new ArrayList<>();
        stringas.add("c");
        stringas.add("b");
        stringas.add("a");
        stringas.add("g");
        stringas.add("e");
        System.out.println(stringas.stream()
        );
    }
}

class TestTwo{
    private Hello test;

    public void init(Hello test){
        this.test=test;
    }
    public void sayHello(){
        test.sayHello();
    }
}

class Test{
    public void sayHello(String hello){
        System.out.println(hello);
    }
}
interface Hello{
    public void sayHello( );

}