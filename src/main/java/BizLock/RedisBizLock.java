package BizLock;

import redis.clients.jedis.Jedis;

import java.net.URI;

public class RedisBizLock extends BizLock {

    private final String pathPre = "redis.biz.lock.";
    private Jedis jedis;
    private final URI uri = new URI("redis://192.168.8.29:6379");

    public RedisBizLock(String lockName) throws Exception {
        jedis = new Jedis(uri);
        this.lockName = getRealLockName(lockName);
    }

    public boolean getLock() throws Exception {
        long re = jedis.incr(lockName);
        if (re == 1) {
            return true;
        }
        return false;
    }

    public void releaseLock() throws Exception {
        if (jedis != null) {
            jedis.del(lockName);
            jedis.close();
        }
    }

    public String getRealLockName(String lockName) {
        return pathPre + lockName;
    }
}
