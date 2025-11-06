package org.example.bentaheryacine_examenblanc.Repositories;

import org.example.bentaheryacine_examenblanc.Entities.Produit;
import org.example.bentaheryacine_examenblanc.Entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
        public Produit findByEtat(String etat);
        public Produit findByNomProduit(String nomProduit);
}
