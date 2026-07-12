package com.sashia.ecommerce.domain.catalog.item;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_stats")
public class ItemStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer views;

    private Integer likes;

    private Integer shares;

    private Integer comments;

    private Short ratings;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /* ******************************** FOREIGN-KEY RELATIONS **************************************/

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    /* **************************** GETTER & SETTERS **********************************/

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer views() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer likes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer shares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public Integer comments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Short ratings() {
        return ratings;
    }

    public void setRatings(Short ratings) {
        this.ratings = ratings;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Item getCatalogItem() {
        return item;
    }

    public void setCatalogItem(Item item) {
        this.item = item;
    }

}
