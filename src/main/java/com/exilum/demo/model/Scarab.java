package com.exilum.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "scarabs_table_spring")
public class Scarab {
    @Id
    @NotBlank(message = "Scarab id can't be blank")
    private Long scarab_id;
    @NotBlank(message = "Scarab name can't be blank")
    private String name;
    @NotBlank(message = "Scarab mechanic can't be blank")
    private String mechanic;
    @NotBlank(message = "Scarab limit can't be blank")
    private Integer scarab_limit;
    @NotBlank(message = "Scarab price can't be blank")
    private Double price;

    // TODO: Add hardcore prices

    @Override
    public String toString() {
        return "Scarab{" +
                "scarab_id=" + scarab_id +
                ", name='" + name + '\'' +
                ", mechanic='" + mechanic + '\'' +
                ", limit=" + scarab_limit +
                ", price=" + price +
                '}';
    }
}
