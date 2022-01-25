package com.parchenegar.capsule.domain.payment;

import com.parchenegar.capsule.domain.order.Order;
import com.parchenegar.capsule.domain.user.Customer;
import com.parchenegar.capsule.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INVOICES")
public class Invoice
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String status;
    String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    Customer customer;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date modified;


}
