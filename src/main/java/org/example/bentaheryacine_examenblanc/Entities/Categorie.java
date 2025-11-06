package org.example.bentaheryacine_examenblanc.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Long id;
    public String nomCategorie ;
    @ManyToMany(mappedBy = "categories")
    @ToString.Exclude
    @JsonIgnore
    public List<Produit> produits = new ArrayList<Produit>();

}
