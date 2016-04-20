package org.yoqu.cms.core.util;

/**
 * Created by yoqu on 2016/4/20 0020.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * mysql语法 sql语句拼接工具简类
 * 使用场景：需要快速书写sql语句时用到，主要使用的是mysql中的sql语法
 * 该版本的select语句只支持单表查询，不支持多表查询，待更新
 * where 语句中关联多个字段默认使用And关键字,暂不可更改，待更新
 * @author 小川
 * @date 2015-12-7
 * @version 0.5
 *
 */
@SuppressWarnings("ALL")
public class SqlHandle {
    /**
     * 排序操作符：顺序 "ASC","DESC"
     */
    public static final String[] SORTS ={"ASC","DESC"};
    /**
     * 数据库操作符：顺序 "select","insert","update","delete"
     */
    public static final String[] OPERATES={"select","insert","update","delete"};
    /**
     * sql语句的String类型
     */
    private StringBuffer sql=new StringBuffer();;
    /**
     * 要操作的字段,用在update insert中的Set之后,字段名称和值进行对应
     */
    private Map<String, Object> operatefileds=new HashMap<String, Object>();

    /**
     * 要操作的值 ,同于insert语句中的values,不需要跟字段名称,只需要值就OK了
     */
    private ArrayList<Object> operatevalues=new ArrayList<Object>();
    /**
     * select语句中用于select 后面的字段名称
     */
    private ArrayList<String> fields = new ArrayList<String>();

    /**
     * 条件集合,用于where语句后面 形式例如 field1=value1
     */
    private ArrayList<String> conditions =new ArrayList<String>();
    /**
     * 操作符,使用select update delete insert者四种操作符
     */
    private String operate="";
    /**
     * 操作表 ,要操作的表
     */
    private String table="";
    /**
     * 限制 要限制的长度
     */
    private String limit="";
    /**
     * 排序规则,定制排序规则
     */
    private String order="";

    public SqlHandle(String operate,String table){
        this.operate=operate;
        this.table=table;
    }

    /**
     * 默认构造方法使用select语句操作
     */
    public SqlHandle(){
        this.operate=OPERATES[0];
    }
    /**
     * 初始化构造数据库操作,默认使用select查询
     * @param operate
     */
    public SqlHandle(String table){
        this.table=table;
        this.operate=OPERATES[0];
    }

    public SqlHandle OPERATE(String operate){
        this.operate=operate;
        return this;
    }
    /**
     * 主要应用update和insert的数据库操作添加sql
     * @param propername
     * @param propervalue
     * @return
     */
    public SqlHandle OPERATEFILED(String propername,Object propervalue){
        operatefileds.put(propername, propervalue);
        return this;
    }

    public SqlHandle OPERATEFILED(Map<String, Object> fileds){
        operatefileds.putAll(fileds);
        return this;
    }

    public SqlHandle OPERATEFILED(Object propervalue){
        operatevalues.add(propervalue);
        return this;
    }

    /**
     * 主要应用于select语句中，用于添加抬头字段
     * @param filed
     * @return
     */
    public SqlHandle FIELD(String field){
        this.fields.add(field);
        return this;
    }

    /**
     * 添加字段并可以给字段加别名
     * @param field
     * @param alias
     * @return
     */
    public SqlHandle FIELD(String field,String alias){
        this.fields.add(field+" as "+alias);
        return this;
    }
    /**
     * 添加字段
     * @param table
     * @return
     */
    public SqlHandle TABLE(String table){
        this.table=table;
        return this;
    }

    public SqlHandle CONDITION(String field,String operator,Object value){
        //value.getClass().getTypeName();
        String conditionStr=field+" "+operator+" ";
        //如果是String类型，需要加上单引号。
        conditionStr+=filterValue(value);
        this.conditions.add(conditionStr);
        return this;
    }

    /**
     * 一种条件场景 where id in (1,2,3) or where id not in (1,2,3)等
     * @param field
     * @param operator
     * @param values
     * @return
     */
    public SqlHandle CONDITION(String field,String operator,Serializable... values){
        String conditionStr=field+" "+operator+" (";
        if(values.length>0){
            for(int i=0;i<values.length-1;i++){
                conditionStr+=filterValue(values[i])+",";
            }
            conditionStr+=filterValue(values[values.length-1])+")";
        }
        this.conditions.add(conditionStr);
        return this;
    }
    /**
     * 过滤值方法，如果是String类型添加单引号，不是直接返回原值
     * @param value
     * @return
     */
    private String filterValue(Object value){
        if(value instanceof String){
            return "'"+value+"'";
        }
        else if(value instanceof Date){
            return "'"+value+"'";
        }
        else{
            return value.toString();
        }
    }

    public SqlHandle ORDERBY(String order,String field){
        order=" "+field+" "+order;
        return this;
    }

    public SqlHandle LIMIT(int start,int length){
        limit="  "+start+","+length;
        return this;
    }
    /**
     * 重写toString方法,返回sql结果
     */
    public String toString(){
        sql.setLength(0);
        switch (operate) {
            case "select":
                return selectToSql();
            case "insert":
                return insertToSql();
            case "update":
                return updateToSql();
            case "delete":
                return deleteToSql();
            default:
                return null;
        }
    }

    /**
     * 添加字段
     * 例子：filed1,field2
     */
    private void addfiled(){
        if(fields.size()>0){
            //批量加字段
            for (String filed : this.fields) {
                //最后一个字段不加逗号
                if(!filed.equals(fields.get(fields.size()-1))){
                    sql=sql.append(" "+filed+",");
                }
                else{
                    sql=sql.append(" "+filed);
                }
            }
        }else{
            sql=sql.append(" *");
        }
    }

    /**
     * 添加条件
     * 例子：where condition1<condition2 and condition3>condition4
     */
    private void addcondition(){
        if(conditions.size()>0){
            sql=sql.append(" where");
            for (String condition : conditions) {
                if(!condition.equals(conditions.get(conditions.size()-1))){
                    sql.append(" "+condition+" and ");
                }else{
                    sql.append(" "+condition);
                }
            }
        }
    }

    /**
     * 添加排序
     * 例子 order by field DESC
     */
    private void addorder(){
        if(!order.isEmpty()){
            sql.append(" order by " +order);
        }
    }

    /**
     * 添加限制
     * 例子 limit 0,10
     */
    private void addlimit(){
        if(!limit.isEmpty()){
            sql.append(" limit "+limit);
        }
    }

    /**
     * 添加SET语句到sql变量中
     * 例子 SET key=value,key2=value2
     */
    private void addkeyvalue(){
        if(!operatefileds.isEmpty() ){
            sql=sql.append(" SET");
            for(String key :operatefileds.keySet()){
                Object value=operatefileds.get(key);
                sql=sql.append(" "+key+" = "+filterValue(value)+",");
            }
            //删除最后一个逗号
            sql=sql.deleteCharAt(sql.length()-1);
        }
    }
    /**
     * select语句转sql方法
     * @return 返回转好的字符串
     */
    private String selectToSql(){
        sql=sql.append(OPERATES[0]);
        //字段处理，没有字段默认使用*
        addfiled();
        //表处理，暂时单表
        sql=sql.append(" from "+table);
        //where条件处理,有处理，无不处理,默认使用And连接符。。
        addcondition();
        //排序语句
        addorder();
        //限制条目
        addlimit();
        return sql.toString();
    }

    /**
     * insert语句转sql放阿飞
     * @return 返回转好的字符串
     */
    private String insertToSql(){
        sql=sql.append(OPERATES[1]);
        sql=sql.append(" "+table);
        //如果opratefileds和operatevalues只能取一个
        //operatefields注入sql语句的键值映射，operatevalues只注入值，不注入键
        if(!operatefileds.isEmpty() && operatevalues.isEmpty()){
            addkeyvalue();
        }
        else if(!operatevalues.isEmpty() && operatefileds.isEmpty()){
            sql=sql.append(" values(");
            for (Object value : operatevalues) {
                sql=sql.append(" "+filterValue(value)+",");
            }
            //删除最后一个逗号并添加括号括上
            sql=sql.deleteCharAt(sql.length()-1).append(")");
        }
        return sql.toString();
    }

    private String updateToSql(){
        sql=sql.append(OPERATES[2]);
        sql=sql.append(" "+table);
        //operatefields注入sql语句的键值映射
        addkeyvalue();
        //写入where语句
        addcondition();
        //排序语句
        addorder();
        //限制条目
        addlimit();
        return sql.toString();
    }

    private String deleteToSql(){
        sql=sql.append(OPERATES[3]);
        sql=sql.append(" from "+table);
        //写入where语句
        addcondition();
        //排序语句
        addorder();
        //限制条目
        addlimit();
        return sql.toString();
    }
}