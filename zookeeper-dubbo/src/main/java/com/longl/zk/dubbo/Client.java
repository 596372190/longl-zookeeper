package com.longl.zk.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import java.io.IOException;

public class Client {

    UserService userService;

    //远程服务的调用地址
    public UserService buildService(String url){
        ApplicationConfig config = new ApplicationConfig("young-app");
        //构建一个引用对象
        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(config);
        referenceConfig.setInterface(UserService.class);
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://175.178.87.194:2181"));
        referenceConfig.setTimeout(50000);
        //透明化
        this.userService=referenceConfig.get();
        return userService;
    }

    static int i=0;

    public static void main(String[] args) throws IOException {
        Client client1 = new Client();
        client1.buildService("");
        String cmd;
        System.out.println("okle");
        while(!(cmd=read()).equals("exit")){
            UserVo u= client1.userService.getUser(Integer.parseInt(cmd));
            System.out.println(u);
        }

    }

    private static String read() throws IOException {
        byte[] b = new byte[1024];
        int size = System.in.read(b);
        return new String(b,0,size).trim();
    }
}
