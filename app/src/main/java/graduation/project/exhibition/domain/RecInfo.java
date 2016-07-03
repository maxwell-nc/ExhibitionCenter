package graduation.project.exhibition.domain;

/**
 * 焦点资讯
 */
public class RecInfo {

    private String id;

    private String text;

    private String title;

    private String praise;

    private String pic;

    private String comment;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getPraise() {
        return this.praise;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return this.pic;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return this.comment;
    }
}
