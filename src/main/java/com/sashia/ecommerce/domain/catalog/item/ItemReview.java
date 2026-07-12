package com.sashia.ecommerce.domain.catalog.item;

import com.sashia.ecommerce.domain.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_reviews")
public class ItemReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;

    @Enumerated(EnumType.STRING)
    private ItemReviewAction actionType;

    private String actionDetails;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @SoftDelete
    private LocalDateTime deletedAt;

    /* **************************** FOREIGN-KEY RELATIONS ***************************** */

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    /* **************************** GETTER & SETTERS ********************************** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public ItemReviewAction getActionType() {
        return actionType;
    }

    public void setActionType(ItemReviewAction actionType) {
        this.actionType = actionType;
    }

    public String getActionDetails() {
        return actionDetails;
    }

    public void setActionDetails(String actionDetails) {
        this.actionDetails = actionDetails;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getCatalogItem() {
        return item;
    }

    public void setCatalogItem(Item item) {
        this.item = item;
    }

}