package LinkedList;

class Interceptor {
    private Object item;
    private Interceptor prev;
    private Interceptor next;

    Interceptor(Interceptor last, Object element){
        last.next = this;
        this.item = element;
        this.prev = last;
    }
    Interceptor(Interceptor last, Interceptor first, Object element){
        this.item = element;
        this.prev = last;
        this.next = first;
    }

    Object getItem(){
        return item;
    }

    Interceptor getPrev(){
        return prev;
    }
    Interceptor getNext(){
        return next;
    }

    void setNext(Interceptor next) {
        this.next = next;
    }

    void setPrev(Interceptor prev) {
        this.prev = prev;
    }

    void setItem(Object item) {
        this.item = item;
    }
}