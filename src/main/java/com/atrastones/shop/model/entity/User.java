package com.atrastones.shop.model.entity;

import com.atrastones.shop.type.GenderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String password;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    // ************************************** FOREIGN-KEY RELATIONS *******************************************

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    // ******************************************** TABLE RELATIONS *******************************************

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Order> orders;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Payment> payments;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<UserLog> userLogs;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Address> addresses;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<ProductReview> productReviews;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vip_memberships",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "vip_group_id")
    )
    private Set<VipGroup> vipGroups;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> wishlists;

    // ************************************** AUTHENTICATION **************************************

    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userGroup.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @NonNull
    public String getUsername() {
        return phone;
    }

}

