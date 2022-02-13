package com.longl.zk.dubbo;

import java.util.Date;
import java.util.UUID;

public class UserServiceImpl implements UserService{

    private Integer port=0;

    @Override
    public UserVo getUser(Integer id) {
        return new UserVo(id, UUID.randomUUID().toString(),new Date(),port);
    }


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
