import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * MinHeap class
 *
 * @author Siyuan
 * @Date 2024/9/21 17:42
 */
public class MinHeap<E> {
    /**
     * JDK使用动态数组来实现二叉堆，一是利用随机访问特点，二是其出队入队操作都在O（logn），链表需要额外空间存储节点间父子关系
     * 左右孩子求解 2i + 1 和 2i + 2 （索引从0开始）
     * parentIndex = (leftIndex/rightIndex - 1) / 2向下取整
     */
    // 元素是可比较的小顶堆
    private List<E> list; // 存放元素的列表（底层基于数组）
    private Comparator<E> comparator; // 表示比较对象，如果为空，使用自然排序

    /**
     * 创建一个空的最小堆
     */
    public MinHeap() {
        list = new ArrayList<>();
    }

    /**
     * 创建一个空的最小堆，并指定比较器来决定元素之间的顺序
     * 调用MinHeap时传入自定义类的自定义比较器
     */
    public MinHeap(Comparator<E> comparator) {
        list = new ArrayList<>();
        this.comparator = comparator;
    }

    /**
     * 参数为数组，将给定的数组转换为一个最小堆
     */
    public MinHeap(E[] arr) {
        list = new ArrayList<>(Arrays.asList(arr));
        heapify();
    }

    /**
     * 创建指定容量的最小堆
     */
    public MinHeap(int capacity) {
        list = new ArrayList<>(capacity);
    }

    /**
     * 创建指定容量的最小堆并指定比较器
     */
    public MinHeap(int capacity, Comparator<E> comparator) {
        list = new ArrayList<>(capacity);
        this.comparator = comparator;
    }

    /**
     * 返回堆中元素个数
     */
    public int size() {
        return list.size();
    }

    /**
     * 判断堆元素是否为空
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 获取当前节点的父节点索引
     */
    private int parentIndex(int childIndex) {
        if (childIndex == 0) {
            throw new IllegalArgumentException();
        }

        return (childIndex - 1) / 2;
    }

    /**
     * 返回当前节点的左子节点
     */
    private int leftChildIndex(int index) {
        return 2 * index + 1; // 索引从0开始
    }

    /**
     * 返回当前节点的右子节点
     */
    private int rightChildIndex(int index) {
        return 2 * index + 2;
    }

    /**
     * 将元素存到小顶堆中
     * 元素插入到ArrayList数组的最后，但是考虑到小顶堆的性质，需要进行向上调整（插入）
     */
    public void add(E e) {
        list.add(e);
        siftUp(list.size() - 1);
    }

    /**
     * siftUp向上调整，保持平衡
     * 首先检查是否定义了比较器comparator。
     * 如果定义了比较器，那么堆中的元素类型可能不是实现了Comparable接口的类，因此需要使用comparator来比较元素的大小
     * 如果没有定义比较器，那么假设堆中的元素都是实现了Comparable接口的类，可以直接使用元素的compareTo方法来比较大小
     */
    private void siftUp(int index) {
        if (comparator != null) {
            // 循环从新增节点开始，自底向上比较子节点和父节点大小，如果父节点大于子节点则交换两者的值
            while (index > 0 && comparator.compare(list.get(parentIndex(index)), list.get(index)) > 0) { // cpmpare -> A < B 返回小于0
                E tempElement = list.get(index);
                list.set(index, list.get(parentIndex(index)));
                list.set(parentIndex(index), tempElement);
                index = parentIndex(index);
            }
        } else {
            // 循环从新增节点开始，自底向上比较子节点和父节点大小，如果父节点大于子节点则交换两者的值
            E tempElement = list.get(index);
            list.set(index, list.get(parentIndex(index)));
            list.set(parentIndex(index), tempElement);
            index = parentIndex(index);
        }
    }

    /**
     * 获取小顶堆堆顶元素（元素也出队） peek则只是获取
     */
    public E poll() {
        if (list == null || list.isEmpty()) {
            return null;
        }
        E ret = list.get(0);
        list.set(0, list.get(list.size() - 1));
        list.remove(list.size() - 1);
        siftDown(0);
        return ret;
    }

    /**
     * 将数组转为堆，在O(n)时间复杂度内完成建堆
     */
    private void heapify() {
        // 找到最后一个非叶子节点，往前遍历
        for (int i = parentIndex(list.size() - 1); i >= 0; i--) {
            // 对每个非叶子节点执行siftDown,使其范围内保持小顶堆特性
            siftDown(i);
        }
    }

    /**
     * 向下调整siftDown
     */
    private void siftDown(int index) {
        if (comparator != null) {
            // 如果左节点(左孩子)小于数组大小才进入循环，避免数组越界
            while (leftChildIndex(index) < list.size()) {
                // 获取左孩子索引
                int cmpIndex = leftChildIndex(index);

                // 获取左或者右子节点中值更小的索引，如果右孩子更小，会把cmpIndex更新为右孩子的索引
                if (rightChildIndex(index) < list.size() &&
                        comparator.compare(list.get(leftChildIndex(index)), list.get(rightChildIndex(index))) > 0) { // A > B return 1 -> leftChildkey > rightChildkey
                    cmpIndex = rightChildIndex(index);
                }

                // 如果父节点比左右孩子中小的子节点小则停止比较，结束循环
                if (comparator.compare(list.get(index), list.get(cmpIndex)) <= 0) {
                    break;
                }

                // 反之交换位置，将index更新为交换后的索引index，进入下一个循环
                E tmpElement = list.get(cmpIndex);
                list.set(cmpIndex, list.get(index));
                list.set(index, tmpElement);

                index = cmpIndex; // 向下调整，不满足特性可能向下传递，完成本次循环之后还要检查更新之后是否符合小顶堆性质
            }
        } else {
            // 如果左节点小于数组大小才进入循环，避免数组越界
            while (leftChildIndex(index) < list.size()) {
                // 获取左索引
                int cmpIndex = leftChildIndex(index);

                // 获取左或者右子节点中值更小的索引
                if (rightChildIndex(index) < list.size() &&
                        ((Comparable) list.get(leftChildIndex(index))).compareTo(list.get(rightChildIndex(index))) > 0) {
                    cmpIndex = rightChildIndex(index);
                }

                /**
                *((Comparable) list.get(leftChildIndex(index)))显示转换将list.get(leftChildIndex(index))结果<list.get()返回Object类型不能直接调用compareTo>
                *转换为Comparable类型，来调用compareTo方法
                */

                // 如果父节点比子节点小则停止比较，结束循环
                if (((Comparable) list.get(index)).compareTo(list.get(cmpIndex)) <= 0) {
                    break;
                }

                // 反之交换位置，将index更新为交换后的索引index，进入下一个循环
                E tmpElement = list.get(cmpIndex);
                list.set(cmpIndex, list.get(index));
                list.set(index, tmpElement);

                index = cmpIndex;
            }
        }
    }

    /**
     * 查看堆顶元素但不移除的方法peek(),堆顶元素存储在index = 0的位置
     */
    public E peek() {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static void main(String[] args) {
        // MinHeap Test Function
        int n = 100_0000; // 随机生成1000W个数据存储到小顶堆中
        MinHeap<Integer> heap = new MinHeap<>(n, Comparator.comparingInt(a -> a)); // lambda

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
    }
}
