import BizLock.MysqlLock;

public class Runner {

    public static void main(String[] args) throws Exception {
        MysqlLock mysqlLock = new MysqlLock("test2");
        mysqlLock.getLock();
        mysqlLock.releaseLock();
    }

}
