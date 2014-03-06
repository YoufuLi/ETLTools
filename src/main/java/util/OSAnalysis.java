package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 10/16/13
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class OSAnalysis {
    public static String regexStr_Windows = "(Windows)\\s(NT)\\s[0-9]{1,2}(.)[0-9]{1,4}";
    public static String regexStr_Linux = "(Linux)\\s[a-zA-Z]{1,2}[0-9]{1,4}_?[0-9]*";
    public static String regexStr_Mac = "[a-zA-Z]*\\s?(Mac)\\s(OS)\\s(X)\\s?[0-9a-zA-Z.]*";
    public static String regexStr_Android = "(Android)\\s[0-9]{1,2}(.)[0-9]{1,4}";
    public static String regexStr_IOS = "[a-zA-Z]*\\s?(iPhone OS)\\s?[0-9]*(_)?[0-9]*(_)?[0-9]*\\s?[a-zA-Z]*\\s?[a-zA-Z]*\\s?[a-zA-Z]*\\s?[a-zA-Z]*";

    public static String getOS(String _os) {
        String OSType = "-";
        if (!getWindows(_os).equals("-")) {
            OSType = getWindows(_os);
        } else if (!getLinux(_os).equals("-")) {
            OSType = getLinux(_os);
        } else if (!getIOS(_os).equals("-")) {
            OSType = getIOS(_os);
        } else if (!getMac(_os).equals("-")) {
            OSType = getMac(_os);
        } else if (!getAndroid(_os).equals("-")) {
            OSType = getAndroid(_os);
        }
        return OSType;
    }

    public static String getWindows(String _os) {
        String os_windows = "-";
        Matcher matcher = Pattern.compile(regexStr_Windows).matcher(_os);
        if (matcher.find()) {
            os_windows = matcher.group();
        }
        return os_windows;
    }

    public static String getLinux(String _os) {
        String os_linux = "-";
        Matcher matcher = Pattern.compile(regexStr_Linux).matcher(_os);
        if (matcher.find()) {
            os_linux = matcher.group();
        }
        return os_linux;
    }

    public static String getMac(String _os) {
        String os_mac = "-";
        Matcher matcher = Pattern.compile(regexStr_Mac).matcher(_os);
        if (matcher.find()) {
            os_mac = matcher.group();
        }
        return os_mac;
    }

    public static String getAndroid(String _os) {
        String os_addroid = "-";
        Matcher matcher = Pattern.compile(regexStr_Android).matcher(_os);
        if (matcher.find()) {
            os_addroid = matcher.group();
        }
        return os_addroid;
    }

    public static String getIOS(String _os) {
        String os_ios = "-";
        Matcher matcher = Pattern.compile(regexStr_IOS).matcher(_os);
        if (matcher.find()) {
            os_ios = matcher.group();
        }
        return os_ios;
    }


    public static void main(String[] args) {
        String osStr = "(iPhone; CPU iPhone OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko)";
        System.out.println(OSAnalysis.getOS(osStr));
    }
}
