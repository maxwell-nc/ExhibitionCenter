package graduation.project.exhibition.domain;

/**
 * 门票
 */
public class Ticket {
    private String id;

    private String time;

    private String no2;

    private String no1;

    private String name;

    private String addr;

    private String pic;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getNo2() {
        return this.no2;
    }

    public void setNo1(String no1) {
        this.no1 = no1;
    }

    public String getNo1() {
        return this.no1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return this.pic;
    }
}
