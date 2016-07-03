package pres.nc.maxwell.asynchttp.log;

import java.util.ArrayList;

/**
 * 日志工具类
 */
public class LogUtils {

    /**
     * 日志信息
     */
    private Log mLog;

    private LogUtils(Log log) {
        this.mLog = log;
    }

    /**
     * 创建日志
     * @return 日志信息
     */
    public static Log log() {
        return new Log();
    }

    /**
     * 打印日志队列
     */
    public void execute() {
        for (String msg : mLog.msgList) {
            android.util.Log.println(mLog.priority, mLog.tag, msg);
        }
    }

    /**
     * 日志信息
     */
    public static class Log {

        /**
         * 打印等级
         */
        int priority;

        /**
         * 日志标记
         */
        String tag;

        /**
         * 日志队列
         */
        ArrayList<String> msgList;

        /**
         * 设置日志标记
         * @param tag 标记
         * @return 日志信息
         */
        public Log tag(String tag) {
            this.tag = tag;
            return this;
        }

        /**
         * 添加日志
         * @param msg 日志内容
         * @return 日志信息
         */
        public Log addMsg(String msg) {
            if (msgList == null) {
                msgList = new ArrayList<>();
            }
            msgList.add(msg);
            return this;
        }

        /**
         * 设置日志等级
         * @param priority 日志等级
         * @return 日志信息
         */
        public Log priority(int priority) {
            this.priority = priority;
            return this;
        }

        /**
         * 生成日志信息工具类
         * @return 日志信息工具类
         */
        public LogUtils build() {
            return new LogUtils(this);
        }

    }

}
