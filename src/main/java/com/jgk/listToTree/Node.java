package com.jgk.listToTree;

import java.util.List;

public class Node<T> {
    private T treeObj;
    private List<Node> nodeList;


    public Node() {

    }


    public T getTreeObj() {
        return treeObj;
    }

    public void setTreeObj(T treeObj) {
        this.treeObj = treeObj;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }


}
