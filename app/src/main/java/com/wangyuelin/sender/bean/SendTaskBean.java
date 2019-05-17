package com.wangyuelin.sender.bean;

public class SendTaskBean {
    private int id;
    private String name; //任务的名称
    private long time;  //创建时间、配送时间、完成时间
    private String way = "机器人";//配送方式

    private String userSelTime;//用户选择配送时间
    private String userSelLocation;//用户选择配送的地址

    private int status;//状态：待配送、正在配送、已配送
    private String recvPhone;//接收人手机号


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public String getWay() {
        return way;
    }

    public String getUserSelTime() {
        return userSelTime;
    }

    public String getUserSelLocation() {
        return userSelLocation;
    }

    public int getStatus() {
        return status;
    }

    public String getRecvPhone() {
        return recvPhone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public void setUserSelTime(String userSelTime) {
        this.userSelTime = userSelTime;
    }

    public void setUserSelLocation(String userSelLocation) {
        this.userSelLocation = userSelLocation;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRecvPhone(String recvPhone) {
        this.recvPhone = recvPhone;
    }
}
