package com.vivo.bigdata.algorithm;

public class LLNode {


    private Integer data;

    private LLNode next;

    private LLNode previous;

    public LLNode(Integer data){
        this.data = data;
    }


    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public LLNode getNext() {
        return next;
    }

    public void setNext(LLNode next) {
        this.next = next;
    }

    public LLNode getPrevious() {
        return previous;
    }

    public void setPrevious(LLNode previous) {
        this.previous = previous;
    }
}
