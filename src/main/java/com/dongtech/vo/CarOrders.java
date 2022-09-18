package com.dongtech.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gzl
 * @date 2020-04-15
 * @program: springboot-jsp
 * @description: ${description}
 */
public class CarOrders {
    private String id;
    private String number;
    private Date createTs;
    private BigDecimal price;



    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CarOrders(String id, String number, BigDecimal price,Date createTs) {
        this.id = id;
        this.number = number;
        this.price = price;
        this.createTs = createTs;
    }

    public CarOrders(String id, String number, BigDecimal price) {
        this.id = id;
        this.number = number;
        this.price = price;
    }
}
