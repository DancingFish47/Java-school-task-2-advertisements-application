package com.rychkov.eads.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Data
public class BookDto {
    private Integer id;
    private Integer version;
    private Date date;
    private String name;
    private String author;
    private Float price;
    private BookCategory bookCategory;
    private Integer pages;
    private Integer amount;
    private Integer sold;


}
