package com.syx.springboot.threespringboot.websocket.domain;


import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *  PUBLISH  tsp:getList '{"customerId":22,"data":{"pm2.5":12,"pm10":32}}'
 */
@Data
@NoArgsConstructor
public class MessageDTO {

    private Integer customerId;

    private String data;

}

