package uz.pdp.springmvcjwtoauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductEntity extends BaseEntity<String> {

    private String name;
    private int quantity;
    private double price;

}
