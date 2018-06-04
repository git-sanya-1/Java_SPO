package HashSet;
import LinkedList.LinkedList;
public class HashSet {

    private int size = 1;

    private LinkedList listing[];

    public HashSet() {
        listing = new LinkedList[size];
    }


    private static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }


    private static int indexFor(int h, int length) {
        return h & (length - 1);
    }

    private boolean check(int index, Object val1, String value) {
        if (listing[index] != null) {
            for (int i = 0; i < listing[index].size(); i++) {
                int hash = hash(listing[index].get(i).hashCode());
                if (hash == hash(val1.hashCode()) && listing[index].contains(val1)) {
                    switch (value) {
                        case "add":
                            listing[index].del(val1);
                            return true;
                        case "remove":
                            listing[index].remove(val1);
                            return true;
                        case "contains":
                            return listing[index].contains(val1);
                        default:
                            break;
                    }
                }
            }
        }
        return false;
    }

    public void add(Object object) {
        int index = indexFor(hash(object.hashCode()), size);
        if (!check(index, object, "add")) {
            listing[index] = new LinkedList();
            listing[index].add(object);
        }
    }

    public void remove(Object object) {
        int index = indexFor(hash(object.hashCode()), size);
        if (!check(index, object, "remove")) {
            System.out.println("This object dont have in program");
        }
    }

    public boolean contains(Object object) {
        int index = indexFor(hash(object.hashCode()), size);
        return check(index, object, "contains");
    }
}