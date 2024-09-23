import java.util.Comparator;

/**
 * class implements Queue
 *
 * @author Siyuan
 * @Date 2024/9/23 15:28
 */
public class PriorityQueue<E extends Comparable> implements Queue<E> {

    private final MinHeap<E> data;

    public PriorityQueue() {
        data = new MinHeap<>();
    }

    public PriorityQueue(Comparator comparator) {
        data = new MinHeap<>(comparator);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean offer(E e) {
        data.add(e);
        return true;
    }

    @Override
    public E poll() {
        return data.poll();
    }

    @Override
    public E peek() {
        return data.peek();
    }
}
