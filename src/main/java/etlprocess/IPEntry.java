package etlprocess;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 10/11/13
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 * 一条IP范围记录，不仅包括国家和区域，也包括起始IP和结束IP *
 */
public class IPEntry {
    public String beginIp;
    public String endIp;
    public String country;
    public String area;

    /**
     * 构造函数
     */
    public IPEntry() {
        beginIp = endIp = country = area = "";
    }

    public String toString() {
        return this.area + "  " + this.country + "IP范围:" + this.beginIp + "-" + this.endIp;
    }
}
