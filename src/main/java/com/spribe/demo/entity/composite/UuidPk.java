package com.spribe.demo.entity.composite;

import com.spribe.demo.entity.converter.StringUuidConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode
public class UuidPk implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid")
    @Convert(converter = StringUuidConverter.class)
    private String id;
}
