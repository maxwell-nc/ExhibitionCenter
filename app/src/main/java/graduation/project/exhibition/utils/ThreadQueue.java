package graduation.project.exhibition.utils;

import android.app.Activity;

import java.util.LinkedHashMap;

/**
 * 线程任务队列
 */
public class ThreadQueue {

    /**
     * 创建新队列
     */
    public static Builder newQueue(Activity activity) {
        return new Builder(activity);
    }

    /**
     * 生成任务但不执行
     */
    public Thread generateQueue(final Builder builder) {
        return new Thread() {

            private Boolean mKey;

            @Override
            public void run() {
                for (Boolean key : builder.taskMap.keySet()) {

                    mKey = key;//使用成员变量而不是final，防止值不变

                    if (mKey) {//主线程

                        builder.activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                builder.taskMap.get(mKey).run();
                            }
                        });

                    } else {//子线程
                        builder.taskMap.get(mKey).run();
                    }

                }

            }
        };
    }

    public static class Builder {

        Activity activity;
        LinkedHashMap<Boolean, Task> taskMap = new LinkedHashMap<>();

        /**
         * 依附的Activity
         */
        public Builder(Activity activity) {
            this.activity = activity;
        }

        /**
         * 主线程执行任务
         */
        public Builder onUiThread(Task task) {
            taskMap.put(true, task);
            return this;
        }

        /**
         * 子线程执行任务
         */
        public Builder onChildThread(Task task) {
            taskMap.put(false, task);
            return this;
        }

        /**
         * 在子线程睡眠等待
         */
        public Builder sleep(final long ms) {

            if (ms < 0) {
                return this;
            }

            taskMap.put(false, new Task() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(ms);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });

            return this;
        }

        /**
         * 执行任务队列
         */
        public void exec() {
            new ThreadQueue().generateQueue(this).start();
        }

    }

    /**
     * 执行的任务
     */
    public interface Task {
        /**
         * 要执行的操作
         */
        void run();
    }

}
