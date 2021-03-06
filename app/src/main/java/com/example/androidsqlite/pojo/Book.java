package com.example.androidsqlite.pojo;

import org.litepal.crud.LitePalSupport;

/**
 * @author ZCL
 * 数据表接收类
 * ps:此处继承的LitePalSupport是需要使用litepal框架时必须继承的，正常使用sqlite并不需要
 */
public class Book extends LitePalSupport {
    private int id;
    private String name;
    private String author;
    private double price;
    private int pages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
