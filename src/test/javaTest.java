package test;

/**
 * Created by yoqu on 2016/4/20 0020.
 */
public class javaTest {

    public static void main(String[] args) {
        String target = new String("/item/100/edit");
        StringBuffer map = new StringBuffer("/item/${id}/edit");
        int index=map.indexOf("$");
        int last=map.lastIndexOf("}");
        if(last!=-1){
            map.replace(index,last+1,"\\d*");
            System.out.println(map.toString());
            System.out.println(target.matches(map.toString()));
        }
//        map.substring(index,last)
//        String result = map.delete(index,last+1).toString();
//        System.out.println(result);
        System.out.println("index = "+index+"last = "+last);
    }
}
