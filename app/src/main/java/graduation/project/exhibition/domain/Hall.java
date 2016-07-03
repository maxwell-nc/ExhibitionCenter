package graduation.project.exhibition.domain;

/**
 * 展览馆
 */
public class Hall {

    private String id;

    private String text;

    private String title;

    private String isRec;

    private String pic;

    private String type;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
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

    public void setIsRec(String isRec) {
        this.isRec = isRec;
    }

    public String getIsRec() {
        return this.isRec;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return this.pic;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
