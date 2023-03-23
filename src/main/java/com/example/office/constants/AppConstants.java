package com.example.office.constants;

public final class AppConstants {
    private AppConstants() {
    }

    public static final class Permissions {
        public static final String CREATE = "CREATE";
        public static final String VIEW = "VIEW";
        public static final String DELETE = "DELETE";

        private Permissions() {
        }
    }

    public static final class Roles {
        public static final String SUPER_ADMIN = "SUPER_ADMIN";
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";

        private Roles() {
        }
    }
}
