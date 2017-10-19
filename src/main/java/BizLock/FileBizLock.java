package BizLock;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class FileBizLock extends BizLock {

    private final String pathPre = "/tmp/lock/";
    private FileChannel fileChannel;
    private FileLock fileLock;

    public FileBizLock(String lockName) throws Exception {

        this.lockName = getRealLockName(lockName);

        RandomAccessFile lockFile = new RandomAccessFile(lockName, "rw");
        fileChannel = lockFile.getChannel();
    }

    public boolean getLock() throws Exception {

        try {
            fileLock = fileChannel.tryLock();
        } catch (OverlappingFileLockException exception) {
            return false;
        }

        return true;
    }

    public void releaseLock() throws Exception {
        if (fileLock != null) {
            fileLock.release();
        }
        if (fileChannel != null) {
            fileChannel.close();
        }
    }

    public String getRealLockName(String lockName) {
        return pathPre + lockName + ".lock";
    }
}
