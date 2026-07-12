package com.sashia.ecommerce.domain.catalog.item.serviceoffering;

import com.sashia.ecommerce.domain.catalog.item.Item;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.group.ServiceGroup;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "service_offerings")
public class ServiceOffering {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Item item;

    private BigDecimal cost;

    @Column(name = "service_group_id", updatable = false, insertable = false)
    private Long serviceGroupId;

    /* **************************** FOREIGN-KEY RELATIONS ***************************** */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ServiceGroup serviceGroup;

    /* ****************************** GETTER & SETTERS ******************************** */

    public BigDecimal cost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Long serviceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(Long serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }

    public ServiceGroup serviceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

}
