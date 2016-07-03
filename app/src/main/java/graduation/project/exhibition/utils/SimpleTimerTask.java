package graduation.project.exhibition.utils;

import android.app.Activity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时任务
 */
public class SimpleTimerTask {

    /**
     * 执行在主线程
     */
    public static void newUITask(final Activity activity, long delay, long period, final Task task) {

        newTask(delay, period, new Task() {
            @Override
            public void onSchedule(final Timer timer) {

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            task.onSchedule(timer);
                        }
                    });
                }
            }

        });

    }

    /**
     * 执行在子线程
     */
    public static void newTask(long delay, long period, final Task task) {

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.onSchedule(timer);
            }

        }, delay, period);

    }

    public interface Task {
        void onSchedule(Timer timer);
    }

}
