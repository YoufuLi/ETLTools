package mapred;


import etlprocess.EtlProcess;
import model.ClickStreamLog;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 10/9/13
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.map.MultithreadedMapper;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import util.Constant;

public class ClickStreamETL {
    public static class EtlMapper extends Mapper<Object, Text, Text, Text> {
        private Text word = new Text("-");
        private Text val = new Text("-");

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            if (value != null && value.toString().isEmpty())
                return;
            String tmp = value.toString();
            ClickStreamLog clickStreamLog = null;
            EtlProcess etlProcess = new EtlProcess();
            if ((clickStreamLog = etlProcess.preProcess(tmp)) == null) {
                word.set("error--##not valid log error"); //转换出错的输出
                val.set(value.toString());
                context.write(word, val);
            } else {

                String tmpStr = clickStreamLog.format2Reduce();
                val.set(tmpStr);
                String[] data = tmpStr.split(Constant.MR_SEPARATION);
                //符合格式的正常日志key由uuid,sessionid和visit_timestamp组成
                word.set(data[4] + "u-and-s" + data[5] + "--##" + data[12]);
                context.write(word, val);
            }
        }
    }

    //map的分发函数，相同的num分配在同1个reduce
    public static class CustomPartitioner extends Partitioner<Text, Text> {
        @Override
        public int getPartition(Text k, Text v, int parts) {
            String term = "-";
            if (k != null) {
                //正常日志把uuid和sessionid做为分发依据，保证同一uuid和sessionid在同一台机器处理
                term = k.toString().split("--##")[0];
            }
            int num = (term.hashCode() & Integer.MAX_VALUE) % parts;
            if (term.equals("error")) {
                num = (k.toString().split("--##")[1].hashCode() & Integer.MAX_VALUE) % parts;
            }
            // ------------关键点：给key加num以至于reduce不会覆盖----------
            k.set(k.toString() + "--##" + num);
            return num;

        }
    }

    //分组比较
    public static class GroupingComparator extends WritableComparator {
        protected GroupingComparator() {
            super(Text.class, true);
        }

        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            return w1.toString().compareTo(w2.toString());
        }
    }

    //key的排序类
    public static class SortComparator extends WritableComparator {
        protected SortComparator() {
            super(Text.class, true);
        }

        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            String[] comp1 = w1.toString().split("--##");
            String[] comp2 = w2.toString().split("--##");
            long re = 0;
            if (comp1 != null && comp2 != null) {
                re = comp1[0].compareTo(comp2[0]);
                if (re == 0 && comp1.length > 1 && comp2.length > 1) {
                    //同一sessionid和appkey相同的时间戳排序
                    try {
                        long ct1 = Long.parseLong(comp1[1]);
                        long ct2 = Long.parseLong(comp2[1]);
                        re = ct1 - ct2;
                        if (re == 0) {
                            return 0;
                        } else {
                            return re > 0 ? 1 : -1;
                        }
                    } catch (NumberFormatException e) {
                        return 1;
                    }

                }
                return re > 0 ? 1 : -1;
            }
            return 1;
        }
    }

    public static class EtlRecucer extends Reducer<Text, Text, Text, Text> {
        Text val = new Text();
        public static int num = 0;
        public static String se = "";

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            if (!se.equals(key.toString().split("--##")[0])) {
                se = key.toString().split("--##")[0];
                num = 0;
            }
            for (Text val : values) {
                num += 1;
                String[] valuelist = val.toString().split(Constant.MR_SEPARATION);
                if (valuelist.length < 22) {
                    //key.set(key.toString() + "--##error");
                    key.set("--##error");
                    context.write(key, val);
                    continue;
                }
                String re = "";
                //key.set(key.toString() + "--##" + valuelist[0]);
                valuelist[7] = "" + num;
                key.set(valuelist[0]);
                for (int i = 1; i < valuelist.length; i++) {
                    if (valuelist[i] == null || valuelist[i].isEmpty()) {
                        valuelist[i] = "-";
                    }
                    re += valuelist[i] + "\t";
                }
                val.set(re);

                context.write(key, val);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        System.out.println("begin run ClickStream");
        Job job = new Job(conf, "ClickStream ETL");
        job.setJarByClass(ClickStreamETL.class);
        job.setMapperClass(EtlMapper.class);
        job.setReducerClass(EtlRecucer.class);
        job.setPartitionerClass(CustomPartitioner.class);
        job.setSortComparatorClass(SortComparator.class);
        job.setGroupingComparatorClass(GroupingComparator.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(Constant.SOURCE_PATH));
        FileOutputFormat.setOutputPath(job, new Path(Constant.DESTINATION_PATH));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
