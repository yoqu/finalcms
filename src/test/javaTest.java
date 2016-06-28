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
        ExecutorService excetors= Executors.newSingleThreadExecutor();
        List<Runnable> tasks=new ArrayList<>();
        tasks.add(new Runnable() {
            @Override
            public void run() {
                System.out.println("task1");
            }
        });
        tasks.add(new Runnable() {
            @Override
            public void run() {
                System.out.println("task2");
            }
        });
        tasks.add(new Runnable() {
            @Override
            public void run() {
                System.out.println("task3");
            }
        });
        for (Runnable task :tasks){
            Future future=excetors.submit(task);
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        excetors.shutdown();
    }



}
