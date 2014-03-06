package model;

import util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 10/10/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClickStreamLog {
    String ip;              //ip address
    String uuid;            //user identification
    String url;             //网页地址
    String sessionid;       //会话ID，由uuid及累计会话次数组合而成
    String session_times;   //累计会话（启动）的次数
    String csvp;            //本次会话内访问页面的次数    short for (current_session_visit_pages)
    String utm_medium;      //广告媒介方式
    String utm_source;      //广告来源
    String utm_term;        //广告系列词汇
    String country;         //用户所在国家
    String province;        //用户所在省份
    String city;            //用户所在市县
    String visit_timestamp; //访问的用户端时间戳（ms）
    String real_date;       //访问的真实服务端日期
    String real_hour;       //访问的真实服务端小时
    String browser_type;    //浏览器版本
    String operation_system;//设备操作系统
    String screen_resolution;//屏幕分辨率
    String refer_url;        //上次url，没有为“-”
    String receive_time;      //web容器获取,服务端接收时间
    String receive_date;
    String receive_hour;

    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    public String getReceive_date() {
        return receive_date;
    }

    public void setReceive_date(String receive_date) {
        this.receive_date = receive_date;
    }

    public String getReceive_hour() {
        return receive_hour;
    }

    public void setReceive_hour(String receive_hour) {
        this.receive_hour = receive_hour;
    }

    public String getUuid() {

        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getSession_times() {
        return session_times;
    }

    public void setSession_times(String session_times) {
        this.session_times = session_times;
    }

    public String getCsvp() {
        return csvp;
    }

    public void setCsvp(String csvp) {
        this.csvp = csvp;
    }

    public String getUtm_medium() {
        return utm_medium;
    }

    public void setUtm_medium(String utm_medium) {
        this.utm_medium = utm_medium;
    }

    public String getUtm_source() {
        return utm_source;
    }

    public void setUtm_source(String utm_source) {
        this.utm_source = utm_source;
    }

    public String getUtm_term() {
        return utm_term;
    }

    public void setUtm_term(String utm_term) {
        this.utm_term = utm_term;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVisit_timestamp() {
        return visit_timestamp;
    }

    public void setVisit_timestamp(String visit_timestamp) {
        this.visit_timestamp = visit_timestamp;
    }

    public String getReal_date() {
        return real_date;
    }

    public void setReal_date(String real_date) {
        this.real_date = real_date;
    }

    public String getReal_hour() {
        return real_hour;
    }

    public void setReal_hour(String real_hour) {
        this.real_hour = real_hour;
    }

    public String getBrowser_type() {
        return browser_type;
    }

    public void setBrowser_type(String browser_type) {
        this.browser_type = browser_type;
    }

    public String getOperation_system() {
        return operation_system;
    }

    public void setOperation_system(String operation_system) {
        this.operation_system = operation_system;
    }

    public String getScreen_resolution() {
        return screen_resolution;
    }

    public void setScreen_resolution(String screen_resolution) {
        this.screen_resolution = screen_resolution;
    }

    public String getRefer_url() {
        return refer_url;
    }

    public void setRefer_url(String refer_url) {
        this.refer_url = refer_url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String format2Reduce() {
        String data;
        data = this.getIp()
                + Constant.MR_SEPARATION + this.getCountry() + Constant.MR_SEPARATION + this.getProvince()
                + Constant.MR_SEPARATION + this.getCity() + Constant.MR_SEPARATION + this.getUuid()
                + Constant.MR_SEPARATION + this.getSessionid() + Constant.MR_SEPARATION + this.getSession_times()
                + Constant.MR_SEPARATION + this.getCsvp() + Constant.MR_SEPARATION + this.getUrl()
                + Constant.MR_SEPARATION + this.getUtm_medium() + Constant.MR_SEPARATION + this.getUtm_source()
                + Constant.MR_SEPARATION + this.getUtm_term() + Constant.MR_SEPARATION + this.getVisit_timestamp()
                + Constant.MR_SEPARATION + this.getReal_date() + Constant.MR_SEPARATION + this.getReal_hour()
                + Constant.MR_SEPARATION + this.getBrowser_type() + Constant.MR_SEPARATION + this.getOperation_system()
                + Constant.MR_SEPARATION + this.getScreen_resolution() + Constant.MR_SEPARATION + this.getRefer_url()
                + Constant.MR_SEPARATION + this.getReceive_time() + Constant.MR_SEPARATION + this.getReceive_date()
                + Constant.MR_SEPARATION + this.getReceive_hour();
        return data;
    }
}
