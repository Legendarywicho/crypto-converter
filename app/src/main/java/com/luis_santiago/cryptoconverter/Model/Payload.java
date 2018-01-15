package com.luis_santiago.cryptoconverter.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Luis Fernando Santiago Ruiz on 1/14/18.
 */

public class Payload {

    @SerializedName("high")
    @Expose
    private String high;
    @SerializedName("last")
    @Expose
    private String last;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("book")
    @Expose
    private String book;
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("vwap")
    @Expose
    private String vwap;
    @SerializedName("low")
    @Expose
    private String low;
    @SerializedName("ask")
    @Expose
    private String ask;
    @SerializedName("bid")
    @Expose
    private String bid;

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVwap() {
        return vwap;
    }

    public void setVwap(String vwap) {
        this.vwap = vwap;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "high='" + high + '\'' +
                ", last='" + last + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", book='" + book + '\'' +
                ", volume='" + volume + '\'' +
                ", vwap='" + vwap + '\'' +
                ", low='" + low + '\'' +
                ", ask='" + ask + '\'' +
                ", bid='" + bid + '\'' +
                '}';
    }
}
