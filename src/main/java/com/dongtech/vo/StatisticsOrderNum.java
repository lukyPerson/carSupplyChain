package com.dongtech.vo;
public class StatisticsOrderNum {

    private int month;
    private int num;

    public StatisticsOrderNum() {
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public StatisticsOrderNum(int month, int num) {
        this.month = month;
        this.num = num;
    }
}
