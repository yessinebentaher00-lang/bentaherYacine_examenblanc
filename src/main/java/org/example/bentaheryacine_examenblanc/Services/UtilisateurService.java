package org.example.bentaheryacine_examenblanc.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bentaheryacine_examenblanc.Entities.Etat;
import org.example.bentaheryacine_examenblanc.Entities.Produit;
import org.example.bentaheryacine_examenblanc.Entities.TypeUtilisateur;
import org.example.bentaheryacine_examenblanc.Entities.Utilisateur;
import org.example.bentaheryacine_examenblanc.Repositories.ProduitRepository;
import org.example.bentaheryacine_examenblanc.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class UtilisateurService implements IUtilisateurService{

    @Autowired
    UtilisateurRepository UR;
    @Autowired
    ProduitRepository PR;
    @Override
    public Utilisateur ajouterUtilisateur(Utilisateur u) {
        return UR.save(u);
    }

    @Override
    public void affecterProdAUser(List<String> nomProduit,String email){

        Utilisateur u = UR.findByEmail(email);
        if (u != null) {
            List<Produit> produits = new ArrayList<>();

            for (String produit : nomProduit) {
                Produit p = PR.findByNomProduit(produit);
                if (p != null) {
                    produits.add(p);
                }
            }
            if (u.getProduits() != null) {
                produits.addAll(u.getProduits());
            }
            u.setProduits(produits);
        }
        UR.save(u);

    }
    @Override
    public List<Utilisateur> recupererUtilisateursParCriteres(String nomCategorie, Date d, TypeUtilisateur tu) {
        return UR.findUtilisateursByCriteres(nomCategorie, d, tu);
    }

    @Override
    @Scheduled(fixedDelay = 40000)
    @Transactional
    public void afficherEtMettreAJourProduits() {
        List<Utilisateur> admins = UR.findByTypeUtilisateur(TypeUtilisateur.ADMIN);

        for (Utilisateur admin : admins) {
            List<Produit> produitsAdmin = admin.getProduits();
            for (Produit p : produitsAdmin) {
                p.setEtat(Etat.BOYCOTT);
                log.info(p.getNomProduit());
            }
            PR.saveAll(produitsAdmin);
        }
        List<Produit> produits = PR.findAll();
        for (Produit p : produits) {
            log.info(p.getNomProduit() + " : " + p.getEtat());

        }
    }

    //@Override
    //public List<Utilisateur> recupererUtilisateurParCriteres1(String nomCategorie){
    //    return UR.findByCategoriesByNomCategorie(nomCategorie);
    //}

//    @Override
//    public List<Utilisateur> recupererUtilisateurParCriteres2(TypeUtilisateur tu){
//        return UR.findByTypeUtilisateur(tu);
//
//    }
//    @Override
//    public List<Utilisateur> recupererUtilisateurParCriteres3(Date d){
//        return UR.findByDateInscriGreaterThan(d);
//    }

    //@Override
    //public List<Utilisateur> recupererUtilisateurParCriteres(String nomCategorie, Date d, TypeUtilisateur tu){
    //    List<Utilisateur> l = recupererUtilisateurParCriteres1(nomCategorie);
    //    l.addAll(recupererUtilisateurParCriteres2(tu));
    //    l.addAll(recupererUtilisateurParCriteres3(d));
    //    return l;
    //}

}
