package test;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by yoqu on 2016/4/20 0020.
 */
public class javaTest {

    public static void main(String[] args) {
        try {
            Object menuclass=Class.forName(Menu.class.getName()).newInstance();
            if(menuclass instanceof MenuArray){
                System.out.println("true.");
            }else{
                System.out.println("false.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



}
