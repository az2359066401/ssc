package com.vivo.bigdata.algorithm;


//基于链表实现队列
public class LLQueue {


    private LLNode frontNode;

    private LLNode rearNode;

    public LLQueue() {
        this.frontNode = null;
        this.rearNode = null;
    }

    public static LLQueue createLLQueue() {
        return new LLQueue();
    }

    public boolean isEmpty() {
        return (frontNode == null);
    }

    public void enQueue(BinaryTreeNode data) {
        LLNode newNode = new LLNode(data.getData());
        if (rearNode != null) {
            rearNode.setNext(newNode);
        }
        rearNode = newNode;
        if (frontNode == null) {
            frontNode = rearNode;
        }

    }


    public BinaryTreeNode deQueue() {
        Integer data = null;
        if (isEmpty()) {
//            throw new EmptyQueueException("Queue Empty");
        } else {
            data = frontNode.getData();
            frontNode = frontNode.getNext();
        }
        return new BinaryTreeNode(data);
    }

    public void deleteQueue() {
        this.frontNode = null;
        this.rearNode = null;
    }
}
