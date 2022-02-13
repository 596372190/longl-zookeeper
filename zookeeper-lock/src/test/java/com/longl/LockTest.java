package com.longl;


import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LockTest {

    private ZookeeperLock zookeeperLock;

    @Before
    public void init() {
        zookeeperLock = new ZookeeperLock();
    }

    @Test
    public void getLockTest() throws InterruptedException {
        Lock lock = zookeeperLock.lock("luban", 180 * 1000);
        System.out.println("成功获取锁");
        Thread.sleep(5000);
        zookeeperLock.unlock(lock);
        Thread.sleep(Long.MAX_VALUE);
        assert lock != null;
    }

    @Test
    public void run() throws IOException, InterruptedException {
        File file = new File("d:/test.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                Lock lock = zookeeperLock.lock(file.getPath(), 180 * 1000);
                try {
                    System.out.println("开始执行业务操作：lockpath:"+lock.getPath());
                    String firstLine = Files.lines(file.toPath()).findFirst().orElse("0");
                    int count = Integer.parseInt(firstLine);
                    count++;
                    Files.write(file.toPath(), String.valueOf(count).getBytes());
                    System.out.println("结束执行业务操作：lockpath:"+lock.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    zookeeperLock.unlock(lock);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        String firstLine = Files.lines(file.toPath()).findFirst().orElse("0");
        System.out.println(firstLine);

    }
}
