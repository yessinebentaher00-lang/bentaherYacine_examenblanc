package org.example.bentaheryacine_examenblanc.Services;

import org.example.bentaheryacine_examenblanc.Entities.TypeUtilisateur;
import org.example.bentaheryacine_examenblanc.Entities.Utilisateur;

import java.util.Date;
import java.util.List;

public interface IUtilisateurService {
        public Utilisateur ajouterUtilisateur(Utilisateur u);
        //public List<Utilisateur> recupererUtilisateurParCriteres1(String nomCategorie);
        //public List<Utilisateur> recupererUtilisateurParCriteres(String nomCategorie, Date d, TypeUtilisateur tu);
        //public List<Utilisateur> recupererUtilisateurParCriteres2( TypeUtilisateur tu);
        //public List<Utilisateur> recupererUtilisateurParCriteres3( Date d);
        public void affecterProdAUser(List<String> nomProduit,String email);
        List<Utilisateur> recupererUtilisateursParCriteres(String nomCategorie, Date d, TypeUtilisateur tu);
        void afficherEtMettreAJourProduits();


}
