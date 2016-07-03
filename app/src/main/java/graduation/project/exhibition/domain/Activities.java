package graduation.project.exhibition.domain;

/**
 * 看资讯：活动信息
 */
public class Activities {

    private String id;

    private String text;

    private String time;

    private String title;

    private String price;

    private String state;

    private String addr;

    private String pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return this.price;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
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
