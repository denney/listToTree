package com.jgk.listToTree;

import java.util.List;

public class Node<T> {
    private T treeObj;
    private List<Node<T>> nodeList;


    public Node() {

    }


    public T getTreeObj() {
        return treeObj;
    }

    public void setTreeObj(T treeObj) {
        this.treeObj = treeObj;
    }

    public List<Node<T>> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node<T>> nodeList) {
        this.nodeList = nodeList;
    }

    @Override
    public String toString() {
        return "Node{" +
                "treeObj=" + treeObj +
                ", nodeList=" + nodeList +
                '}';
    }
}
