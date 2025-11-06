package org.example.bentaheryacine_examenblanc.Repositories;

import org.example.bentaheryacine_examenblanc.Entities.Categorie;
import org.example.bentaheryacine_examenblanc.Entities.TypeUtilisateur;
import org.example.bentaheryacine_examenblanc.Entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByEmail(String email);
    List<Utilisateur> findByTypeUtilisateur(TypeUtilisateur typeUtilisateur);
    @Query("SELECT DISTINCT u FROM Utilisateur u " +
            "JOIN u.produits p " +
            "JOIN p.categories c " +
            "WHERE c.nomCategorie = :nomCategorie " +
            "AND u.dateInscri > :date " +
            "AND u.typeUtilisateur = :typeUtilisateur")
    List<Utilisateur> findUtilisateursByCriteres(String nomCategorie, Date date, TypeUtilisateur typeUtilisateur);

//    List<Utilisateur>  findByTypeUtilisateur(TypeUtilisateur typeUtilisateur);
//
//    List<Utilisateur>  findByDateInscriGreaterThan(Date dateInscriIsGreaterThan);
//
//    List<Utilisateur> findByCategoriesByNomCategorie(String s);
//
//    List<Utilisateur>  findByTypeUtilisateurAndDateInscriGreaterThan(TypeUtilisateur typeUtilisateur, Date dateInscriIsGreaterThan);
}
