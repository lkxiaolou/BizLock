package dao;

import model.MysqlLock;

public interface MysqlLockDao {

    public int insertLock(MysqlLock mysqlLock);

    public int deleteLock(String name);

}
