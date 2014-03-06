package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 10/10/13
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class Constant {
    public static final String SOURCE_PATH = "hdfs://172.16.3.32:9000/user/youfu/input";
    public static final String DESTINATION_PATH = "hdfs://172.16.3.32:9000/user/youfu/output/etl_output";
    public static final String IP_FILE = "hadoop@172.16.3.32:/home/hadoop/youfu/ClickStream/qqwry.dat";
    //public static final String IP_FILE = "/home/youfuli/Documents/IntelliJ/ETLTools/resource/qqwry.dat";
    public static final String SEPARATION_CHARACTER = "-\\|-\\|";
    public static final String MR_SEPARATION = "-#-#-#-";
    public static final List<String> PROVINCE_LIST = new ArrayList<String>();

    static {
        PROVINCE_LIST.add("北京市");
        PROVINCE_LIST.add("天津市");
        PROVINCE_LIST.add("上海市");
        PROVINCE_LIST.add("重庆市");
        PROVINCE_LIST.add("河北省");
        PROVINCE_LIST.add("山西省");
        PROVINCE_LIST.add("辽宁省");
        PROVINCE_LIST.add("吉林省");
        PROVINCE_LIST.add("黑龙江省");
        PROVINCE_LIST.add("江苏省");
        PROVINCE_LIST.add("浙江省");
        PROVINCE_LIST.add("安徽省");
        PROVINCE_LIST.add("福建省");
        PROVINCE_LIST.add("江西省");
        PROVINCE_LIST.add("山东省");
        PROVINCE_LIST.add("河南省");
        PROVINCE_LIST.add("湖北省");
        PROVINCE_LIST.add("湖南省");
        PROVINCE_LIST.add("广东省");
        PROVINCE_LIST.add("海南省");
        PROVINCE_LIST.add("四川省");
        PROVINCE_LIST.add("贵州省");
        PROVINCE_LIST.add("云南省");
        PROVINCE_LIST.add("台湾省");
        PROVINCE_LIST.add("陕西省");
        PROVINCE_LIST.add("甘肃省");
        PROVINCE_LIST.add("青海省");
        PROVINCE_LIST.add("西藏自治区");
        PROVINCE_LIST.add("新疆维吾尔自治区");
        PROVINCE_LIST.add("广西壮族自治区");
        PROVINCE_LIST.add("内蒙古自治区");
        PROVINCE_LIST.add("宁夏回族自治区");
        PROVINCE_LIST.add("香港特别行政区");
        PROVINCE_LIST.add("澳门特别行政区");

        PROVINCE_LIST.add("北京");
        PROVINCE_LIST.add("天津");
        PROVINCE_LIST.add("上海");
        PROVINCE_LIST.add("重庆");
        PROVINCE_LIST.add("河北");
        PROVINCE_LIST.add("山西");
        PROVINCE_LIST.add("辽宁");
        PROVINCE_LIST.add("吉林");
        PROVINCE_LIST.add("黑龙江");
        PROVINCE_LIST.add("江苏");
        PROVINCE_LIST.add("浙江");
        PROVINCE_LIST.add("安徽");
        PROVINCE_LIST.add("福建");
        PROVINCE_LIST.add("江西");
        PROVINCE_LIST.add("山东");
        PROVINCE_LIST.add("河南");
        PROVINCE_LIST.add("湖北");
        PROVINCE_LIST.add("湖南");
        PROVINCE_LIST.add("广东");
        PROVINCE_LIST.add("海南");
        PROVINCE_LIST.add("四川");
        PROVINCE_LIST.add("贵州");
        PROVINCE_LIST.add("云南");
        PROVINCE_LIST.add("台湾");
        PROVINCE_LIST.add("陕西");
        PROVINCE_LIST.add("甘肃");
        PROVINCE_LIST.add("青海");
        PROVINCE_LIST.add("西藏");
        PROVINCE_LIST.add("新疆");
        PROVINCE_LIST.add("广西");
        PROVINCE_LIST.add("内蒙古");
        PROVINCE_LIST.add("宁夏");
        PROVINCE_LIST.add("香港");
        PROVINCE_LIST.add("澳门");

    }
}
