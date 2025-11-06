package org.example.bentaheryacine_examenblanc.Controllers;


import lombok.AllArgsConstructor;
import org.example.bentaheryacine_examenblanc.Entities.Produit;
import org.example.bentaheryacine_examenblanc.Entities.Utilisateur;
import org.example.bentaheryacine_examenblanc.Services.IProduitService;
import org.example.bentaheryacine_examenblanc.Services.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProduitController {
    @Autowired
    IProduitService ps;

    @PostMapping("/add1")
    public Produit addingProd(@RequestBody Produit p) {
        return ps.ajouterProduitEtCategories(p);
    }

    @GetMapping("/chercher/{nomProduit}")
    public boolean chercherProduit(@PathVariable String nomProduit) {
        return ps.chercherProduit(nomProduit);
    }

    @PutMapping("/desafectation/{nomP}")
    public void Desaffectation(@RequestBody List<String> nomC, @PathVariable("nomP") String nomP) {
        ps.desAffecterCatDeProd(nomC, nomP);
    }


}
