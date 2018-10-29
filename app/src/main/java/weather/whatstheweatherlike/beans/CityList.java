package weather.whatstheweatherlike.beans;

import java.util.List;

public class CityList {

    private List<City> list;

    public CityList() {
    }

    public CityList(List<City> list) {
        this.list = list;
    }

    public List<City> getList() {
        return list;
    }

    public void setList(List<City> list) {
        this.list = list;
    }
}
