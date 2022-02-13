package com.longl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.I0Itec.zkclient.ZkClient;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Agent {

    private String server = "8.129.96.243:2181,175.178.87.194:2181,8.129.96.243:2183";
    ZkClient zkClient;
    private static Agent instance;
    private static final String rootPath = "/tuling-manger";
    private static final String servicePath = rootPath + "/service";
    private String nodePath;
    private Thread stateThread;
    List<OsBean> list = new ArrayList<>();

    private Agent() {
    }

    public static Agent getInstance(){
        return instance;
    }

    //虚拟机jvm执行main方法时前  执行的premain
    public static void premain(String args, Instrumentation instrumentation) {
        instance = new Agent();
        if (args != null) {
            instance.server = args;
        }
        instance.init();

    }

    // 初始化连接
    public void init() {
        zkClient = new ZkClient(server, 5000, 30000);
        //zkClient = new ZkClient(server);
        System.out.println("zk连接成功" + server);
        buildRoot();
        createServerNode();
        stateThread = new Thread(() -> {
            while (true) {
                updateServerNode();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "zk_stateThread");
        stateThread.setDaemon(true);
        stateThread.start();

    }

    // 构建根节点
    public void buildRoot() {
        if (!zkClient.exists(rootPath)) {
            zkClient.createPersistent(rootPath);
        }
    }

    // 生成服务节点
    public void createServerNode() {
        nodePath = zkClient.createEphemeralSequential(servicePath, getOsInfo());
        System.out.println("创建节点:" + nodePath);
    }

    // 监听服务节点状态改变


    public void updateServerNode() {
        zkClient.writeData(nodePath, getOsInfo());
    }

    // 更新服务节点状态
    public String getOsInfo() {
        OsBean bean = new OsBean();
        bean.lastUpdateTime = System.currentTimeMillis();
        bean.ip = getLocalIp();
        bean.cpu = CPUMonitorCalc.getInstance().getProcessCpu();
        MemoryUsage memoryUsag = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        bean.usableMemorySize = memoryUsag.getUsed() / 1024 / 1024;
        bean.maxMemorySize = memoryUsag.getMax() / 1024 / 1024;
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateNode(String path, Object data) {
        if (zkClient.exists(path)) {
            zkClient.writeData(path, data);
        } else {
            zkClient.createEphemeral(path, data);
        }
    }


    public static String getLocalIp() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return addr.getHostAddress();
    }

/*    public static void main(String[] args) {
        instance = new com.longl.Agent();
        instance.init();
        System.out.println(11111);
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

}