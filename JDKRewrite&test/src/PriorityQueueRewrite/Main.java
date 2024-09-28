package PriorityQueueRewrite;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        // MinHeap Function Test
        int n = 1000_0000; // 随机生成1000W个数据存储到小顶堆中
        MinHeap<Integer> heap = new MinHeap<>(n, Comparator.comparingInt(a -> a)); // lambda
        long MinHeapStartTime = System.nanoTime();
        // 往堆中随机存放1000W个元素
        for (int i = 0; i < n; i++) {
            heap.add(ThreadLocalRandom.current().nextInt(0, n)); // ThreadRandom java7中引入的一个类用来生成随机数，线程安全的（Random类不安全，T继承Random）
            // current是该类的一个静态方法，返回线程ThreadLocalRandom实例（第一次调用则new一个）
            // nextInt 生成一个随机数，接受两个参数，随机数范围为[a, b)

        }

        int [] arr = new int[n];

        // 进行出队操作，将元素存到数组中
        for (int i = 0; i < n; i++) {
            arr[i] = heap.poll();
        }

        // 循环遍历，如果前一个元素比后一个元素大，说明小顶堆有问题（小顶堆存储结构是无序的，逻辑结构是根小于左右孩子的完全二叉树）
        for (int i = 1; i < n; i++) {
            if (arr[i - 1] > arr[i]) {
//                System.out.printf("i : %d,arr[%d] = %d\n", i, arr[i - 1], arr[i]);
                throw new RuntimeException("err heap");
            }
        }
        long MinHeapEndTime = System.nanoTime();
        System.out.println("PriorityQueue is fine and execute in " + (MinHeapEndTime - MinHeapStartTime) / 100_0000 + "ms") ;

        // PriorityQueue Function Test
//        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
//        long startTime = System.nanoTime();
//        for (int i = 0; i < n; i++) {
//            priorityQueue.offer(ThreadLocalRandom.current().nextInt(1, n));
//        }
//
//        // 将优先队列中数据按照优先级去除并存到数组中
//        int[] arr = new int[n];
//
//        for (int i = 0; i < n; i++) {
//            arr[i] = priorityQueue.poll();
//        }
//
//        // 如果前一个元素大于后一个元素，则说明优先队列排列有问题
//        for (int i = 1; i < n; i++) {
//            if (arr[i - 1] > arr[i]) {
//                throw new RuntimeException("error PriorityQueue");
//            }
//        }
//        long endTime = System.nanoTime();
//        System.out.println("PriorityQueue is fine and execute in " + (endTime - startTime) / 100_0000 + "ms");
    }
}