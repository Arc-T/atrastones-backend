package com.atrastones.shop.model.entity;

import com.atrastones.shop.type.ShopStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    @Enumerated(EnumType.STRING)
    private ShopStatus status;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    /* **************************** FOREIGN-KEY RELATIONS **********************************/

    @OneToOne(fetch = FetchType.LAZY)
    private Address address;

    /* ********************************* TABLE RELATIONS **********************************/

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Order> orders;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Product> products;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private Set<ShopMember> members;

}
