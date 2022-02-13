package com.longl;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.stream.Collectors;

public class ZookeeperLock {
    private String server = "8.129.96.243:2181,175.178.87.194:2181,8.129.96.243:2183";
    private ZkClient zkClient;
    private static final String rootPath = "/tuling-lock";

    public ZookeeperLock() {
        zkClient = new ZkClient(server,5000,30000);
        buildRoot();
    }

    public void buildRoot() {
        if(!zkClient.exists(rootPath)){
            zkClient.createPersistent(rootPath);
        }
    }

    public Lock lock(String lockId,long timeout){
        Lock lockNode = createLockNode(lockId);
        lockNode = tryActiveLock(lockNode);//尝试获得锁
        if(!lockNode.isActive()){
            try{
                synchronized (lockNode){
                    lockNode.wait(timeout);//阻塞
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(!lockNode.isActive()){
            throw new RuntimeException("lock timeout");
        }
        return lockNode;
    }

    public Lock tryActiveLock(Lock lockNode) {
        //判断当前是否为最小节点
        List<String> list = zkClient.getChildren(rootPath).stream().sorted()
                .map(p -> rootPath + "/" + p).collect(Collectors.toList());
        String firstNodePath = list.get(0);
        System.out.println("当前的最小的节点是:"+firstNodePath);
        if(firstNodePath.equals(lockNode.getPath())){
            System.out.println("当前节点就是最小节点："+lockNode);
            lockNode.setActive(true);
        }else{
            String upNodePath = list.get(list.indexOf(lockNode.getPath())-1);//监控他的上一个节点
            System.out.println(lockNode+"--监控他自己上一个节点："+upNodePath);
            zkClient.subscribeDataChanges(upNodePath, new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {

                }

                @Override
                public void handleDataDeleted(String dataPath) throws Exception {//上一个节点删除相当于释放锁
                    System.out.println("节点删除："+dataPath);
                    Lock lock=tryActiveLock(lockNode);
                    synchronized (lockNode){
                        if(lockNode.isActive()){
                            lockNode.notify();
                        }
                    }
                    zkClient.unsubscribeDataChanges(upNodePath,this);
                }
            });
        }
        return lockNode;
    }

    public Lock createLockNode(String lockId) {
        String nodePath =zkClient.createEphemeralSequential(rootPath+"/"+lockId,1);
        return new Lock(lockId,nodePath);
    }

    public void unlock(Lock lock){
        if(lock.isActive()){
            zkClient.delete(lock.getPath());
        }
        System.out.println("解锁：path"+lock.getPath());
    }
}
