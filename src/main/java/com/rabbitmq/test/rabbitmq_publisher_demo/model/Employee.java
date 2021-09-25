package com.rabbitmq.test.rabbitmq_publisher_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEEID", updatable = false)
    private Integer employeeId;

    @Column(name = "EPF")
    private String epf;

    @Column(name = "NAMEINFULL")
    private String nameInFull;

    @Column(name = "INITIALS")
    private String initials;

    @Column(name = "PREFERREDNAME")
    private String preferredName;

    @Column(name = "SURENAME")
    private String surName;

    @Column(name = "NIC")
    private String nic;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CONTACTNO01")
    private String contactNoOne;

    @Column(name = "CONTACTNO02")
    private String contactNoTwo;

    @Column(name = "HIERARCHYID")
    private Integer hierarchyId;

    @Column(name = "STATUS")
    private Integer status;

    @CreationTimestamp
    @Column(name = "CREATEDDATETIME")
    private Date createdDateTime;

    @UpdateTimestamp
    @Column(name = "LASTUPDATEDDATETIME")
    private Date lastUpdatedDateTime;

    @Column(name = "CREATEDUSER")
    private String createdUser;
}
