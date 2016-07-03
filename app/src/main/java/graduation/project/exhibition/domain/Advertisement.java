package graduation.project.exhibition.domain;

/**
 * 广告内容
 */
public class Advertisement {

    private String id;

    private String pics;

    private String price;

    private String name;

    private String size;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getPics() {
        return this.pics;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return this.price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return this.size;
    }


}
