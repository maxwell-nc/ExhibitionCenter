package graduation.project.exhibition.domain;

/**
 * 展品的基本信息
 */
public class Product {

    private String id;

    private String text;

    private String title;

    private String comid;

    private String price;

    private String pic;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
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
    public void setComid(String comid){
        this.comid = comid;
    }
    public String getComid(){
        return this.comid;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public String getPrice(){
        return this.price;
    }
    public void setPic(String pic){
        this.pic = pic;
    }
    public String getPic(){
        return this.pic;
    }

}
