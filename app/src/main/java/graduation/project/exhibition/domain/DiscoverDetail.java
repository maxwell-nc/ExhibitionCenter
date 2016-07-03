package graduation.project.exhibition.domain;

/**
 * 发现详情
 */
public class DiscoverDetail {

    private String id;

    private String text;

    private String addr;

    private String count;

    private String distime;

    private String special;

    private String express;

    private String discount;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return this.count;
    }

    public void setDistime(String distime) {
        this.distime = distime;
    }

    public String getDistime() {
        return this.distime;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getSpecial() {
        return this.special;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getExpress() {
        return this.express;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscount() {
        return this.discount;
    }
}
