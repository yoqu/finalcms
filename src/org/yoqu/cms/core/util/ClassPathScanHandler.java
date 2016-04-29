package org.yoqu.cms.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by yoqu on 2016/4/22 0022.
 */
public class ClassPathScanHandler {

    /**
     * 扫描包
     *
     * @param basePackage 基础包
     * @param recursive   是否递归搜索子包
     * @return Set
     */
    public static Set<Class<?>> getPackageAllClasses(String basePackage, boolean recursive, Class annotationClass) {
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        String packageName = basePackage;
        if (packageName.endsWith(".")) {
            packageName = packageName
                    .substring(0, packageName.lastIndexOf('.'));
        }
        String package2Path = packageName.replace('.', '/');

        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(
                    package2Path);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    System.out.println(("扫描class...."));
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    doScanPackageClassesByFile(classes, packageName, filePath,
                            recursive,annotationClass);
                }
            }
        } catch (IOException e) {
            System.out.println("IOException error:"+e);
        }

        return classes;
    }

    /**
     * 以文件的方式扫描包下的所有Class文件
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    private static void doScanPackageClassesByFile(Set<Class<?>> classes,String packageName, String packagePath, boolean recursive,Class annotationClass) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        final boolean fileRecursive = recursive;
        File[] dirfiles = dir.listFiles();
        for (File file : dirfiles) {
            if (file.isDirectory()) {
                doScanPackageClassesByFile(classes, packageName + "."
                        + file.getName(), file.getAbsolutePath(), recursive,annotationClass);
            } else {
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {

                    if (annotationClass != null) {
                        if (isAnnotationClass(packageName + '.' + className,annotationClass)) {
                            classes.add(Thread.currentThread().getContextClassLoader()
                                    .loadClass(packageName + '.' + className));
                        }
                    } else {
                        classes.add(Thread.currentThread().getContextClassLoader()
                                .loadClass(packageName + '.' + className));
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("IOException error:"+e);
                }
            }
        }
    }

    private static boolean isAnnotationClass(String className,Class annotationClass) {
        try {
            Class entity = Class.forName(className);
            if (entity.isAnnotationPresent(annotationClass)) {
                return true;
            } else {
                return false;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


}
