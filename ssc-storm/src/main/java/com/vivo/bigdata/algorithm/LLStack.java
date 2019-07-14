package com.vivo.bigdata.algorithm;

import java.util.EmptyStackException;


//使用链表实现栈
public class LLStack {


    private BinaryTreeNode headNode;

    public LLStack() {
        this.headNode = new BinaryTreeNode(null);
    }


    public void push(BinaryTreeNode data) {
        if (headNode == null)
            headNode = new BinaryTreeNode(data.getData());
        else if (headNode.getData() == null) {
            headNode.setData(data.getData());
        } else {
            BinaryTreeNode llNode = new BinaryTreeNode(data.getData());
            llNode.setLeft(headNode);
            headNode = llNode;
        }
    }

    public BinaryTreeNode top() {
        if (headNode == null)
            return null;
        else return headNode;
    }

    public BinaryTreeNode pop() {
        if (headNode == null) {
            throw new EmptyStackException();
        } else {
            Integer data = headNode.getData();
            headNode = headNode.getLeft();
            return new BinaryTreeNode(data);
        }
    }

    public boolean isEmpty() {
        if (headNode == null) return true;
        else return false;
    }


    public void deleteStack() {
        headNode = null;
    }


}
