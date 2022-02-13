package com.longl.zk.dubbo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
public class UserVo implements Serializable {


    private Integer id;
    private String name;
    private Date birthDay;
    private Integer port;

    public UserVo(Integer id, String name, Date birthDay, Integer port) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.port = port;
    }

    public UserVo() {
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDay=" + birthDay +
                ", port=" + port +
                '}';
    }
}
