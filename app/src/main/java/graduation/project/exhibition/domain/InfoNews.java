package graduation.project.exhibition.domain;

/**
 * 最新资讯
 */
public class InfoNews {

    private String id;

    private String text;

    private String title;

    private String pic;

    private String readcount;

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setPic(String pic){
        this.pic = pic;
    }
    public String getPic(){
        return this.pic;
    }
    public void setReadcount(String readcount){
        this.readcount = readcount;
    }
    public String getReadcount(){
        return this.readcount;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
}
