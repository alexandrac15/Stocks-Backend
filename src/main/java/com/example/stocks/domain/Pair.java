package com.example.stocks.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Pair {
//    @JsonSerialize(as = Date.class)
////    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
   // @DateTimeFormat(pattern="dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private Float price;

   public Pair(){}

    public Pair(Date date, Float price) {
        this.date = date;
        this.price = price;
    }
    public Pair(String date, String price) throws ParseException {
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        this.date =date1 ;
        this.price = Float.parseFloat(price);
    }
  // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");

        return "Pair{" +
                "date=" + date +
                ", price=" + price +
                '}';
    }
}
