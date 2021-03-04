package com.syx.springboot.threespringboot.websocket.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResult<T> {


    private static final  String SUCCESS = "SUCCESS";

    private boolean status;

    private String msg;

    private T data;


    public static <T> MessageResult<T> success(){
        MessageResult<T> result = new MessageResult<>();
        result.setStatus(true);
        result.setMsg(SUCCESS);
        return result;
    }

    public static <T> MessageResult<T> success(T data){
        MessageResult<T> result  = success();
        result.setData(data);
        return result;
    }
}
