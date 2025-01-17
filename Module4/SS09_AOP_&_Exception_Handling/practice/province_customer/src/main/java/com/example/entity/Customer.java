package com.example.entity;


import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;


}
