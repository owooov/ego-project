package com.ego.cart.vo;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/10 09:26
 * @Description:
 */
public class Cart {
    private Long id;
    private String title;
    private Long price;
    private Integer num;
    private String[] images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
