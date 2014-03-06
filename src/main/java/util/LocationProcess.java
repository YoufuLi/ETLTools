package util;

import etlprocess.IPSeeker;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 10/15/13
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationProcess {
    public Map locationMap = new HashMap();
    protected IPSeeker ipSeeker = IPSeeker.getInstance();
    protected String country = "-";
    protected String area = "-";
    protected String province = "-";
    protected String city = "-";

    public Map getLocation(String ip) {
        country = ipSeeker.getCountry(ip).replace("CZ88.NET", "").replace("错误的IP数据库文件", "").replace("局域网", "");
        area = ipSeeker.getArea(ip).replace("CZ88.NET", "").replace("错误的IP数据库文件", "").replace("对方和您在同一内部网", "");
        boolean isChina = false;
        if (country != null && !country.equals("") && !country.equals(" ")) {
            //把中国的地址拿出来特殊处理
            System.out.println("lalal");
            isChina = this.ChinaProcess(country);
        } else {
            country = "-";
        }
        if (!isChina) {
            if (area != null && !area.equals("") && !area.equals(" ")) {
                this.foreignProcess(area);
            } else {
                province = "-";
                city = "-";
            }
        }
        locationMap.put("country", country);
        locationMap.put("province", province);
        locationMap.put("city", city);
        return locationMap;
    }

    /*
    用于判断地址是否来自中国，如果来自中国，对省份和城市信息进行处理
     */
    public Boolean ChinaProcess(String _location) {
        boolean isChina = false;
        //Unicode:中国\u4E2D\u56FD    省\u7701  市\u5E02 县\u53BF 地区\u5730\u533A
        if (_location.length() >= 3 && Constant.PROVINCE_LIST.contains(_location.substring(0, 3))) {
            country = "\u4E2D\u56FD";
            if (_location.lastIndexOf("\u7701") > 0) {
                province = _location.substring(0, _location.lastIndexOf("\u7701") + 1);
            } else {
                province = _location.substring(0, 3);
            }
            String temp = _location.replace(province, "").replace("\u7701", "");
            if (temp.lastIndexOf("\u5E02") > 0) {
                city = temp.substring(0, temp.lastIndexOf("\u5E02") + 1);
            } else if (temp.lastIndexOf("\u53BF") > 0) {
                city = temp.substring(0, temp.lastIndexOf("\u53BF") + 1);
            } else if (temp.lastIndexOf("\u5730\u533A") > 1) {
                city = temp.substring(0, temp.lastIndexOf("\u5730\u533A") + 2);
            } else {
                city = "-";
            }
            isChina = true;
        } else if (_location.length() >= 2 && Constant.PROVINCE_LIST.contains(_location.substring(0, 2))) {
            country = "\u4E2D\u56FD";

            if (_location.lastIndexOf("\u7701") > 0) {
                province = _location.substring(0, _location.lastIndexOf("\u7701") + 1);
            } else {
                province = _location.substring(0, 2);
            }
            String temp = _location.replace(province, "").replace("\u7701", "");
            if (temp.lastIndexOf("\u5E02") > 0) {
                city = temp.substring(0, temp.lastIndexOf("\u5E02") + 1);
            } else if (temp.lastIndexOf("\u53BF") > 0) {
                city = temp.substring(0, temp.lastIndexOf("\u53BF") + 1);
            } else if (temp.lastIndexOf("\u5730\u533A") > 1) {
                city = temp.substring(0, temp.lastIndexOf("\u5730\u533A") + 2);
            } else {
                city = "-";
            }
            isChina = true;

        }
        return isChina;
    }

    public void foreignProcess(String _are) {
        province = _are;
        city = _are;
    }

    public static void main(String[] args) {
        /*北京IP 58.83.145.3  黑龙江：113.2.47.240  美国24.179.140.0 宁夏14.134.119.255
        *新疆218.202.209.0    西藏 ： 219.243.238.17 内蒙古 58.18.0.2 香港203.198.69.64
        */
        String ip_address = "192.168.1.58";
        LocationProcess locationProcess = new LocationProcess();
        Map map = locationProcess.getLocation(ip_address);
        IPSeeker seeker = IPSeeker.getInstance();
        System.out.println(seeker.getCountry(ip_address));
        System.out.println(seeker.getArea(ip_address));

        System.out.println(map.get("country"));
        System.out.println(map.get("province"));
        System.out.println(map.get("city"));

    }

}
