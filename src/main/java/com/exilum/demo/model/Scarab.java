package com.exilum.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Scarabs")
public class Scarab {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "scarab_id")
    private Integer scarab_id;

    @Column(name = "name")
    @NotBlank(message = "Scarab name can't be blank")
    private String name;

    @Column(name = "mechanic")
    // @NotBlank(message = "Scarab mechanic can't be blank")
    private String mechanic;

    @Column(name = "scarab_limit")
    // @NotNull(message = "Scarab limit can't be blank")
    private Integer scarab_limit;

    @Column(name = "price")
    @NotNull(message = "Scarab price can't be blank")
    private Double price;

    @Column(name = "icon_url")
    @NotBlank(message = "Scarab image url can't be blank")
    private String icon_url;

    public void setScarab_id(Integer scarab_id) {
        this.scarab_id = scarab_id;
    }

    // TODO: Add hardcore prices


    @Override
    public String toString() {
        return "{" +
                "scarab_id=" + scarab_id +
                ", name='" + name + '\'' +
                ", mechanic='" + mechanic + '\'' +
                ", scarab_limit=" + scarab_limit +
                ", price=" + price +
                ", icon_url='" + icon_url + '\'' +
                '}';
    }
}
