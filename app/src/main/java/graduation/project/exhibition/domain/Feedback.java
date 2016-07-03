package graduation.project.exhibition.domain;

/**
 * 反馈数据
 */
public class Feedback {
    private String id;

    private String text;

    private String usercode;

    private String repeat;

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

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUsercode() {
        return this.usercode;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getRepeat() {
        return this.repeat;
    }

}
