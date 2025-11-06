package org.example.bentaheryacine_examenblanc.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nomProduit;
    @Enumerated(EnumType.STRING)
    public Etat etat;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Categorie> categories;


}
