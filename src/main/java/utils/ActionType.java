package utils;

public enum ActionType {
    CONNECT_DATABASE,
    CREATE_DATA,
    POPULATE_FROM_LIST,
    POPULATE_FROM_FILE,
    DUMP_TO_CSV,
    EXIT;

    public static ActionType from(String type) {
        switch (type) {
            case "connect to db":
                return ActionType.CONNECT_DATABASE;
            case "create list":
                return ActionType.CREATE_DATA;
            case "insert list":
                return ActionType.POPULATE_FROM_LIST;
            case "insert file":
                return ActionType.POPULATE_FROM_FILE;
            case "output file":
                return ActionType.DUMP_TO_CSV;
            case "exit":
                return ActionType.EXIT;
        }

        throw new IllegalArgumentException("Invalid argument!");
    }
}
