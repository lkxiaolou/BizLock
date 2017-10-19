package BizLock;

abstract public class BizLock {

    public String lockName = "";

    abstract public boolean getLock() throws Exception;

    abstract public void releaseLock() throws Exception;

}
