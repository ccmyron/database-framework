package utils;

public enum DatabaseType {
    MS_SQL,
    POSTGRESQL,
    MYSQL;

    public static DatabaseType from(String type) {
        switch (type) {
            case "mssql":
                return DatabaseType.MS_SQL;
            case "postgres":
                return DatabaseType.POSTGRESQL;
            case "mysql":
                return DatabaseType.MYSQL;
        }
        throw new IllegalArgumentException("Invalid argument!");
    }
}
