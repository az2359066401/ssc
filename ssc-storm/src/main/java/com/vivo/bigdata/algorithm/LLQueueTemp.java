package com.vivo.bigdata.algorithm;


//基于链表实现队列
public class LLQueueTemp {


    private LLNode frontNode;

    private LLNode rearNode;

    public LLQueueTemp() {
        this.frontNode = null;
        this.rearNode = null;
    }

    public static LLQueueTemp createLLQueue() {
        return new LLQueueTemp();
    }

    public boolean isEmpty() {
        return (frontNode == null);
    }

    public void enQueue(int data) {
        LLNode newNode = new LLNode(data);
        if (rearNode != null) {
            rearNode.setNext(newNode);
        }
        rearNode = newNode;
        if (frontNode == null) {
            frontNode = rearNode;
        }

    }


    public Integer deQueue() {
        Integer data = null;
        if (isEmpty()) {
//            throw new EmptyQueueException("Queue Empty");
        } else {
            data = frontNode.getData();
            frontNode = frontNode.getNext();
        }
        return data;
    }
}
