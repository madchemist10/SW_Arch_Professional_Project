package app.database;

/**
 * Instantiates Statements for Database SQL queries.
 */
class DBStatementBuilder {

    /**Key word for selecting from a table.*/
    private static final String SELECT = "SELECT ";
    /**Key word for updating a table.*/
    private static final String UPDATE = "UPDATE ";
    /**Key word for inserting into a table.*/
    private static final String INSERT_INTO = "INSERT INTO ";
    /**Key word for conditionals in sql statements in a table.*/
    private static final String WHERE = " WHERE ";
    /**Key word for pulling from a table.*/
    private static final String FROM = " FROM ";
    /**Key word for assigning set of values for an update statement.*/
    private static final String VALUES = " VALUES ";
    /**Key word for assigning set of keys for an update statement.*/
    private static final String SET = " SET ";

    /**
     * Build a select statement to be executed against the database.
     * Should be in the form of {SELECT * FROM table}
     * @param selectVar String query that is to be preceded by select
     * @return properly formatted string of a sql statement.
     */
    static String selectStatement(String selectVar){
        return SELECT+selectVar;
    }

    /**
     * Build a update statement to be executed against the database.
     * Should be in the form of {UPDATE table SET _,_ VALUES _,_}
     * @param updateVar String query that is to be preceded by update
     * @return properly formatted string of a sql statement.
     */
    static String updateStatement(String updateVar){
        return UPDATE+updateVar;
    }

    /**
     * Build a insert statement to be executed against the database.
     * Should be in the form {INSERT INTO table {_,_} {_,_}}.
     * Each column set and insert set should be same number of elements.
     * @param tableName name of the table to insert into.
     * @param columnVar comma separated column set that is to be inserted into the table.
     * @param insertVar comma separated values to be inserted into table.
     * @return properly formatted string of a sql statement.
     */
    static String insertStatement(String tableName, String columnVar, String insertVar){
        return INSERT_INTO+tableName+" " + "("+columnVar+") "+insertVar;
    }

    /**
     * Build a where statement to be executed against the database.
     * Should be in the form of {statement WHERE conditional}.
     * @param whereVar String query that is to be preceded by where
     * @return properly formatted string of a sql statement.
     */
    static String whereStatement(String whereVar){
        return WHERE+whereVar;
    }

    /**
     * Build a from statement to be executed against the database.
     * Used in conjunction with {@link #selectStatement(String)}.
     * @param fromVar String query that is to be preceded by from.
     * @return properly formatted string of a sql statement.
     */
    static String fromStatement(String fromVar){
        return FROM+fromVar;
    }

    /**
     * Build a value statement to be used with {@link #setStatement(String)}.
     * @param valueVar of the value to associate with a key that is to be set.
     * @return properly formatted string of a sql statement.
     */
    static String valueStatement(String valueVar){
        return VALUES+"("+valueVar+")";
    }

    /**
     * Build a set statement to be executed against the database.
     * @param setVar of the key that is to be set.
     * @return properly formatted string of a sql statement.
     */
    static String setStatement(String setVar){
        return SET+setVar;
    }

    /**
     * Build a create table statement to be executed against the database.
     * @param tableName of the table that is to be created.
     * @return properly formatted string of a sql statement.
     */
    static String createTableStatement(String tableName){
        return "CREATE TABLE IF NOT EXISTS '"+tableName+"'";
    }

    /**
     * Build a create table text column statement to be executed against the database.
     * @param column that is to be added with data type text.
     * @return properly formatted string of a sql statement.
     */
    static String createTableTextColumn(String column){
        return "\""+column+"\" TEXT";
    }
}
