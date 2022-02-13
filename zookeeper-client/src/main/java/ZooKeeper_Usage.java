import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

//Java API->创建连接->创建一个最基本的ZooKeeper会话实例
public class ZooKeeper_Usage implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        ZooKeeper zooKeeper1 = zookeeperUsageSimple();
        //ZooKeeper zooKeeper2 = zookeeperUsageSidPasswd();
        try {
            //创建节点 world anyone 31 cdrwa
            /*zooKeeper1.create("/node","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);*/
           /* zooKeeper1.create("/node1","456".getBytes(),
                    Collections.singletonList(new ACL(ZooDefs.Perms.CREATE,ZooDefs.Ids.ANYONE_ID_UNSAFE)),
                            CreateMode.PERSISTENT);*/
           //异步创建一个临时的顺序节点
            /*zooKeeper1.create("/node2", "123".getBytes(),
                    Collections.singletonList(new ACL(ZooDefs.Perms.ALL, new Id("ip", "127.0.0.1"))),
                    CreateMode.EPHEMERAL_SEQUENTIAL,
                    new AsyncCallback.StringCallback() {
                        @Override
                        public void processResult(int rc, String path, Object ctx, String name) {
                            System.out.println("rc:" + rc);
                            System.out.println("path:" + path);
                            System.out.println("ctx:" + ctx);
                            System.out.println("name:" + name);
                        }
                    },"传给服务端的内容,会在异步回调时传回来11");
            Thread.sleep(20000);*/

            //异步创建一个持久节点, ACL为 digest:huangrl:1V+cT/PKoGODtNa5rH+Vhn7EmnI=:cdrwa,等同于如下命令：
            //     * create /node3 123 digest:huangrl:1V+cT/PKoGODtNa5rH+Vhn7EmnI=:cdrwa
            /*zooKeeper1.create("/node3", "123".getBytes(),
                    Collections.singletonList(new ACL(ZooDefs.Perms.ALL,
                            new Id("digest", "huangrl:1V+cT/PKoGODtNa5rH+Vhn7EmnI="))),
                    CreateMode.PERSISTENT,
                    new AsyncCallback.StringCallback() {
                        @Override
                        public void processResult(int rc, String path, Object ctx, String name) {
                            System.out.println("rc:" + rc);
                            System.out.println("path:" + path);
                            System.out.println("ctx:" + ctx);
                            System.out.println("name:" + name);
                        }
                    },"传给服务端的内容,会在异步回调时传回来22"
            );
            Thread.sleep(20000);*/

            /**
             * 创建一个持久顺序定时节点，如果在10000毫秒内 未修改node，并且没有子节点,那么它将被删掉
             */
            /*zooKeeper1.create("/node4", "123".getBytes(),
                    Collections.singletonList(new ACL(ZooDefs.Perms.ALL,
                            new Id("digest", "huangrl:1V+cT/PKoGODtNa5rH+Vhn7EmnI="))),
                    CreateMode.PERSISTENT_SEQUENTIAL_WITH_TTL,
                    new AsyncCallback.Create2Callback() {
                        @Override
                        public void processResult(int rc, String path, Object ctx, String name, Stat stat) {
                            System.out.println("rc:" + rc);
                            System.out.println("path:" + path);
                            System.out.println("ctx:" + ctx);
                            System.out.println("name:" + name);
                            System.out.println("stat:" + stat);
                        }
                    },"传给服务端的内容,会在异步回调时传回来444", 10000);
            Thread.sleep(20000);*/

            //===============================获取节点数据======================================
            /*zooKeeper1.addAuthInfo("digest","huangrl:123".getBytes());

            byte[] data = zooKeeper1.getData("/node3", new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("修改数据了");
                    System.out.println(watchedEvent);
                }
            }, new Stat());
            System.out.println("111:"+new String(data));*/

            /**
             * 异步调用
             * path 节点路径
             * watch true使用创建zookeeper时指定的默认watcher 如果为false则不设置监听
             * DataCallback 异步通知
             * ctx 回调上下文
             */
            /*zooKeeper1.getData("/node3", false, new AsyncCallback.DataCallback() {
                @Override
                public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                    System.out.println("rc:" + rc);
                    System.out.println("path:" + path);
                    System.out.println("ctx:" + ctx);
                    System.out.println("data:" + new String(data));
                    System.out.println("stat:" + stat);
                }
            },"传给服务端的内容,会在异步回调时传回来555");
            Thread.sleep(2000);
*/
            //===============================修改节点数据============================
            /**
             * setData节点有version这一参数，给定版本与节点的版本匹配，则设置给定路径的节点的数据（如果给定版本是-1，则它匹配任何节点的版本）。返回节点的统计信息。
             * 如果不存在具有给定路径的节点，则将抛出NoNodeException
             * 如果给定版本与节点的版本不匹配，将抛出BadVersionException
             * 设置的数据最大允许大小为1 MB
             */

            /**
             * 同步设置数据 -1匹配任何版本
             */
            /*Stat stat = zooKeeper1.setData("/node3", "helloworld11".getBytes(), -1);
            System.out.println("111stat:"+stat);*/

            /**
             * 异步设置数据
             */
            /*zooKeeper1.setData("/node3", "helloword22".getBytes(), -1, new AsyncCallback.StatCallback() {
                @Override
                public void processResult(int rc, String path, Object ctx, Stat stat) {

                    System.out.println("rc:" + rc);
                    System.out.println("path:" + path);
                    System.out.println("ctx:" + ctx);
                    System.out.println("stat:" + stat);

                }
            },"传给服务端的内容,会在异步回调时传回来6666");
            Thread.sleep(2000);*/

            //===============================删除节点======================================

            /**
             * 删除给定路径的节点。如果存在这样的节点，则调用将成功，并且给定版本与节点的版本匹配（如果给定版本为-1，则它匹配任何节点的版本）。
             * 如果节点不存在，将抛出NoNodeException。
             * 如果给定版本与节点的版本不匹配，将抛出BadVersionException。
             * 如果节点有子节点，将抛出NotEmptyException。
             * 如果成功将触发现有API调用留下的给定路径节点上的所有监视，以及getChildren API调用留下的父节点上的监视。
             */

            //zooKeeper1.delete("/d",-1);

            /**
             * 异步删除节点
             */
            /*zooKeeper1.delete("/node", -1, new AsyncCallback.VoidCallback() {
                @Override
                public void processResult(int rc, String path, Object ctx) {
                    System.out.println("rc:" + rc);
                    System.out.println("path:" + path);
                    System.out.println("ctx:" + ctx);
                }
            },"传给服务端的内容,会在异步回调时传回来777");
            Thread.sleep(2000);*/

            //===============================判断节点是否存在=====================
            /**
             * 同步检查节点是否存在,并留下监听
             */
            Stat exists = zooKeeper1.exists("/node2", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("留下监视");
                }
            });
            System.out.println("判断节点是否存在："+exists);


            //===============================ACL操作==========
            /*zooKeeper1.addAuthInfo("digest","helloworld:123456".getBytes());
            Stat auth = zooKeeper1.setACL("/nodeACL", Collections.singletonList(new ACL(ZooDefs.Perms.ALL,
                    new Id("auth", "helloworld:123456"))), -1);
            System.out.println(auth);
*/
            /*List<ACL> acl = zooKeeper1.getACL("/nodeACL", new Stat());
            System.out.println(acl);*/

            zooKeeper1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 简单连接
     */
    public static ZooKeeper zookeeperUsageSimple(){
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper("8.129.96.243:2181,175.178.87.194:2181,8.129.96.243:2183",3000,
                    new ZooKeeper_Usage());
            System.out.println("zookeeperUsageSimple:"+zooKeeper.getState());
            //Thread.sleep(2000);
            long sessionId = zooKeeper.getSessionId();
            byte[] sessionPasswd = zooKeeper.getSessionPasswd();
            System.out.println("zookeeperUsageSimple:"+zooKeeper.getState());
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }


    /**
     * 复用会话，以维持之前会话的有效性
     */
    public static ZooKeeper zookeeperUsageSidPasswd(){
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper("8.129.96.243:2181",5000,
                    new ZooKeeper_Usage());
            countDownLatch.await();
            System.out.println("zookeeperUsageSidPasswd:"+zooKeeper.getState());
            long sessionId = zooKeeper.getSessionId();
            byte[] sessionPasswd = zooKeeper.getSessionPasswd();
            //Use illegal sessionId and sessionPassWd
            zooKeeper = new ZooKeeper("8.129.96.243:2181",5000,//
                    new ZooKeeper_Usage(),//
                    1l,//
                    "test".getBytes());
            System.out.println("zookeeperUsageSidPasswd:"+zooKeeper.getState());
            //Use correct sessionId and sessionPassWd
            zooKeeper = new ZooKeeper("8.129.96.243:2181",5000,//
                    new ZooKeeper_Usage(),//
                    sessionId,//
                    sessionPasswd);
            System.out.println("zookeeperUsageSidPasswd:"+zooKeeper.getState());
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zooKeeper;

    }
    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched event:"+watchedEvent);
        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
            countDownLatch.countDown();
        }

    }
}
