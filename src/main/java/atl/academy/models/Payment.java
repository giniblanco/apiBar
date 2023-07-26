package atl.academy.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "discount",nullable = false)
    private float discount;

    @Column(name = "amount",nullable = false)
    private float amount;

    @Column(name = "date_pay",nullable = false)
    private Date datePay;



}
