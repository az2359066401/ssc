package com.vivo.bigdata.algorithm;

public class BinaryTreeNode{


    private Integer data;

    private BinaryTreeNode right;

    private BinaryTreeNode left;


    public BinaryTreeNode(Integer data) {
        this.data = data;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode right) {
        this.right = right;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode left) {
        this.left = left;
    }
}
