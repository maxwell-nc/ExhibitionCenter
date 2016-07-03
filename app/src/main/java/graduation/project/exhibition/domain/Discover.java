package graduation.project.exhibition.domain;

import java.io.Serializable;

/**
 * 发现：猜你喜欢
 */
public class Discover implements Serializable {

    private String id;

    private String type;

    private String text;

    private String title;

    private String title_tap;

    private String text_tap;

    private String star;

    private String add_text;

    private String near;

    private String pic;

    private String spend;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNear() {
        return near;
    }

    public void setNear(String near) {
        this.near = near;
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

    public void setTitle_tap(String title_tap) {
        this.title_tap = title_tap;
    }

    public String getTitle_tap() {
        return this.title_tap;
    }

    public void setText_tap(String text_tap) {
        this.text_tap = text_tap;
    }

    public String getText_tap() {
        return this.text_tap;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getStar() {
        return this.star;
    }

    public void setAdd_text(String add_text) {
        this.add_text = add_text;
    }

    public String getAdd_text() {
        return this.add_text;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return this.pic;
    }

    public void setSpend(String spend) {
        this.spend = spend;
    }

    public String getSpend() {
        return this.spend;
    }

}
