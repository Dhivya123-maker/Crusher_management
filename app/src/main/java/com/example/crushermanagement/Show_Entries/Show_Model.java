package com.example.crushermanagement.Show_Entries;

import java.io.Serializable;

public class Show_Model implements Serializable  {
    public Show_Model(String date, String s_no, String place, String rate, String v_no, String p_name, String m_name,String id) {
        this.date = date;
        this.s_no = s_no;
        this.place = place;
        this.rate = rate;
        this.v_no = v_no;
        this.p_name = p_name;
        this.m_name = m_name;
        this.id = id;

    }

    String date;
    String s_no;

    public Show_Model(String units, String price) {
        this.units = units;
        this.price = price;
    }

    String units;

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    String place;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    String rate;

    public String getV_no() {
        return v_no;
    }

    public void setV_no(String v_no) {
        this.v_no = v_no;
    }

    String v_no;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getS_no() {
        return s_no;
    }

    public void setS_no(String s_no) {
        this.s_no = s_no;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }



    String p_name;
    public Show_Model(){}


    String m_name;



}
