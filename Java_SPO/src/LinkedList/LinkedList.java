package LinkedList;

public class LinkedList {
    private Interceptor first;
    private Interceptor last;
    private int size = 0;
    private Interceptor header;

    public LinkedList() {
        header = new Interceptor(last, first, null);
        first = header;
        last = header;
    }

    public void add(Object element) {
        if (size == 0) {
            last = new Interceptor(last, element);
            first = last;
        } else {
            last = new Interceptor(last, element);
        }
        last.setNext(header);
        header.setPrev(last);
        size++;
    }

    private Interceptor getInteceptor(int index) {
        check_it(index);
        Interceptor x;
        int count;
        if (index < (size >> 1)) {
            x = first;
            count = 0;
            while (index != count) {
                x = x.getNext();
                count++;
            }
            return x;
        } else {
            x = last;
            count = size - 1;
            while (index != count) {
                x = x.getPrev();
                count--;
            }
            return x;
        }
    }

    public Object get(int index) {
        return getInteceptor(index).getItem();
    }

    private void remove(int index) {
        Interceptor interceptor = getInteceptor(index);
        Interceptor prev = interceptor.getPrev();
        Interceptor next = interceptor.getNext();
        if (prev == header) {
            first = next;
            header.setNext(first);
        } else {
            prev.setNext(next);
            interceptor.setPrev(null);
        }
        if (next == header) {
            last = prev;
            header.setPrev(last);
        } else {
            next.setPrev(prev);
            interceptor.setNext(null);
        }
        interceptor.setItem(null);
        size--;
    }

    private void check_it(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException("It is not true index ");
    }

    public void remove(Object rem) {
        remove(get_for_func(rem));
    }

    public boolean contains(Object con) {
        return get_for_func(con) != -1;
    }

    private int get_for_func(Object o) {
        int count = 0;
        Interceptor x = first;
        if (size() != 0) {
            while (!x.getItem().equals(o)) {
                x = x.getNext();
                count++;
                if (count == size) {
                    break;
                }
            }
        }
        return count < size ? count : -1;
    }

    public void del(Object object) {
        int index = get_for_func(object);
        set(index, object);
    }

    public void set(int index, Object object) {
        getInteceptor(index).setItem(object);
    }

    public int size() {
        return size;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

}