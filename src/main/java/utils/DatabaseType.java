package utils;

public enum DatabaseType {
    MS_SQL,
    POSTGRESQL,
    MYSQL;

    public static DatabaseType from(String type) throws ArgumentException {
        switch (type) {
            case "mssql":
                return DatabaseType.MS_SQL;
            case "postgres":
                return DatabaseType.POSTGRESQL;
            case "mysql":
                return DatabaseType.MYSQL;
        }
        throw new ArgumentException("Invalid argument!");
    }
}
