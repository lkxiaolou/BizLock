package BizLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

public class ZkBizLock extends BizLock {

    private static final String nameSpace = "zkBizLock";
    private static final String zkUrl = "192.168.8.29:2181";

    private CuratorFramework client = null;

    public ZkBizLock(String lockName) {
        this.lockName = getRealLockName(lockName);

        client = CuratorFrameworkFactory.newClient(zkUrl, new RetryNTimes(5, 5000));
        client.start();
        client.usingNamespace(nameSpace);
    }

    public boolean getLock() throws Exception{
        try {
            client.create().withMode(CreateMode.EPHEMERAL).forPath(lockName);
        } catch (KeeperException.NodeExistsException e) {
            return false;
        }
        return true;
    }

    public void releaseLock() throws Exception{
        if (client != null) {
            client.delete().forPath(lockName);
            client.close();
        }
    }

    public String getRealLockName(String lockName) {
        return "/"+lockName;
    }
}
