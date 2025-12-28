// ============================================
// FILE: TitreNotification.java
// Location: src/main/java/ma/project/dentalTech/entities/enums/TitreNotification.java
// ============================================
package ma.project.dentalTech.entities.enums;

public enum TitreNotification {
    EN_COURS("En cours"),
    URGENT("Urgent"),
    INFORMATION("Information"),
    RAPPEL("Rappel"),
    ALERTE("Alerte"),
    CONFIRMATION("Confirmation");

    private final String libelle;

    TitreNotification(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}

// ============================================
// FILE: TypeNotification.java
// Location: src/main/java/ma/project/dentalTech/entities/enums/TypeNotification.java
// ============================================
package ma.project.dentalTech.entities.enums;

public enum typeNotification {
    RDV("Rendez-vous"),
    PAIEMENT("Paiement"),
    RAPPEL("Rappel"),
    CONSULTATION("Consultation"),
    URGENCE("Urgence"),
    INFORMATION("Information"),
    SYSTEME("Système");

    private final String libelle;

    typeNotification(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}

// ============================================
// FILE: PrioriteNotification.java
// Location: src/main/java/ma/project/dentalTech/entities/enums/PrioriteNotification.java
// ============================================
package ma.project.dentalTech.entities.enums;

public enum PrioriteNotification {
    BASSE("Basse"),
    NORMALE("Normale"),
    HAUTE("Haute"),
    CRITIQUE("Critique");

    private final String libelle;

    PrioriteNotification(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}

// ============================================
// FILE: CategorieStatistique.java
// Location: src/main/java/ma/project/dentalTech/entities/enums/CategorieStatistique.java
// ============================================
package ma.project.dentalTech.entities.enums;

public enum CategorieStatistique {
    PATIENTS("Patients"),
    CONSULTATIONS("Consultations"),
    FINANCES("Finances"),
    RDV("Rendez-vous"),
    ACTES("Actes médicaux"),
    REVENUS("Revenus"),
    CHARGES("Charges"),
    MENSUEL("Mensuel"),
    ANNUEL("Annuel");

    private final String libelle;

    CategorieStatistique(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}

// ============================================
// USAGE EXAMPLES
// ============================================

/*
// Example 1: Using TitreNotification
TitreNotification titre = TitreNotification.EN_COURS;
System.out.println(titre.getLibelle()); // Output: "En cours"

// Example 2: Using TypeNotification
TypeNotification type = TypeNotification.RDV;
System.out.println(type.getLibelle()); // Output: "Rendez-vous"

// Example 3: Using PrioriteNotification
PrioriteNotification priorite = PrioriteNotification.HAUTE;
System.out.println(priorite.getLibelle()); // Output: "Haute"

// Example 4: Switch statement
switch (type) {
    case RDV:
        System.out.println("Gérer le rendez-vous");
        break;
    case PAIEMENT:
        System.out.println("Traiter le paiement");
        break;
    default:
        System.out.println("Type non géré");
}

// Example 5: Iterating through all values
for (TypeNotification t : TypeNotification.values()) {
    System.out.println(t.name() + " - " + t.getLibelle());
}
*/