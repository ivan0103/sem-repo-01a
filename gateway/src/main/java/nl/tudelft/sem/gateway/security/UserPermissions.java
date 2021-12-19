package nl.tudelft.sem.gateway.security;

public enum UserPermissions {
    CREATE_STUDENT_SERVICE_POST("studentServicePost:create"),
    VIEW_STUDENT_SERVICE_POSTS("studentServicePost:view"),
    CREATE_GENERIC_SERVICE_POST("genericServicePost:create"),
    VIEW_GENERIC_SERVICE_POSTS("genericServicePost:view"),
    CREATE_COMPANY_FEEDBACK("company:createFeedbake"),
    VIEW_COMPANY_FEEDBACK("company:viewFeedback"),
    CREATE_STUDENT_FEEDBACK("student:createFeedback"),
    VIEW_STUDENT_FEEDBACK("student:viewFeedback"),
    CREATE_CONTRACT("contract:create"),
    VIEW_CONTRACT("contract:view");

    private final String permission;

    UserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
