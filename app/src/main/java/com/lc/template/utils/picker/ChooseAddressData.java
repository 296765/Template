package com.lc.template.utils.picker;

import java.io.Serializable;

/**
 * 地址 选择
 */
public class ChooseAddressData implements Serializable {


    public String province_id;
    public String city_id;
    public String area_id;
    public String province_name;
    public String city_name;
    public String area_name;



    public ChooseAddressData() {
    }

    public ChooseAddressData(String province_name, String city_name, String area_name) {
        this.province_name = province_name;
        this.city_name = city_name;
        this.area_name = area_name;
    }

    @Override
    public String toString() {
        return "ChooseTimeData{" +
                "province_id='" + province_id + '\'' +
                ", city_id='" + city_id + '\'' +
                ", area_id='" + area_id + '\'' +
                '}';
    }
}