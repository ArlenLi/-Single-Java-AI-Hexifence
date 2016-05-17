package aiproj.hexifence.lindongl;

/**
 * Created by lld on 17/05/2016.
 */
/**
 *
 */

import aiproj.hexifence.Referee;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Happy
 * @me <a href="http://www.weibo.com/qiaolevip">@快乐每一天Joe</a>
 * @mail qiaole1990@sina.com
 * @date 2012-10-20
 */
public class TimerDo {
    public static void main(String[] args) {
        Integer cacheTime = 1000 * 3;
        Timer timer = new Timer();
        // (TimerTask task, long delay, long period)任务，延迟时间，多久执行
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Referee.main(new String[]{"2","lindongl.Lindongl", "lindongl.Lindongl"});
            }
        }, 1000, cacheTime);
    }
}

