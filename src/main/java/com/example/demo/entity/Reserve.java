package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@IdClass(ReserveCompositeKey.class)
@Table(name = "t_reserve")
public class Reserve {
    @Id
    @Column(name = "date")
    private Date date;

    @Id
    @Column(name = "time")
    private String time;

    @Id
    @Column(name = "eid")
    private String eid;
    
    @Column(name = "cid")
    private String cid;
    
    @Column(name = "etc")
    private String etc;
}
