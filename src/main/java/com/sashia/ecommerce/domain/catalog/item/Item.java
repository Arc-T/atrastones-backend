package com.sashia.ecommerce.domain.catalog.item;

import com.sashia.ecommerce.domain.catalog.category.Category;
import com.sashia.ecommerce.domain.catalog.tag.Tag;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "items", schema = "catalog")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    private String slug;

    @Enumerated(EnumType.STRING)
    private ItemTypeEnum type;

    private boolean published;

    private boolean featured;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @SoftDelete(strategy = SoftDeleteType.TIMESTAMP)
    private LocalDateTime deletedAt;

    /* **************************** FOREIGN-KEY RELATIONS ***************************** */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    /* **************************** TABLE RELATIONS *********************************** */

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "item_tags",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

//    @OneToMany(mappedBy = "item")
//    private Set<Media> media = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "catalogItem")
    private Set<ItemStat> stats = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "catalogItem")
    private Set<ItemReview> reviews = new HashSet<>();

    /* **************************** GETTER & SETTERS ********************************** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ItemTypeEnum getType() {
        return type;
    }

    public void setType(ItemTypeEnum type) {
        this.type = type;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<ItemStat> getStats() {
        return stats;
    }

    public void setStats(Set<ItemStat> stats) {
        this.stats = stats;
    }

    public Set<ItemReview> getReviews() {
        return reviews;
    }

    public void setReviews(Set<ItemReview> reviews) {
        this.reviews = reviews;
    }

}