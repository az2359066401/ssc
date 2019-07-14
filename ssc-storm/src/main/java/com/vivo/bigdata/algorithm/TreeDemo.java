package com.vivo.bigdata.algorithm;

import com.alibaba.fastjson.JSON;
import com.sun.scenario.effect.Merge;
import org.apache.storm.trident.testing.IFeeder;
import org.apache.storm.trident.testing.TrueFilter;

import java.beans.BeanInfo;
import java.util.Stack;

public class TreeDemo {


    //前序遍历递归
    void PreOrder(BinaryTreeNode root) {
        if (root != null)
            System.out.println(root.getData());
        PreOrder(root.getLeft());
        PreOrder(root.getRight());
    }

    //前序遍历非递归
    void PreOrderNonRecursive(BinaryTreeNode root) {
        if (root == null) return;
        LLStack S = new LLStack();
        while (true) {
            while (root != null) {
                System.out.println(root.getData());
                S.push(root);
                root = root.getLeft();
            }
            if (S.isEmpty())
                break;
            root = (BinaryTreeNode) S.pop();
            root = root.getRight();
        }
        return;
    }

    //中序遍历递归
    void InOrder(BinaryTreeNode root) {
        if (root != null) {
            InOrder(root.getLeft());
            System.out.println(root.getData());
            InOrder(root.getRight());
        }
    }

    //中序遍历非递归
    void InOrderNonRecursive(BinaryTreeNode root) {
        if (root == null) return;
        LLStack S = new LLStack();
        while (true) {
            while (root != null) {
                S.push(root);
                root = root.getLeft();
            }
            if (S.isEmpty())
                break;
            root = (BinaryTreeNode) S.pop();
            System.out.println(root.getData());
            root = root.getRight();

        }
        return;
    }

    //后序遍历递归
    void PostOrder(BinaryTreeNode root) {
        if (root != null) {
            PostOrder(root.getLeft());
            PostOrder(root.getRight());
            System.out.println(root.getData());
        }
    }

    //后序遍历非递归
    void PostOrderNonRecursive(BinaryTreeNode root) {
        LLStack S = new LLStack();
        while (true) {
            if (root != null) {
                S.push(root);
                root = root.getLeft();
            } else {
                if (S.isEmpty()) {
                    System.out.println("Stack is Empty");
                    return;
                } else if (((BinaryTreeNode) S.top()).getRight() == null) {
                    root = (BinaryTreeNode) S.pop();
                    System.out.println(root.getData());
                    if (root == ((BinaryTreeNode) S.top()).getRight()) {
                        System.out.println(S.top());
                        S.pop();
                    }
                }
                if (!S.isEmpty()) {
                    root = ((BinaryTreeNode) S.top()).getRight();
                } else {
                    root = null;
                }

            }
            S.deleteStack();
        }
    }


    //层次遍历
    void LevelOrder(BinaryTreeNode root) {
        BinaryTreeNode temp;
        LLQueue Q = new LLQueue();
        if (root == null)
            return;
        Q.enQueue(root);
        while (!Q.isEmpty()) {
            temp = Q.deQueue();
            //处理当前节点
            System.out.println(temp.getData());
            if (temp.getLeft() != null)
                Q.enQueue(temp.getLeft());
            if (temp.getRight() != null)
                Q.enQueue(temp.getRight());
        }
        Q.deleteQueue();


    }


    //给出查找二叉树中最大元素的算法
    int FindMax(BinaryTreeNode root) {
        int root_val, left, right, max = 0;
        if (root != null) {
            root_val = root.getData();
            left = FindMax(root.getLeft());
            right = FindMax(root.getRight());
            //在三个值中找出最大值
            if (left > right)
                max = left;
            else
                max = right;
            if (root_val > max)
                max = root_val;
        }
        return max;
    }


    //用非递归的方法实现查找二叉树中的最大元素
    int FindMaxUsingLevelOrder(BinaryTreeNode root) {
        BinaryTreeNode temp;
        int max = 0;
        LLQueue Q = new LLQueue();
        Q.enQueue(root);
        while (!Q.isEmpty()) {
            temp = Q.deQueue();

            //所有值中最大值
            if (max < temp.getData())
                max = temp.getData();
            if (temp.getLeft() != null)
                Q.enQueue(temp.getLeft());
            if (temp.getRight() != null)
                Q.enQueue(temp.getRight());
        }
        Q.deleteQueue();
        return max;
    }


    //在给出二叉树中搜索某个元素的算法
    Boolean FindInBinaryTreeUsingRecursion(BinaryTreeNode root, int data) {
        Boolean temp;
        // 基本情况 == 空树 ,在此情况下,数据未找到因此返回false
        if (root == null)
            return false;
        else {//判断是否等于当前根节点的值
            if (data == root.getData())
                return true;
            else {
                //否则从子树继续递归向下搜索
                temp = FindInBinaryTreeUsingRecursion(root.getLeft(), data);
                if (temp != true)
                    return temp;
                else return (FindInBinaryTreeUsingRecursion(root.getRight(), data));
            }
        }
    }


    //利用非递归算法来搜索二叉树中的某个元素
    Boolean SearchUsingLevelOrder(BinaryTreeNode root, int data) {
        BinaryTreeNode temp;
        LLQueue Q = new LLQueue();
        if (root == null) return false;
        Q.enQueue(root);
        while (!Q.isEmpty()) {
            temp = Q.deQueue();
            //判断是否等于当前根节点的值
            if (data == root.getData())
                return true;
            if (temp.getLeft() != null)
                Q.enQueue(temp.getLeft());
            if (temp.getRight() != null)
                Q.enQueue(temp.getRight());
        }
        Q.deleteQueue();
        return false;
    }


    //实现将一个元素插入一个二叉树中的算法
    void InsertBinaryTree(BinaryTreeNode root, int data) {
        LLQueue Q = new LLQueue();
        BinaryTreeNode temp;
        BinaryTreeNode newNode = new BinaryTreeNode(null);
        newNode.setLeft(null);
        newNode.setRight(null);
        if (newNode == null) {
            System.out.println("Memory Error");
            return;
        }
        if (root == null) {
            root = newNode;
            return;
        }
        Q.enQueue(root);
        while (!Q.isEmpty()) {
            temp = Q.deQueue();
            if (temp.getLeft() != null)
                Q.enQueue(temp.getLeft());
            else {
                temp.setLeft(newNode);
                Q.deleteQueue();
                return;
            }
            if (temp.getRight() != null)
                Q.enQueue(temp.getRight());
            else {
                temp.setRight(newNode);
                Q.deleteQueue();
                return;
            }
        }
        Q.deleteQueue();
    }


    //给出获取二叉树节点个数的算法
    int SizeOfBinaryTree(BinaryTreeNode root) {
        if (root == null) return 0;
        else return (SizeOfBinaryTree(root.getLeft()) + 1 + SizeOfBinaryTree(root.getRight()));
    }


    //利用非递归算法获取二叉树节点的算法  层次遍历
    int SizeofBTUsingLevelOrder(BinaryTreeNode root) {
        BinaryTreeNode temp;
        LLQueue Q = new LLQueue();
        int count = 0;
        if (root == null) return 0;
        Q.enQueue(root);
        while (!Q.isEmpty()) {
            temp = Q.deQueue();
            count++;
            if (temp.getLeft() != null)
                Q.enQueue(temp.getLeft());
            if (temp.getRight() != null)
                Q.enQueue(temp.getRight());
        }
        Q.deleteQueue();
        return count;
    }


    //实现删除树的算法
    void DeleteBinaryTree(BinaryTreeNode root) {
        if (root == null)
            return;
        //首先删除两棵子树
        DeleteBinaryTree(root.getLeft());
        DeleteBinaryTree(root.getRight());
        //仅当子树删除后再删除当前节点
        root = null; //在java 中，将由垃圾回收期对其进行清理
    }


    //给出算法 逆向逐层输出树种中的元素

    void LevelOrderTraversalInReverse(BinaryTreeNode root) {
        LLQueue Q = new LLQueue();
        LLStack S = new LLStack();
        BinaryTreeNode temp;
        if (root == null) return;
        Q.enQueue(root);
        if (!Q.isEmpty()) {
            temp = Q.deQueue();
            if (temp.getRight() != null)
                Q.enQueue(temp.getRight());
            if (temp.getLeft() != null)
                Q.enQueue(temp.getLeft());
            S.push(temp);
        }
        while (!S.isEmpty())
            System.out.println(S.pop().getData());
    }


    //求已知二叉树高度(深度)的算法
    int HeightOfBinaryTree(BinaryTreeNode root) {
        int leftheight, rightheight;
        if (root == null) return 0;
        else {
            //计算每颗子树的深度
            leftheight = HeightOfBinaryTree(root.getLeft());
            rightheight = HeightOfBinaryTree(root.getRight());
            if (leftheight > rightheight)
                return (leftheight + 1);
            else return (rightheight + 1);
        }
    }


    //利用非递归算法求二叉树高度（深度）的算法
    int FindHeightofBinaryTree(BinaryTreeNode root) {
        int level = 1;
        LLQueue Q = new LLQueue();
        if (root == null) return 0;
        Q.enQueue(root);
        //第一层结束
        Q.enQueue(null);
        while (Q.isEmpty()) {
            root = Q.deQueue();
            //当前层遍历结束
            if (root == null) {
                if (!Q.isEmpty())
                    Q.enQueue(null);
                level++;
            } else {
                if (root.getLeft() != null)
                    Q.enQueue(root.getLeft());
                if (root.getRight() != null)
                    Q.enQueue(root.getRight());
            }
        }
        return level;
    }


    //实现查找二叉树中最深节点的算法
    BinaryTreeNode DeepesNodeinBinaryTree(BinaryTreeNode root) {
        BinaryTreeNode temp = null;
        LLQueue Q = new LLQueue();
        if (root == null) return null;
        Q.enQueue(root);
        while (!Q.isEmpty()) {
            temp = Q.deQueue();
            if (temp.getLeft() != null)
                Q.enQueue(temp.getLeft());
            if (temp.getRight() != null)
                Q.enQueue(temp.getRight());
        }
        Q.deleteQueue();
        return temp;
    }


    //实现删除二叉树中某元素的算法

    //用非递归算法获取二叉树中叶子节点的个数
    int NumberOfLeavesInBTusingLevelOrder(BinaryTreeNode root) {
        BinaryTreeNode temp;
        LLQueue Q = new LLQueue();
        int count = 0;
        if (root == null)
            return 0;
        Q.enQueue(root);
        while (!Q.isEmpty()) {
            temp = Q.deQueue();
            if (temp.getLeft() != null && temp.getRight() != null)
                count++;
            else {
                if (temp.getLeft() != null)
                    Q.enQueue(temp.getLeft());
                if (temp.getRight() != null)
                    Q.enQueue(temp.getRight());
            }
        }
        Q.deleteQueue();
        return count;
    }


    //用非递归算法实现查找二叉树中满节点的个数
    int NumberOfFullNodesInBTusingLevelOrder(BinaryTreeNode root) {
        BinaryTreeNode temp;
        LLQueue Q = new LLQueue();
        int count = 0;
        if (root == null)
            return 0;
        Q.enQueue(root);
        while (!Q.isEmpty()) {
            temp = Q.deQueue();
            if (temp.getLeft() != null && temp.getRight() != null)
                count++;
            if (temp.getLeft() != null)
                Q.enQueue(temp.getLeft());
            if (temp.getRight() != null)
                Q.enQueue(temp.getRight());
        }
        Q.deleteQueue();
        return count;
    }


    //用非递归算法实现查询二叉树中半节点的个数
    int NumberOfHalfNodesInBTusingLevelOrder(BinaryTreeNode root) {
        BinaryTreeNode temp;
        LLQueue Q = new LLQueue();
        int count = 0;
        if (root == null) return 0;
        Q.enQueue(root);
        while (!Q.isEmpty()) {
            temp = Q.deQueue();
            //可以使用如下判断条件来替代两个temp.getLeft() ^ temp.getRight()
            if (temp.getLeft() != null && temp.getRight() != null || temp.getLeft() != null && temp.getRight() != null)
                count++;
            if (temp.getLeft() != null)
                Q.enQueue(temp.getLeft());
            if (temp.getRight() != null)
                Q.enQueue(temp.getRight());

        }
        Q.deleteQueue();
        return count;
    }


    //对于给定两颗树 判断他们结构是否相同
    Boolean AreStructurullySameTrees(BinaryTreeNode root1, BinaryTreeNode root2) {
        //如果两颗树都为空 =》1
        if (root1 == null && root2 == null)
            return true;
        if (root1 == null || root2 == null)
            return false;
        //若都不为空 进行比较
        return (root1.getData() == root2.getData() &&
                AreStructurullySameTrees(root1.getLeft(), root2.getLeft()) &&
                AreStructurullySameTrees(root1.getRight(), root2.getRight())
        );


    }


    //求二叉树直径的算法   树的直径就是树中两个叶子节点之间的最长路径中的节点个数
    int DiameterOfTree(BinaryTreeNode root, int diameter) {
        int left, right;
        if (root == null) return 0;
        left = DiameterOfTree(root.getLeft(), diameter);
        right = DiameterOfTree(root.getRight(), diameter);
        if (left + right > diameter)
            diameter = left + right;
        return Math.max(left, right) + 1;
    }


    //找出二叉树中同一层节点数据之和最大的层
    //逻辑上类似与查找二叉树的层数 唯一不同的是 还需要跟踪每一层节点的数据和
    int FindLevelwithMaxSum(BinaryTreeNode root) {
        BinaryTreeNode temp;
        int level = 0;
        int maxLevel = 0;
        LLQueue Q = new LLQueue();
        int currentSum = 0;
        int maxSum = 0;
        Q.enQueue(root);
        Q.enQueue(null);//第一层结束
        while (!Q.isEmpty()) {
            temp = Q.deQueue();
            if (temp == null) {
                if (currentSum > maxSum) {
                    maxSum = currentSum;
                    maxLevel = level;
                }
                currentSum = 0;
                //将标记下一层结束的指示器置入队尾
                if (!Q.isEmpty())
                    Q.enQueue(null);
                level++;
            } else {
                currentSum += temp.getData();
                if (temp.getLeft() != null)
                    Q.enQueue(temp.getLeft());
                if (temp.getRight() != null)
                    Q.enQueue(temp.getRight());
            }

        }
        return maxLevel;
    }


    //对于一颗给定的二叉树 输出所有从根节点到叶子节点的路劲
    public void printPaths() {
        BinaryTreeNode node = new BinaryTreeNode(null);
        int[] path = new int[256];
        printPaths(node, path, 0);
    }

    public void printPaths(BinaryTreeNode node, int[] path, int pathLen) {
        if (node == null) return;
        //将该节点添加到路径数组中
        path[pathLen] = node.getData();
        pathLen++;
        //当前为叶子节点 因此输出到这里的路径
        if (node.getLeft() == null && node.getRight() == null) {
            printArray(path, pathLen);
        } else {
            //否则继续遍历两棵树
            printPaths(node.getLeft(), path, pathLen);
            printPaths(node.getRight(), path, pathLen);
        }
    }

    private void printArray(int[] path, int pathLen) {
        for (int i = 0; i < pathLen; i++) {
            System.out.println(path[i] + "");
        }
        System.out.println();
    }


    //给出一个算法 判断是否存在路径的数据和等于给定值   也就是说判断是否存在一条从根节点到任意节点的路径 其所经过节点的数据和为给定值
    public boolean hasPathSum(int sum) {
        BinaryTreeNode root = new BinaryTreeNode(null);
        return hasPathSum(root, sum);
    }

    boolean hasPathSum(BinaryTreeNode node, int sum) {
        //如果所有节点已被访问且 sum == ，则返回true
        if (node == null)
            return (sum == 0);
        else {
            //否则检查两颗子树
            int subSum = sum - node.getData();
            return (hasPathSum(node.getLeft(), subSum) || hasPathSum(node.getRight(), subSum));
        }
    }


    //实现算法 求出二叉树所有节点数据之和
    int Add(BinaryTreeNode root) {
        if (root == null) return 0;
        else return (root.getData() + Add(root.getLeft()) + Add(root.getRight()));
    }


    //非递归 求出二叉树所有节点数据之和
    int SumofBTusingLevelOrder(BinaryTreeNode root) {
        BinaryTreeNode temp;
        LLQueue Q = new LLQueue();
        int sum = 0;
        if (root == null)
            return 0;
        Q.enQueue(root);
        while (!Q.isEmpty()) {
            temp = Q.deQueue();
            sum += temp.getData();
            if (temp.getLeft() != null)
                Q.enQueue(temp.getLeft());
            if (temp.getRight() != null)
                Q.enQueue(temp.getRight());
        }
        Q.deleteQueue();
        return sum;
    }


    //实现将一棵树转换为其镜像的算法
    BinaryTreeNode MirrorOfBinaryTree(BinaryTreeNode root) {
        BinaryTreeNode temp;
        if (root != null) {
            MirrorOfBinaryTree(root.getLeft());
            MirrorOfBinaryTree(root.getRight());
            //交换节点内两个指针
            temp = root.getLeft();
            root.setLeft(root.getRight());
            root.setRight(temp);
        }
        return root;
    }


    //给定两颗树 设计算法判断他们是否为镜像


    Boolean AreMirrors(BinaryTreeNode root1, BinaryTreeNode root2) {
        if (root1 == null && root2 == null)
            return true;
        if (root1 == null && root2 == null)
            return false;
        if (root1.getData() != root2.getData())
            return false;
        else return (AreMirrors(root1.getLeft(), root2.getRight()) && AreMirrors(root1.getRight(), root2.getLeft()));
    }


    //给出一个算法 更具给定中序遍历 和前序遍历来构建一个二叉树
    BinaryTreeNode BuildBinaryTree(int inOrder[], int preOrder[], int inStrt, int inEnd) {
        int preIndex = 0;
        BinaryTreeNode newNode = new BinaryTreeNode(null);
        if (inStrt > inEnd) return null;
        if (newNode == null) {
            System.out.println("Memory Error");
            return null;
        }
        //利用 preIndex 在前序遍历中选择当前节点
        newNode.setData(preOrder[preIndex]);
        preIndex++;
        if (inStrt == inEnd) // 若该节点没有孩子节点则返回
            return newNode;
        //否则在中序序列中找到该节点的索引
        int inIndex = Search(inOrder, inStrt, inEnd, newNode.getData());
        //利用中序序列中节点的索引分别建立左子树和右子树
        newNode.setLeft(BuildBinaryTree(inOrder, preOrder, inStrt, inIndex - 1));
        newNode.setRight(BuildBinaryTree(inOrder, preOrder, inIndex + 1, inEnd));
        return newNode;
    }


    //TODO
    private int Search(int[] inOrder, int inStrt, int inEnd, Integer data) {
        return 0;
    }


    //给定两个遍历序列 能否构建一颗唯一的二叉树
    Boolean PrintAllAncestors(BinaryTreeNode root, BinaryTreeNode node) {
        if (root == null)
            return false;
        if (root.getLeft() == node || root.getRight() == node || PrintAllAncestors(root.getLeft(), node) || PrintAllAncestors(root.getRight(), node)) {
            System.out.println(root.getData());
            return true;
        }
        return false;
    }

    //设计算法计算二叉树中两个节点的最近公共祖先
    BinaryTreeNode LCA(BinaryTreeNode root, BinaryTreeNode a, BinaryTreeNode b) {
        BinaryTreeNode left, right;
        if (root == null)
            return root;
        if (root == a || root == b)
            return root;
        left = LCA(root.getLeft(), a, b);
        right = LCA(root.getRight(), a, b);
        if (left != null && right != null) return root;
        else return (left != null ? left : right);
    }


    //Zigzag 树 遍历   设计算法以Zigzag 顺序遍历二叉树
    void ZigZagTraversal(BinaryTreeNode root) {
        BinaryTreeNode temp;
        int leftToRight = 1;
        if (root == null)
            return;
        Stack currentLevel = new Stack();
        Stack nextLevel = new Stack();
        Push(currentLevel, root);
        while (!isEmpty(currentLevel)) {
            temp = Pop(currentLevel);
            if (temp != null) {
                System.out.println(temp.getData());
                if (leftToRight == 1) {
                    if (temp.getLeft() != null)
                        Push(nextLevel, temp.getLeft());
                    if (temp.getRight() != null)
                        Push(nextLevel, temp.getRight());
                } else {
                    if (temp.getRight() != null)
                        Push(nextLevel, temp.getRight());
                    if (temp.getLeft() != null)
                        Push(nextLevel, temp.getLeft());
                }
            }
            if (isEmpty(currentLevel)) {
                leftToRight = 1 - leftToRight;
                swap(currentLevel, nextLevel);
            }

        }

    }

    private BinaryTreeNode Pop(Stack currentLevel) {
        return null;
    }

    private void Push(Stack nextLevel, BinaryTreeNode right) {
    }

    private void swap(Stack currentLevel, Stack nextLevel) {
    }

    private boolean isEmpty(Stack currentLevel) {
        return currentLevel.isEmpty();
    }


    //得到二叉树的垂直和
    void VerticalSumInBinaryTree(BinaryTreeNode root, int column) {
        if (root == null) return;
        VerticalSumInBinaryTree(root.getLeft(), column - 1);
        //散列表的实现
        //TODO
//        Hash[column] += root.getData();
        VerticalSumInBinaryTree(root, 0);

    }

    //一般 n 个节点有 2^n -n棵不同的树


    //假设一颗树 叶子节点用L表示  内部节点用I 表示 同时假定每个节点只能由0个或2个孩子节点 更具这棵树的前序遍历构建这颗树
    BinaryTreeNode BuildTreeFromPreOrder(char[] A, int i) {
        if (A == null)
            return null;
        BinaryTreeNode newNode = new BinaryTreeNode(null);
        newNode.setData(Integer.valueOf(A[i]));
        newNode.setLeft(null);
        newNode.setRight(null);
        if (A[i] == 'L')
            return newNode;
        i = i + 1;//构建左子树
        newNode.setLeft(BuildTreeFromPreOrder(A, i));
        i = i + 1;//构建右子树
        newNode.setRight(BuildTreeFromPreOrder(A, i));
        return newNode;
    }


    void FillNextSiblings(BinaryTreeNode root) {
        LLQueue Q = new LLQueue();
        BinaryTreeNode temp;
        if (root == null) return;
        Q.enQueue(root);
        Q.enQueue(null);
        while (!Q.isEmpty()) {
            root = Q.deQueue();
            //当前层结束
            if (root == null) {
                //为下一层放置一个标记
                if (!Q.isEmpty())
                    Q.enQueue(null);
            } else {
                //TODO
//                temp.setNextSibling(Q.getFront());
                if (root.getLeft() != null)
                    Q.enQueue(root.getLeft());
                if (root.getRight() != null)
                    Q.enQueue(root.getRight());
            }
        }
    }


    //给定一颗树 设计算法获取树的所有节点值之和
    int FindSum(TreeNode root) {
        if (root == null)
            return 0;
        return root.getData() + FindSum(root.getFirstChild()) + FindSum(root.getNextSibling());
    }


    //给定一个双亲数组 P 计算获取树的高度或深度

    int FindDepthInGenericTree(int P[], int n) {
        int maxDepth = -1, currentDepth = -1, j = 0;
        for (int i = 0; i < n; i++) {
            currentDepth = 0;
            j = P[j];
            while (P[j] != -1) {
                currentDepth++;
                j = P[j];
            }
            if (currentDepth > maxDepth)
                maxDepth = currentDepth;
        }
        return maxDepth;
    }


    //给定一个节点 需要遍历其所有兄弟节点
    int SiblingsCOunt(TreeNode current) {
        int count = 0;
        while (current != null) {
            count++;
            current = current.getNextSibling();
        }
        return count;
    }

    //给定两颗树 如何判断两棵树是否为同构树
    int IsIsomorphic(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null)
            return 1;
        if ((root1 == null && root2 != null) || (root1 != null && root2 == null))
            return 0;
        //TODO
        return 0;
//        return (IsIsomorphic(root1.getLeft(),root2.getLeft()) && IsIsomorphic(root1.getRight(),root2.getRight()));
    }


    //如何判断两棵树为准构树
    Boolean QuasiIsomorphic(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null)
            return true;
        if ((root1 == null && root2 != null) || (root1 != null && root2 == null))
            return false;
        return (QuasiIsomorphic(root1.getFirstChild(), root2.getFirstChild()) &&
                QuasiIsomorphic(root1.getNextSibling(), root2.getNextSibling()) ||
                QuasiIsomorphic(root1.getNextSibling(), root2.getFirstChild()) &&
                        QuasiIsomorphic(root1.getFirstChild(), root2.getNextSibling()));
    }


    //给定通用树中的一个节点设计算法来计算该节点的孩子节点数
    int ChildCount(TreeNode current) {
        int count = 0;
        current = current.getFirstChild();
        while (current != null) {
            count++;
            current = current.getNextSibling();
        }
        return count;
    }


    //满k 叉树要么有k个孩子节点 要么没有孩子节点   给定一颗 k叉树的前序序列 构建该k叉树的算法


    public static void main845(String[] args) {
        int A[] = {1, 2, 42, 2, 56, 443, 6, 7, 8};
        int temp[] = new int[A.length];
        int left = 0;
        int right = A.length - 1;
        long start = System.nanoTime();
        MergeSort(A, temp, left, right);
        long end = System.nanoTime();
        System.out.println(end - start);
        System.out.println(JSON.toJSONString(A));
        System.out.println(JSON.toJSONString(temp));
    }

    //归并排序
    static void MergeSort(int A[], int temp[], int left, int right) {
        int mid;
        if (right > left) {
            mid = (right + left) / 2;
            MergeSort(A, temp, left, mid);
            MergeSort(A, temp, mid + 1, right);
            Merge(A, temp, left, mid + 1, right);
        }
    }

    static void Merge(int A[], int temp[], int left, int mid, int right) {
        int i, left_end, size, temp_pos;
        left_end = mid - 1;
        temp_pos = left;
        size = right - left + 1;
        while ((left <= left_end) && (mid <= right)) {
            if (A[left] <= A[mid]) {
                temp[temp_pos] = A[left];
                temp_pos = temp_pos + 1;
                left = left + 1;

            } else {
                temp[temp_pos] = A[mid];
                temp_pos = temp_pos + 1;
                mid = mid + 1;
            }
        }

        while (left <= left_end) {
            temp[temp_pos] = A[left];
            left = left + 1;
            temp_pos = temp_pos + 1;
        }
        while (mid <= right) {
            temp[temp_pos] = A[mid];
            mid = mid + 1;
            temp_pos = temp_pos + 1;
        }
        for (i = 0; i < size; i++) {
            A[right] = temp[right];
            right = right - 1;
        }
    }


    public static void main904(String[] args) {
        ShuffleArray();
    }

    static void ShuffleArray() {
        int n = 4, A[] = {1, 3, 5, 7, 2, 4, 6, 8};
        for (int i = 0, q = 1, k = n; i < n; i++, k++, q++) {
            for (int j = k; j > i + q; j--) {
                int tmp = A[j - 1];
                A[j - 1] = A[j];
                A[j] = tmp;
            }
        }
        for (int i = 0; i < 2 * n; i++) {
            System.out.println(A[i]);
        }
    }


    //给定一个数组 A[] 查找最大的 j-i 使得 A[j] >A[i]
    public static void main923(String[] args) {
        int[] A = {1, 3, 5, 73, 3, 5, 1, 1, 1};
        System.out.println(maxIndexDiff(A, A.length));
    }


    static int maxIndexDiff(int A[], int n) {
        int maxDiff = -1;
        int i, j;
        for (i = 0; i < n; ++i) {
            for (j = n - 1; j > i; --j) {
                if (A[j] > A[i] && maxDiff < (j - i)) {
                    maxDiff = j - i;
                }
            }
        }
        return maxDiff;
    }





}
