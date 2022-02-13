package com.logl.web;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

public class MainControl implements InitializingBean {

    @Value("${zk:8.129.96.243:2181,175.178.87.194:2181,8.129.96.243:2183}")
    private String server;

    private ZkClient zkClient;
    private static final String rootPath = "/tuling-manger";


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
