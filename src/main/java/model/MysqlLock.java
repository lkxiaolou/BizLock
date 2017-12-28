package model;

public class MysqlLock {

    private long id;
    private String name;
    private long gmtCreate;

    public String getName() {
        return name;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
