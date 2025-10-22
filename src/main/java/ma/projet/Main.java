package ma.projet;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class Main implements CommandLineRunner {

    @Autowired
    private HommeService hommeService;

    @Autowired
    private FemmeService femmeService;

    @Autowired
    private MariageService mariageService;

    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("   PROGRAMME DE TEST - GESTION DES MARIAGES");


        System.out.println(">>> 1. Création de 5 hommes\n");

        Homme h1 = new Homme("Alami", "0612345678", "Casablanca", sdf.parse("1975-05-15"));
        Homme h2 = new Homme("Bennani", "0623456789", "Rabat", sdf.parse("1980-08-20"));
        Homme h3 = new Homme("Chakir", "0634567890", "Fes", sdf.parse("1978-03-10"));
        Homme h4 = new Homme("Daoudi", "0645678901", "Marrakech", sdf.parse("1972-11-25"));
        Homme h5 = new Homme("Elhami", "0656789012", "Agadir", sdf.parse("1985-07-08"));

        hommeService.create(h1);
        hommeService.create(h2);
        hommeService.create(h3);
        hommeService.create(h4);
        hommeService.create(h5);

        System.out.println("✓ 5 hommes créés avec succès\n");


        System.out.println(">>> 2. Création de 10 femmes\n");

        Femme f1 = new Femme("Amrani", "Fatima", "0661111111", "Casablanca", sdf.parse("1985-02-20"));
        Femme f2 = new Femme("Bekkali", "Aicha", "0662222222", "Rabat", sdf.parse("1970-07-15"));
        Femme f3 = new Femme("Cherif", "Laila", "0663333333", "Fes", sdf.parse("1990-11-05"));
        Femme f4 = new Femme("Drissi", "Samira", "0664444444", "Marrakech", sdf.parse("1987-04-25"));
        Femme f5 = new Femme("Elhajjam", "Nadia", "0665555555", "Tangier", sdf.parse("1992-09-30"));
        Femme f6 = new Femme("Fassi", "Khadija", "0666666666", "Meknes", sdf.parse("1983-12-12"));
        Femme f7 = new Femme("Gharbi", "Malika", "0667777777", "Tetouan", sdf.parse("1988-05-18"));
        Femme f8 = new Femme("Hamidi", "Rachida", "0668888888", "Oujda", sdf.parse("1975-03-22"));
        Femme f9 = new Femme("Idrissi", "Salma", "0669999999", "Kenitra", sdf.parse("1995-08-07"));
        Femme f10 = new Femme("Jabri", "Zineb", "0660000000", "Safi", sdf.parse("1989-01-14"));

        femmeService.create(f1);
        femmeService.create(f2);
        femmeService.create(f3);
        femmeService.create(f4);
        femmeService.create(f5);
        femmeService.create(f6);
        femmeService.create(f7);
        femmeService.create(f8);
        femmeService.create(f9);
        femmeService.create(f10);

        System.out.println("✓ 10 femmes créées avec succès\n");

        // Créer des mariages pour les tests
        Mariage m1 = new Mariage(h1, f1, sdf.parse("2010-06-15"), sdf.parse("2015-12-20"), 2);
        Mariage m2 = new Mariage(h1, f2, sdf.parse("2016-03-10"), null, 1);
        Mariage m3 = new Mariage(h1, f3, sdf.parse("2018-07-01"), null, 1);
        Mariage m4 = new Mariage(h1, f4, sdf.parse("2019-05-12"), null, 0);

        Mariage m5 = new Mariage(h2, f5, sdf.parse("2005-01-20"), sdf.parse("2012-08-15"), 3);
        Mariage m6 = new Mariage(h2, f6, sdf.parse("2013-05-25"), null, 2);

        Mariage m7 = new Mariage(h3, f7, sdf.parse("2008-09-10"), null, 1);
        Mariage m8 = new Mariage(h3, f3, sdf.parse("2015-04-05"), sdf.parse("2017-11-20"), 2);

        Mariage m9 = new Mariage(h4, f8, sdf.parse("2000-02-14"), null, 4);

        Mariage m10 = new Mariage(h5, f9, sdf.parse("2020-06-20"), null, 0);
        Mariage m11 = new Mariage(h5, f10, sdf.parse("2021-03-15"), null, 1);

        mariageService.create(m1);
        mariageService.create(m2);
        mariageService.create(m3);
        mariageService.create(m4);
        mariageService.create(m5);
        mariageService.create(m6);
        mariageService.create(m7);
        mariageService.create(m8);
        mariageService.create(m9);
        mariageService.create(m10);
        mariageService.create(m11);

        System.out.println("✓ Mariages créés avec succès\n");

        // ==========================================
        // 3. AFFICHER LA LISTE DES FEMMES
        // ==========================================
        System.out.println("\n>>> 3. Liste de toutes les femmes\n");
        System.out.printf("%-15s %-15s %-15s %-20s %-15s%n", "NOM", "PRÉNOM", "TÉLÉPHONE", "ADRESSE", "DATE NAISSANCE");

        List<Femme> toutesFemmes = femmeService.findAll();
        for (Femme f : toutesFemmes) {
            System.out.printf("%-15s %-15s %-15s %-20s %-15s%n",
                f.getNom(),
                f.getPrenom(),
                f.getTelephone(),
                f.getAdresse(),
                sdf.format(f.getDateNaissance())
            );
        }
        System.out.println("Total: " + toutesFemmes.size() + " femmes\n");


        System.out.println("\n>>> 4. Femme la plus âgée\n");

        Femme femmePlusAgee = toutesFemmes.stream()
            .min((f_1, f_2) -> f_1.getDateNaissance().compareTo(f_2.getDateNaissance()))
            .orElse(null);

        if (femmePlusAgee != null) {
            System.out.println("La femme la plus âgée                      ");
            System.out.println("Nom: " + femmePlusAgee.getNom() + " " + femmePlusAgee.getPrenom());
            System.out.println("  Date de naissance: " + sdf.format(femmePlusAgee.getDateNaissance()));
            System.out.println("  Téléphone: " + femmePlusAgee.getTelephone());
            System.out.println("  Adresse: " + femmePlusAgee.getAdresse());
        }



        System.out.println("\n>>> 5. Épouses de l'homme: " + h1.getNom() + "\n");

        Date dateDebut = sdf.parse("2000-01-01");
        Date dateFin = sdf.parse("2025-12-31");
        List<Femme> epouses = hommeService.getEpousesEntreDeuxDates(h1, dateDebut, dateFin);

        System.out.println("Période: " + sdf.format(dateDebut) + " à " + sdf.format(dateFin));

        if (epouses.isEmpty()) {
            System.out.println("Aucune épouse trouvée pour cette période");
        } else {
            for (Femme f : epouses) {
                System.out.println("• " + f.getPrenom() + " " + f.getNom() + " (née le " + sdf.format(f.getDateNaissance()) + ")");
            }
        }
        System.out.println("Total: " + epouses.size() + " épouse(s)\n");


        System.out.println("\n>>> 6. Nombre d'enfants de: " + f1.getPrenom() + " " + f1.getNom() + "\n");

        Date date1 = sdf.parse("2010-01-01");
        Date date2 = sdf.parse("2020-12-31");
        long nbEnfants = femmeService.getNombreEnfantsEntreDeuxDates(f1, date1, date2);

        System.out.println("Période: " + sdf.format(date1) + " à " + sdf.format(date2));
        System.out.println("Nombre total d'enfants: " + nbEnfants);


        System.out.println("\n>>> 7. Femmes mariées deux fois ou plus\n");

        List<Femme> femmesMultiMariages = femmeService.getFemmesMarieesPlusieuresFois();

        if (femmesMultiMariages.isEmpty()) {
            System.out.println("Aucune femme mariée plusieurs fois trouvée");
        } else {
            for (Femme f : femmesMultiMariages) {
                System.out.println("• " + f.getPrenom() + " " + f.getNom());
            }
        }
        System.out.println("Total: " + femmesMultiMariages.size() + " femme(s)\n");


        System.out.println("\n>>> 8. Hommes mariés à quatre femmes (Criteria API)\n");

        Date date3 = sdf.parse("2000-01-01");
        Date date4 = sdf.parse("2025-12-31");
        long nbHommes = mariageService.getNombreHommesMariesQuatreFemmes(date3, date4);
        List<Homme> hommes4Femmes = mariageService.getHommesMariesQuatreFemmes(date3, date4);

        System.out.println("Période: " + sdf.format(date3) + " à " + sdf.format(date4));
        System.out.println("Nombre d'hommes mariés à exactement 4 femmes: " + nbHommes);

        if (!hommes4Femmes.isEmpty()) {
            System.out.println("\nListe des hommes:");
            for (Homme h : hommes4Femmes) {
                System.out.println("• " + h.getNom() + " (né le " + sdf.format(h.getDateNaissance()) + ")");
            }
        }

        System.out.println("\n>>> 9. Détails complets des mariages de: " + h1.getNom() + "\n");

        hommeService.afficherMariagesHomme(h1);

        System.out.println("   RÉSUMÉ DES STATISTIQUES");
        System.out.println("• Nombre d'hommes: " + hommeService.findAll().size());
        System.out.println("• Nombre de femmes: " + femmeService.findAll().size());
        System.out.println("• Nombre de mariages: " + mariageService.findAll().size());
        System.out.println("\n✓ Programme de test terminé avec succès!\n");
    }
}