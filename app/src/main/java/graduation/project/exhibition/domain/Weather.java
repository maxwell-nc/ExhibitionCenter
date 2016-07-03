package graduation.project.exhibition.domain;

/**
 * 天气信息
 */
public class Weather {

    private String tem;//温度
    private String time;
    private String weather;//天气文本
    private String type;

    public void setTem(String tem){
        this.tem = tem;
    }
    public String getTem(){
        return this.tem;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
    public void setWeather(String weather){
        this.weather = weather;
    }
    public String getWeather(){
        return this.weather;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
}
