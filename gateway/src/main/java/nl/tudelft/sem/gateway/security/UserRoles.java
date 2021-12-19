package nl.tudelft.sem.gateway.security;

import com.google.common.collect.Sets;
import java.util.Set;

public enum UserRoles {
    STUDENT(Sets.newHashSet(
            UserPermissions.CREATE_STUDENT_FEEDBACK,
            UserPermissions.VIEW_GENERIC_SERVICE_POSTS,
            UserPermissions.CREATE_COMPANY_FEEDBACK,
            UserPermissions.VIEW_COMPANY_FEEDBACK,
            // For student to view their own feedback
            UserPermissions.VIEW_STUDENT_FEEDBACK,
            UserPermissions.CREATE_CONTRACT,
            UserPermissions.VIEW_CONTRACT
            )),
    COMPANY(Sets.newHashSet(
            UserPermissions.CREATE_GENERIC_SERVICE_POST,
            UserPermissions.VIEW_STUDENT_SERVICE_POSTS,
            UserPermissions.CREATE_STUDENT_FEEDBACK,
            UserPermissions.VIEW_STUDENT_FEEDBACK,
            // For company to view their own feedback
            UserPermissions.VIEW_COMPANY_FEEDBACK,
            UserPermissions.CREATE_CONTRACT,
            UserPermissions.VIEW_CONTRACT
    ));

    private final Set<UserPermissions> permissions;

    UserRoles(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }
}

