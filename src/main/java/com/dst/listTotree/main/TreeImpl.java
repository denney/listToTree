package com.dst.listTotree.main;

public class TreeImpl implements Tree {


    private String id;
    private String textContent;
    private String pid;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TreeImpl(String id, String pid, String textContent,String url) {
        this.id = id;
        this.textContent = textContent;
        this.pid = pid;
        this.url=url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String getItemId() {
        return this.id;
    }

    @Override
    public String getParentId() {
        return this.pid;
    }




}
