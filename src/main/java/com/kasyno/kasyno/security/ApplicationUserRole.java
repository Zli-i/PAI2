package com.kasyno.kasyno.security;

import com.google.common.collect.Sets;
import java.util.Set;

import static com.kasyno.kasyno.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {

    USER(Sets.newHashSet(USER_READ,USER_WRITE)),
    ADMIN(Sets.newHashSet(ADMIN_READ,ADMIN_WRITE,USER_READ,USER_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
