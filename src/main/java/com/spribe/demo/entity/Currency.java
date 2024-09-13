package com.spribe.demo.entity;

import com.spribe.demo.entity.composite.UuidPk;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "currency")
@Getter
@Setter
@IdClass(UuidPk.class)
public class Currency implements Serializable {

    @Id
    private String id;

    @Column(name = "symbol", length = 20, nullable = false, unique = true)
    private String symbol;
}
