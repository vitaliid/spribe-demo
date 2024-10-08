package com.spribe.demo.entity;

import com.spribe.demo.entity.composite.UuidPk;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "rates_take")
@Getter
@Setter
@IdClass(UuidPk.class)
public class RatesTake implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    @Id
    private String id;

    @Column(name = "timestamp", nullable = false)
    private long timestamp;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Currency.class, optional = false)
    @JoinColumn(
            name = "base_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "base_id_currency_id_key"),
            nullable = false,
            insertable = false,
            updatable = false)
    private Currency base;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, String> rates = new HashMap<>();
}
