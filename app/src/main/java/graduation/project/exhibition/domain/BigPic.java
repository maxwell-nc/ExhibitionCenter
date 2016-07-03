package graduation.project.exhibition.domain;

/**
 * 推荐热点大图
 */
public class BigPic {

    private String id;

    private String text;

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

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return this.pic;
    }

}
