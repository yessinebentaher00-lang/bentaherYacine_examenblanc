package org.example.bentaheryacine_examenblanc.Services;

import org.example.bentaheryacine_examenblanc.Entities.Produit;

import java.util.List;

public interface IProduitService {
    public Produit ajouterProduitEtCategories(Produit p);
    boolean chercherProduit(String nomProduit);
    public void desAffecterCatDeProd(List<String> nomCategories, String nomProduit);    //public boolean chercherProduit(String nomProduit);

}
