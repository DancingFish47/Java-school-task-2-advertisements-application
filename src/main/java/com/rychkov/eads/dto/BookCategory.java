package com.rychkov.eads.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Data
class BookCategory {
    private Integer id;
    private Integer version;
    private Date date;
    private String name;
}
