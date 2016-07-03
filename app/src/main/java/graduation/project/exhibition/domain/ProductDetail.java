package graduation.project.exhibition.domain;

/**
 * 订单详情
 */
public class ProductDetail {

    private String id;

    private String key2;

    private String key1;

    private String key3;

    private String key4;

    private String star;

    private String special;

    private String addr;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getKey4() {
        return key4;
    }

    public void setKey4(String key4) {
        this.key4 = key4;
    }

    public void setKey2(String key2){
        this.key2 = key2;
    }
    public String getKey2(){
        return this.key2;
    }
    public void setKey1(String key1){
        this.key1 = key1;
    }
    public String getKey1(){
        return this.key1;
    }
    public void setStar(String star){
        this.star = star;
    }
    public String getStar(){
        return this.star;
    }
    public void setSpecial(String special){
        this.special = special;
    }
    public String getSpecial(){
        return this.special;
    }
    public void setAddr(String addr){
        this.addr = addr;
    }
    public String getAddr(){
        return this.addr;
    }

}
