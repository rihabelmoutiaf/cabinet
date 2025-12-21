package ma.project.dentalTech.entities.users;

import jakarta.persistence.*;
import ma.project.dentalTech.entities.base.BaseEntity;
import ma.project.dentalTech.entities.enums.RoleType;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType roleType;

    public Role() {}

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}
