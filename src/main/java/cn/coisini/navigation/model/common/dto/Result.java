package cn.coisini.navigation.model.common.dto;

import cn.coisini.navigation.model.common.enums.ResultEnum;

import java.io.Serializable;

/**
 * Author: xiaoxiang
 * Description: 通用的结果返回类
 */
public class Result <T> implements Serializable {

    private String host;
    private Integer code;
    private String message;
    private T data;

    public Result() {
        this.code = 200;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public static <T> Result ok(int code, String msg) {
        Result result = new Result();
        return result.okResult(code, null, msg);
    }

    public static <T> Result<T> ok(Object data) {
        Result<T> result = setResultEnum(ResultEnum.SUCCESS, ResultEnum.SUCCESS.getMessage());
        if(data!=null) {
            result.setData((T) data);
        }
        return result;
    }
    public static <T> Result error(int code, String msg) {
        Result result = new Result();
        return result.errorResult(code, msg);
    }

    public static <T> Result<T> error(ResultEnum enums){
        return setResultEnum(enums,enums.getMessage());
    }

    public static <T> Result<T> error(ResultEnum enums, String message){
        return setResultEnum(enums,message);
    }

    public static <T> Result<T> setResultEnum(ResultEnum enums){
        return ok(enums.getCode(),enums.getMessage());
    }

    private static <T> Result<T> setResultEnum(ResultEnum enums, String message){
        return ok(enums.getCode(),message);
    }

    public Result<T> errorResult(Integer code, String msg) {
        this.code = code;
        this.message = msg;
        return this;
    }

    public Result<T> okResult(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public Result<T> okResult(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.message = msg;
        return this;
    }

    public Result<T> okResult(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public static void main(String[] args) {
        //前置
        /*ResultEnum success = ResultEnum.SUCCESS;
        System.out.println(success.getCode());
        System.out.println(success.getmessage());*/
        /*0 操作成功*/

        //查询一个对象
        /*Map map = new HashMap();
        map.put("name","zhangsan");
        map.put("age",18);
        Result result = Result.okResult(map);
        System.out.println(JSON.toJSONString(result));*/
        /*{"code":0,"data":{"name":"zhangsan","age":18},"message":"操作成功"}*/


        //新增，修改，删除  在项目中统一返回成功即可
        /*Result result = Result.error(ResultEnum.SUCCESS);
        System.out.println(JSON.toJSONString(result));*/
        /*{"code":0,"message":"操作成功"}*/


        //根据不用的业务返回不同的提示信息  比如：当前操作需要登录、参数错误
        /*Result result = Result.error(ResultEnum.NEED_LOGIN);
        System.out.println(JSON.toJSONString(result));*/
        /*{"code":1,"message":"需要登录后操作"}*/

        //查询分页信息
        /*PageResult result = new PageResult(1,5,50);
        List list = new ArrayList();
        list.add("coisini");
        list.add("666");
        result.setData(list);
        System.out.println(JSON.toJSONString(result));*/
        /*{"code":200,"currentPage":1,"data":["coisini","666"],"size":5,"total":50}*/

    }

}