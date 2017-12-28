package BizLock;

import dao.MysqlLockDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 表结构
 * CREATE TABLE `biz_lock` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(64) NOT NULL,
    `gmtCreate` int(10) NOT NULL,
     PRIMARY KEY (`id`),
    UNIQUE KEY `idex_name` (`name`)
 * ) ENGINE=MyISAM DEFAULT CHARSET=utf8
 */
public class MysqlLock extends BizLock {

    private SqlSession session = null;

    public MysqlLock(String lockName) throws IOException {
        this.lockName = getRealLockName(lockName);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
    }

    public boolean getLock() {

        MysqlLockDao mysqlLockDao = session.getMapper(MysqlLockDao.class);
        model.MysqlLock mysqlLock = new model.MysqlLock();
        mysqlLock.setName(lockName);
        int affect;
        try {
            affect = mysqlLockDao.insertLock(mysqlLock);
        } catch (Exception e) {
            return false;
        }
        if (mysqlLock.getId() > 0) {
            return true;
        }

        return false;
    }

    public void releaseLock() throws Exception{
        MysqlLockDao mysqlLockDao = session.getMapper(MysqlLockDao.class);
        mysqlLockDao.deleteLock(lockName);
    }

    public String getRealLockName(String lockName) {
        return lockName;
    }
}
