import java.util.HashMap;
import java.util.Map;

public class LRU {
    class DLinkedNode {
        //封装链表节点，包含key/value以及上一个和下一个两个指针
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;

        public DLinkedNode(int _key, int _value) {
            this.key = _key;
            this.value = _value;
        }

        public DLinkedNode() {
        }
    }

    //定义HashMap存储缓存中的key和对应的节点
    private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
    //current cache length
    private int size;
    //the length of cache
    private int capacity;
    //linked list : head ,tail
    private DLinkedNode head, tail;

    public LRU(int capacity) {
        //constructor initialize cache mechanism,including the length of cache,total length and node of head and tail
        this.size = 0;
        this.capacity = capacity;
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
       //if key exists,according to the hash table to locate,then move to the head of the link
        moveToHead(node);
        return node.value;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void put(int key,int value){
        DLinkedNode node = cache.get(key);
        if(node==null){
            DLinkedNode newNode = new DLinkedNode(key,value);
            cache.put(key,newNode);
            addToHead(newNode);
            size++;
            if(size>capacity){
                DLinkedNode tail = removeTail();
                cache.remove(tail.key);
                size--;
            }
        }else{
    //if node exists,according to hash table to locate,and move to the head of the linked list
            node.value = value;
            moveToHead(node);
        }
    }

    private DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }
}
