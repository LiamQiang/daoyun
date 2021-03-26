package com.lq.daoyun.Entity;


import com.baomidou.mybatisplus.extension.api.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object object;


    /**
     * 带信息成功返回结果
     * @param message
     * @return
     */
    public static RespBean success(String message){
        return new RespBean(200, message, null);
    }

    /**
     * 带信息和对象成功返回结果
     * @param message
     * @param object
     * @return
     */
    public static RespBean success(String message, Object object){
        return  new RespBean(200, message, object);
    }


    /**
     * 带信息失败返回结果
     * @param message
     * @return
     */
    public static RespBean error(String message){
        return new RespBean(500, message, null);
    }


    /**
     * 带信息和对象失败返回结果
     * @param message
     * @param object
     * @return
     */
    public static RespBean error(String message, Object object){
        return new RespBean(500, message, object);
    }



}
