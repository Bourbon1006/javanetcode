
package echo;
import java.util.concurrent.*;

public class MaxFinder {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] arr = {2,8,1,6,10,9,5,3,25};

        // 创建一个线程池
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // 创建一个 Callable 对象来查找数组的最大值
        Callable<Integer> task = () -> {
            int max = arr[0];
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] > max) {
                    max = arr[i];
                }
            }
            return max;
        };

        // 提交任务并获得 Future 对象
        Future<Integer> future = executor.submit(task);

        // 等待任务完成并获取结果
        int max = future.get();

        // 输出结果
        System.out.println("数组的最大值是：" + max);

        // 关闭线程池
        executor.shutdown();
    }
}

