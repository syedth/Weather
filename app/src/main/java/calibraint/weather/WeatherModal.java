package calibraint.weather;

/**
 * Created by syedthoufiq on 07/04/2017.
 */

public class WeatherModal {
    private String day, tempmax, tempmin, description;

    public WeatherModal(String day, String tempmax, String tempmin, String description){
        this.day = day;
        this.tempmax = tempmax;
        this.tempmin = tempmin;
        this.description = description;
    }

    public String getDay(){
        return  day;
    }
    public void setDay(String day){
        this.day = day;
    }
    public String getTempmax(){
        return tempmax;
    }
    public void setTempmax(String tempmax){
        this.tempmax = tempmax;
    }
    public String getTempmin(){
        return tempmin;
    }
    public void setTempmin(String tempmin){
        this.tempmin =  tempmin;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
}
