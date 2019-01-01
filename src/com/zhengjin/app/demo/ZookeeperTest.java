package com.zhengjin.app.demo;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperTest {

	private static final String TAG = ZookeeperTest.class.getSimpleName() + " => ";

	private void testZookeeper01(ZooKeeper zk) throws InterruptedException, KeeperException {
		zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.setData("/testRootPath", "modifytestRootData".getBytes(), -1);

		System.out.println(TAG + "children: " + zk.getChildren("/testRootPath", true));
		zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);

		System.out.println(TAG + new String(zk.getData("/testRootPath", true, null)));
		zk.setData("/testRootPath", "modifytestRootData".getBytes(), -1);

		System.out.println(TAG + "root node states: " + zk.exists("/testRootPath", true));
		System.out.println(TAG + "root node states: " + zk.exists("/testRootPath/testChildPathOne", true));
		zk.delete("/testRootPath/testChildPathOne", -1);
		zk.delete("/testRootPath", -1);
	}

	private void testZookeeper02(ZooKeeper zk) throws InterruptedException, KeeperException {
		zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);

		System.out.println(TAG + new String(zk.getData("/testRootPath", false, null)));
		zk.setData("/testRootPath", "modifytestRootData".getBytes(), -1);

		System.out.println(TAG + "root node states: " + zk.exists("/testRootPath", true));
		System.out.println(TAG + "children: " + zk.getChildren("/testRootPath", true));

		zk.setData("/testRootPath/testChildPathOne", "modifyChildDataOne".getBytes(), -1);
		zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		System.out.println(TAG + new String(zk.getData("/testRootPath/testChildPathTwo", true, null)));

		zk.delete("/testRootPath/testChildPathTwo", -1);
		zk.delete("/testRootPath/testChildPathOne", -1);
		zk.delete("/testRootPath", -1);
	}

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

		ZookeeperTest testZk = new ZookeeperTest();

		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// default watcher
				System.out.println(TAG + "trigger " + event.getType() + "event: " + event.getPath());
			}
		});

		try {
			String testNum = "01";
			if ("01".equals(testNum)) {
				testZk.testZookeeper01(zk);
			} else {
				testZk.testZookeeper02(zk);
			}
		} finally {
			if (zk != null) {
				zk.close();
			}
		}
	}

}
