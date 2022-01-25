package com.parchenegar.capsule.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDERS")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String status;
    String priceAmount;
    String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    List<OrderItem> orderItems;

}
