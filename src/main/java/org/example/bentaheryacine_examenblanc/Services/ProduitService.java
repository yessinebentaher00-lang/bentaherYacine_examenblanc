package org.example.bentaheryacine_examenblanc.Services;

import lombok.AllArgsConstructor;
import org.example.bentaheryacine_examenblanc.Entities.*;
import org.example.bentaheryacine_examenblanc.Repositories.ICategorieRepository;
import org.example.bentaheryacine_examenblanc.Repositories.ProduitRepository;
import org.example.bentaheryacine_examenblanc.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ProduitService implements IProduitService {
    @Autowired
    ProduitRepository PR;
    @Autowired
    ICategorieRepository categorieRepo;


    @Override
    public Produit ajouterProduitEtCategories(Produit p) {

        return PR.save(p);
    }


    @Override
    public boolean chercherProduit(String nomProduit) {
        Produit produit = PR.findByNomProduit(nomProduit);
        if (produit != null) {
            return produit.getEtat() == Etat.BOYCOTT;

        }
        return false;
    }
    @Override
    public void desAffecterCatDeProd(List<String> nomCategories, String nomProduit) {
        Produit p = PR.findByNomProduit(nomProduit);
        if (p != null) {
            List<Categorie> categories = p.getCategories();
            if (categories != null) {
                for (String nomCat : nomCategories) {
                    categories.removeIf(categorie -> categorie.getNomCategorie().equals(nomCat));
                }
            }
            p.setCategories(categories);
            PR.save(p);
        }

    }

    //@Override
    //public boolean chercherProduit(String nomProduit);

    //return PR.findByEtat;

}
