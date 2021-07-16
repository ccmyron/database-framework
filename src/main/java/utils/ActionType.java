package utils;

public enum ActionType {
    CONNECT_DATABASE,
    FREE_QUERY,
    LIST_INSERT,
    BULK_INSERT,
    PRINT_TO_CSV,
    PRINT_TO_CONSOLE,
    CREATE_DATA,
    EXIT;

    public static ActionType from(String type) throws ArgumentException {
        switch (type) {
            case "connect":
                return ActionType.CONNECT_DATABASE;
            case "query":
                return ActionType.FREE_QUERY;
            case "insert":
                return ActionType.LIST_INSERT;
            case "bulk":
                return ActionType.BULK_INSERT;
            case "print-csv":
                return ActionType.PRINT_TO_CSV;
            case "print-console":
                return ActionType.PRINT_TO_CONSOLE;
            case "list":
                return ActionType.CREATE_DATA;
            case "exit":
                return ActionType.EXIT;
        }

        throw new ArgumentException("Invalid argument!");
    }
}
