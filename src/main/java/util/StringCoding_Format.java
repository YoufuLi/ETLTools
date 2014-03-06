package util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 10/15/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCoding_Format {
    public StringCoding_Format() {

    }

    public static String stringFormat(String _srcString) {
        String destString = "";
        destString = _srcString.replace("\t", "");
        destString = destString.replace("\t", "");
        return destString;
    }

    public static String stringEncoding(String _srcString) {
        String destString = "";
        try {
            destString = URLEncoder.encode(_srcString, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            destString = _srcString;
        }
        return destString;
    }

    public static String stringDecoding(String _srcString) {
        String destString = "";
        try {
            destString = URLDecoder.decode(_srcString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            destString = _srcString;
        }
        return destString;

    }

    public static void main(String[] args) {
        System.out.println(StringCoding_Format.stringEncoding("你好"));
        System.out.println(StringCoding_Format.stringDecoding("%E4%B8%9C%E6%96%B9%E8%88%AA%E7%A9%BA%E5%85%AC%E5%8F%B8"));
        System.out.println(StringCoding_Format.stringDecoding("\u7701"));
    }
}
