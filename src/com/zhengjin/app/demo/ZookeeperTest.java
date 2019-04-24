package com.zhengjin.app.demo;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * zookeeper basic APIs test demo.
 *
 */
public final class ZookeeperTest {

	private static final String TAG = ZookeeperTest.class.getSimpleName() + " => ";

	private void testZookeeper01(ZooKeeper zk) throws InterruptedException, KeeperException {
		final String testNode = "/testRootNode01";
		if (zk.exists(testNode, false) != null) {
			zk.delete(testNode, -1);
		}

		System.out.println(TAG + "create a zk node for test.");
		zk.create(testNode, "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.setData(testNode, "testRootDataNew".getBytes(), -1);

		System.out.println(TAG + "set child watch for testnode, and add a child node.");
		System.out.println(TAG + "test node children: " + zk.getChildren(testNode, true));
		this.sleep(1);
		final String childOneNode = testNode + "/testChildNodeOne";
		zk.create(childOneNode, "testChildDataOne".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		System.out.println(TAG + "set data watch for testNode, and update node value.");
		System.out.println(TAG + "testnode value: " + new String(zk.getData(testNode, true, null)));
		this.sleep(1);
		zk.setData(testNode, "testRootDataNew2".getBytes(), -1);

		System.out.println(TAG + "set data watch for testnode, and custom watch for childone node.");
		System.out.println(TAG + "root node states: " + this.getNodeStat(zk.exists(testNode, true)));
		System.out.println(TAG + "root node states: " + this.getNodeStat(zk.exists(childOneNode, new MyWatcher())));
		this.sleep(1);

		System.out.println(TAG + "clearup, delete created nodes.");
		zk.delete(childOneNode, -1);
		zk.delete(testNode, -1);
		this.sleep(1);
	}

	private void testZookeeper02(ZooKeeper zk) throws InterruptedException, KeeperException {
		final String tmpNode = "/testEphemeralNode";
		final String testNode = "/testRootNode02";
		final String childNodeOne = testNode + "/testChildNodeOne";
		final String childNodeTwo = testNode + "/testChildNodeTwo";

		if (zk.exists(testNode, false) != null) {
			try {
				zk.delete(childNodeOne, -1);
				zk.delete(childNodeTwo, -1);
			} catch (KeeperException e) {
				System.out.println(e.getMessage());
			}
			zk.delete(testNode, -1);
		}

		System.out.println(TAG + "create a zk ephemeral testnode.");
		zk.create(tmpNode, "tmpNodedata".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println(TAG + "ephemeral testnode data: " + new String(zk.getData(tmpNode, false, null)));

		System.out.println(TAG + "create a zk testnode with a childone.");
		zk.create(testNode, "testNodeData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create(childNodeOne, "ChildOneData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		System.out.println(TAG + "get testnode data, and update it.");
		zk.setData(testNode, "testNodeDataNew".getBytes(), -1);
		System.out.println(TAG + "test node data: " + new String(zk.getData(testNode, false, null)));

		System.out.println(TAG + "set both data and child watch for testnode.");
		System.out.println(TAG + "testnode states: " + this.getNodeStat(zk.exists(testNode, true)));
		System.out.println(TAG + "testnode children: " + zk.getChildren(testNode, true));
		this.sleep(1);

		System.out.println(TAG + "update childone node data, and add a childtwo node to testnode.");
		zk.setData(childNodeOne, "ChildOneDataNew".getBytes(), -1);
		zk.create(childNodeTwo, "ChildTwoData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		System.out.println(TAG + "set data watch for childtwo node.");
		System.out.println(TAG + "childtwo node data: " + new String(zk.getData(childNodeTwo, true, null)));
		this.sleep(1);

		System.out.println(TAG + "clearup, delete all created test nodes.");
		zk.delete(childNodeTwo, -1);
		zk.delete(childNodeOne, -1);
		zk.delete(testNode, -1);
		this.sleep(1);
	}

	private String getNodeStat(Stat stat) {
		return String.format("Version=%d, NumChildren=%d", stat.getVersion(), stat.getNumChildren());
	}

	private void sleep(int sec) {
		try {
			Thread.sleep(sec * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class MyWatcher implements Watcher {

		@Override
		public void process(WatchedEvent event) {
			System.out.println(
					TAG + String.format("custom watch [event]: type=%s, path=%s", event.getType(), event.getPath()));
		}
	}

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

		ZookeeperTest testZk = new ZookeeperTest();

		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182", 5000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// default watch event
				System.out.println(TAG
						+ String.format("default watch [event]: type=%s, path=%s", event.getType(), event.getPath()));
			}
		});

		try {
			String testNum = "02";
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
		System.out.println(TAG + "zk test demo DONE.");
	}

}
