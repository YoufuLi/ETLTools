package etlprocess;

import model.ClickStreamLog;
import util.*;

import java.io.IOException;
import java.util.Map;

/**
 * APP应用正常日志ETL处理器
 */
public class EtlProcess {
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
    String receive_time;      //服务端接收时间
    String receive_date;
    String receive_hour;

    TimeProcess timeProcess = new TimeProcess();//声明时间格式处理类
    IPSeeker ipSeeker = IPSeeker.getInstance();
    LocationProcess locationProcess = new LocationProcess();

    /**
     * @param value
     * @return
     */
    // ---------------------处理格式日志---------------------------
    public ClickStreamLog preProcess(String value) {
        if (value == null || value.length() > 1000000) {
            return null;
        }
        value = this.cleanData(value);
        String logSet[] = value.split(Constant.SEPARATION_CHARACTER);
        if (logSet == null) {
            return null;
        }

        if (logSet.length < 8) {
            return null;
        }
        //url与uuid为空，抛弃该条记录，需要优先处理
        //网页地址
        if (this.urlProcess(logSet[4]).equals("-")) {
            return null;
        }
        //uuid处理
        if (this.uuidProcess("1234jlkfsdk").equals("-")) {
            return null;
        }
        //上次url，没有为“-”
        this.refer_urlProcess(logSet[0]);
        //浏览器版本browser_type
        this.browser_typeProcess(logSet[5]);

        //设备操作系统operation_system
        this.operation_systemProcess(logSet[1]);

        //visit_timestamp 访问的用户端时间戳（ms）,访问的真实服务端日期,访问的真实服务端小时
        this.visit_timestampProcess(logSet[2]);

        //屏幕分辨率,接受到的格式为sr=h1080!l1920
        this.screen_resolutionProcess(logSet[3]);

        //ip的赋值处理
        //用户所在国家
        //用户所在省份
        //用户所在市县
        this.ipProcess(logSet[6]);
        //String recive_time;      //无，web容器获取	服务端接收时间
        //String recive_date;
        //String recive_hour;
        this.receive_timeProcess(logSet[7]);

        //sessionid处理
        this.sessionidProcess("sessionid");
        //session_time处理
        this.session_timeProcess("sessiontime");
        //csvp处理
        this.csvpProcess("csvp");

        ClickStreamLog data = new ClickStreamLog();
        data.setIp(ip);
        data.setCountry(country);
        data.setProvince(province);
        data.setCity(city);

        data.setUuid(uuid);

        data.setUrl(url);
        data.setUtm_medium(utm_medium);
        data.setUtm_source(utm_source);
        data.setUtm_term(utm_term);

        data.setSessionid(sessionid);
        data.setSession_times(session_times);
        data.setCsvp(csvp);

        data.setBrowser_type(browser_type);
        data.setOperation_system(operation_system);
        data.setScreen_resolution(screen_resolution);

        data.setRefer_url(refer_url);

        data.setVisit_timestamp(visit_timestamp);
        data.setReal_date(real_date);
        data.setReal_hour(real_hour);

        data.setReceive_time(receive_time);
        data.setReceive_date(receive_date);
        data.setReceive_hour(receive_hour);
        return data;
    }

    //初步清洗数据
    public String cleanData(String _value) {
        String cleanData = StringCoding_Format.stringFormat(_value);
        cleanData = StringCoding_Format.stringDecoding(cleanData);
        return cleanData;
    }

    //String ip;              //ip address
    //String network_operators;//网络运营商
    public void ipProcess(String _ip) {
        if (_ip != null && !_ip.equals("")) {
            String[] ips = _ip.split("=");
            if (ips != null && ips.length > 0) {
                ip = ips[1].trim();
                this.getCountry_Pro_City(ip);
            } else {
                ip = "-";
                country = "-";
                province = "-";
                city = "-";
            }
        } else {
            ip = "-";
            country = "-";
            province = "-";
            city = "-";
        }
    }

    /**
     * //用户所在国家,用户所在省份,用户所在市县
     * 为处理完成，需要重写
     *
     * @param _ip
     */
    public void getCountry_Pro_City(String _ip) {
        Map locationMap = locationProcess.getLocation(_ip);
        country = locationMap.get("country").toString();
        province = locationMap.get("province").toString();
        city = locationMap.get("city").toString();
    }

    //String uuid;            //user identification
    public String uuidProcess(String _uuid) {
        if (_uuid != null && !_uuid.equals("")) {
            uuid = _uuid;
        } else {
            uuid = "-";
        }
        return uuid;
    }

    //url网页地址
    //String utm_medium;      //广告媒介方式
    //String utm_source;      //广告来源
    //String utm_term;        //广告系列词汇
    public String urlProcess(String _url) {
        if (_url != null && !_url.equals("")) {
            String urlString = _url.substring(_url.indexOf("=") + 1);
            if (urlString != null) {
                url = urlString.trim();
                this.utmProcess(url);
            } else {
                url = "-";
                utm_medium = "-";
                utm_source = "-";
                utm_term = "-";
            }
        } else {
            url = "-";
            utm_medium = "-";
            utm_source = "-";
            utm_term = "-";
        }
        return url;
    }

    /**
     * 根据URL处理url中的参数信息
     *
     * @param _url
     */
    public void utmProcess(String _url) {
        System.out.println(_url);
        String[] urlSet = _url.split("\\?");
        if (urlSet != null && urlSet.length > 1) {
            String[] utmSet = urlSet[1].split("&");
            if (utmSet.length >= 1 && utmSet[0] != null) {
                String[] utm_sourceSet = utmSet[0].split("=");
                if (utm_sourceSet != null && utm_sourceSet.length > 1) {
                    utm_source = utm_sourceSet[1];
                } else {
                    utm_source = "-";
                }
            } else {
                utm_source = "-";
            }
            if (utmSet.length >= 2 && utmSet[1] != null) {
                String[] utm_mediumSet = utmSet[1].split("=");
                if (utm_mediumSet != null && utm_mediumSet.length > 1) {
                    utm_medium = utm_mediumSet[1];
                } else {
                    utm_medium = "-";
                }
            } else {
                utm_medium = "-";
            }
            if (utmSet.length >= 3 && utmSet[2] != null) {
                String[] utm_termSet = utmSet[2].split("=");
                if (utm_termSet != null && utm_termSet.length > 1) {
                    utm_term = utm_termSet[1];
                } else {
                    utm_term = "-";
                }
            } else {
                utm_term = "-";
            }
        } else {
            utm_medium = "-";
            utm_source = "-";
            utm_term = "-";
        }
    }

    //上次url，没有为“-”
    public void refer_urlProcess(String _refer_url) {
        if (_refer_url != null && !_refer_url.equals("")) {
            System.out.println(_refer_url);
            String[] refer_urlSet = _refer_url.split("=");
            if (refer_urlSet != null && refer_urlSet.length > 1) {
                System.out.println(refer_urlSet[1]);
                if (!refer_urlSet[1].toString().equals("not set")) {
                    refer_url = refer_urlSet[1].trim();
                } else {
                    refer_url = "-";
                }
            } else {
                refer_url = "-";
            }
        } else {
            refer_url = "-";
        }
    }

    //String visit_timestamp; //访问的用户端时间戳（ms）
    //String real_date;       //访问的真实服务端日期
    //String real_hour;       //访问的真实服务端小时
    public void visit_timestampProcess(String _visit_timmestmap) {
        if (_visit_timmestmap != null && !_visit_timmestmap.equals("")) {
            String[] visit_timestampSet = _visit_timmestmap.split("=");
            if (visit_timestampSet != null && visit_timestampSet.length > 1) {
                visit_timestamp = visit_timestampSet[1].trim();
                real_date = timeProcess.getDate(visit_timestamp);
                real_hour = timeProcess.getHour(visit_timestamp);
            } else {
                visit_timestamp = "-";
                real_date = "-";
                real_hour = "-";
            }
        } else {
            visit_timestamp = "-";
            real_date = "-";
            real_hour = "-";
        }
    }

    //String browser_type;    //浏览器版本
    public void browser_typeProcess(String _browser_type) {
        if (_browser_type != null && !_browser_type.equals("")) {
            String[] browser_typeSet = _browser_type.split("=");
            if (browser_typeSet != null && browser_typeSet.length > 1) {
                browser_type = browser_typeSet[1].trim();
            } else {
                browser_type = "-";
            }
        } else {
            browser_type = "-";
        }
    }

    //String operation_system;//设备操作系统
    public void operation_systemProcess(String _operation_system) {
        if (_operation_system != null && !_operation_system.equals("")) {
            String[] operation_systemSet = _operation_system.split("=");
            if (operation_systemSet != null && operation_systemSet.length > 1) {
                operation_system = operation_systemSet[1].trim();
                operation_system = OSAnalysis.getOS(operation_system);
            } else {
                operation_system = "-";
            }

        } else {
            operation_system = "-";
        }
    }

    //String screen_resolution;//屏幕分辨率
    public void screen_resolutionProcess(String _screen_resolution) {
        if (_screen_resolution != null && !_screen_resolution.equals("")) {
            String[] screen_resolutionSet = _screen_resolution.split("=");
            if (screen_resolutionSet != null && screen_resolutionSet.length > 1) {
                screen_resolution = screen_resolutionSet[1].trim();
            } else {
                screen_resolution = "-";
            }
        } else {
            screen_resolution = "-";
        }
    }

    //String receive_time;      //无，web容器获取	服务端接收时间
    //String receive_date;
    //String receive_hour;
    public void receive_timeProcess(String _receive_time) {
        if (_receive_time != null && !_receive_time.equals("")) {
            String[] receive_timeSet = _receive_time.split("=");
            if (receive_timeSet != null && receive_timeSet.length > 1) {
                receive_time = receive_timeSet[1].trim();
                receive_date = timeProcess.getDate(receive_time);
                receive_hour = timeProcess.getHour(receive_time);
            } else {
                receive_time = "-";
                receive_date = "-";
                receive_hour = "-";
            }
        } else {
            receive_time = "-";
            receive_date = "-";
            receive_hour = "-";
        }
    }

    //String sessionid;       //会话ID，由uuid及累计会话次数组合而成
    public void sessionidProcess(String _sessionid) {
        sessionid = _sessionid;
    }

    //String session_times;   //累计会话（启动）的次数
    public void session_timeProcess(String _session_times) {
        session_times = _session_times;
    }

    //String csvp;            //本次会话内访问页面的次数    short for (current_session_visit_pages)
    public void csvpProcess(String _csvp) {
        csvp = _csvp;
    }

    public static void main(String args[]) throws IOException {

        String log = "rurl=not set-|-|os=Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0-|-|ct1=1381456273688-|-|sr=h1080!l1920-|-|url=http://www.ceair.com/?utm_source=Google&utm_medium=cpc&utm_term=%E4%B8%9C%E6%96%B9%E8%88%AA%E7%A9%BA%E5%85%AC%E5%8F%B8&gclid=CO-s5I3klboCFRF34godsRUASQ-|-|bt=firefox/24.0-|-|ip=211.23.45.67-|-|ct2=1381456280059";
        EtlProcess etlProcess = new EtlProcess();
        ClickStreamLog clickStreamLog = etlProcess.preProcess(log);
        System.out.println(StringCoding_Format.stringDecoding(log));
        System.out.println(clickStreamLog.format2Reduce());
    }
}