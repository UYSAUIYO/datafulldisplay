package org.example.datafulldisplay.result;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 自定义响应数据结构
 * 这个类是提供给门户，ios，安卓，微信商城用的
 * 门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 其他自行处理
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 555：异常抛出信息
 */
@Data
@NoArgsConstructor
public class GlobalResult {
    /**
     * 响应业务状态
     */
    private Integer status;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Object data;

    private String ok;


    public GlobalResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public GlobalResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public GlobalResult(Object data, String msg) {
        this.status = 200;
        this.msg = msg;
        this.data = data;
    }

    public static GlobalResult build(Integer status, String msg, Object data) {
        return new GlobalResult(status, msg, data);
    }

    public static GlobalResult ok(Object data) {
        return new GlobalResult(data);
    }

    public static GlobalResult ok(Object data, String msg) {
        return new GlobalResult(data, msg);
    }

    public static GlobalResult ok() {
        return new GlobalResult(null);
    }

    public static GlobalResult errorMsg(String msg) {
        return new GlobalResult(500, msg, null);
    }

    public static GlobalResult errorMap(Object data) {
        return new GlobalResult(501, "error", data);
    }

    public static GlobalResult errorTokenMsg(String msg) {
        return new GlobalResult(502, msg, null);
    }

    public static GlobalResult errorException(String msg) {
        return new GlobalResult(555, msg, null);
    }

    public Boolean isOK() {
        return this.status == 200;
    }

}
