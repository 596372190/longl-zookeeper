import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ZkClientTest1 {

    private String hosts = "8.129.96.243:2181";

    private ZkClient client;

    @BeforeAll
    public void initClient() {
        client = new ZkClient(hosts);
        log.info("ZkClient已完成初始化！！！");
    }

    @Data
    @AllArgsConstructor
    static class User implements Serializable{
        private String username;
        private Integer age;
    }

    public void createTestZkClient(){
        // 创建一个持久节点，值支持object
        client.create("/zkclientnode2",20, CreateMode.PERSISTENT);
        // 创建一个持久节点，可以递归创建节点，但无法递归设置值
        client.createPersistent("/zkclientnode/users/lisi",true);

    }

}
