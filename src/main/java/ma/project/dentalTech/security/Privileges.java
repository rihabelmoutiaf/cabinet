package ma.project.dentalTech.security;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import ma.project.dentalTech.entities.enums.RoleType;

public final class Privileges {

    public static final String PATIENT_ACCESS = "PATIENT_ACCESS";
    public static final String DOSSIER_ACCESS = "DOSSIER_ACCESS";
    public static final String CAISSE_ACCESS = "CAISSE_ACCESS";
    public static final String USERS_ACCESS = "USERS_ACCESS";
    public static final String CABINET_ACCESS = "CABINET_ACCESS";

    private Privileges() {
    }

    public static Set<String> all() {
        Set<String> all = new HashSet<>();
        all.add(PATIENT_ACCESS);
        all.add(DOSSIER_ACCESS);
        all.add(CAISSE_ACCESS);
        all.add(USERS_ACCESS);
        all.add(CABINET_ACCESS);
        return all;
    }

    public static Set<String> forRole(RoleType role) {
        if (role == null) {
            return Collections.emptySet();
        }
        Set<String> privileges = new HashSet<>();
        switch (role) {
            case ADMIN -> privileges.addAll(all());
            case MEDECIN -> {
                privileges.add(PATIENT_ACCESS);
                privileges.add(DOSSIER_ACCESS);
                privileges.add(CAISSE_ACCESS);
                privileges.add(CABINET_ACCESS);
            }
            case SECRETAIRE -> {
                privileges.add(PATIENT_ACCESS);
                privileges.add(CAISSE_ACCESS);
            }
            case STAFF -> {
                // no default privileges
            }
        }
        return privileges;
    }
}
