package momo.cn.edu.fjnu.videoclient.service;

/**
 * 位置服务,提供经度,纬度,定位的位置信息
 * Created by GaoFei on 2016/3/8.
 */
public class LocationService {
    /**经度*/
    public static double lng  = 0;
    /**纬度*/
    public static double lat = 0;
    /**位置信息*/
    public static String address = "";

    public static void initValue(){
        lng = lat = 0;
        address = "";
    }

}
