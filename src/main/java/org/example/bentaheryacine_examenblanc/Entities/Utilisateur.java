package org.example.bentaheryacine_examenblanc.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String email;
    public Date dateInscri; //localDate
    @Enumerated(EnumType.STRING)
    public TypeUtilisateur typeUtilisateur;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Produit> produits;
//    public List<Produit> getProduits() {
//        return this.produits;
//    }

}
