package com.ueboot.shiro.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserInfo {

    private String userName;

    private Date expiredDate;


    public  UserInfo(){
        super();
    }

    /***
     *
     * @param userName
     * @param expiredDate
     */
    public UserInfo(String userName, Date expiredDate) {
        this.userName = userName;
        this.expiredDate = expiredDate;
    }
}
