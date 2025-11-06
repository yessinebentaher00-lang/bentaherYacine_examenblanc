package org.example.bentaheryacine_examenblanc.Controllers;


import lombok.AllArgsConstructor;
import org.example.bentaheryacine_examenblanc.Entities.TypeUtilisateur;
import org.example.bentaheryacine_examenblanc.Entities.Utilisateur;
import org.example.bentaheryacine_examenblanc.Services.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static java.util.Date.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UtilisateurController {
    @Autowired
    IUtilisateurService UU;

    @PostMapping("/add")
    public Utilisateur addingBloc(@RequestBody Utilisateur u) {
        return UU.ajouterUtilisateur(u);

    }

    @PutMapping("/affectation/{email}")
    public void affectation(@RequestBody List<String> produits, @PathVariable("email") String email) {
        UU.affecterProdAUser(produits, email);
    }

    @GetMapping("/filtrer")
    public List<Utilisateur> recupererUtilisateursParCriteres(
            @RequestParam String nomCategorie,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam TypeUtilisateur typeUtilisateur) {

        return UU.recupererUtilisateursParCriteres(nomCategorie, date, typeUtilisateur);
    }



    //@GetMapping("/getByCriteres")
    //public List<Utilisateur> recupererUtilisateurByCriteres(String nomCriteres, Date d, TypeUtilisateur tu)
    //{
    //    return UU.recupererUtilisateurParCriteres(nomCriteres,d,tu);
    //}

}
