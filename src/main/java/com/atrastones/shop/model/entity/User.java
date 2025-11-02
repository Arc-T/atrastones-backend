package com.atrastones.shop.model.entity;

import com.atrastones.shop.type.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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

    @Column(name = "first_name")
    private String firsName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String phone;

    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // ************************************** FOREIGN-KEY RELATIONS *******************************************

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    // ******************************************** TABLE RELATIONS *******************************************

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Shop> shops;

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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userGroup.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .distinct() // Remove duplicates if same permission exists in multiple roles
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

}

