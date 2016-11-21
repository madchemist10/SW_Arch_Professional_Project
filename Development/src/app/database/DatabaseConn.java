package app.database;
import java.sql.*;
import java.util.ArrayList;

/**
 * Database class responsible for database connection establishment.
 * http://www.tutorialspoint.com/sqlite/sqlite_java.htm
 */

public class DatabaseConn {

    /**Reference to the connection for this database connection.*/
    private Connection conn = null;
    /**Database filepath for which this connection is connecting to.*/
    private String database = null;

    /**
     * Create and establish a new connection to the database.
     * @param db String representation of the filepath to the database.
     */
    DatabaseConn(String db){
        this.database = db;
        this.conn = this.makeConnection(this.database);
    }

    /**
     * Set the database filepath.
     * @param database filepath to the database.
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * Retrieve the filepath to the database.
     * @return String representation of the filepath of this connection's database.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Establish a connection to an sqlite database.
     * Uses sqlite library.
     * @param database filepath to the database.
     * @return Connection that has been established or null
     *      if an error occurred.
     */
    private Connection makeConnection(String database){
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:"+database);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Attempt to close the current connection.
     * Could result in null pointer if connection was
     * failed to be established.
     */
    private void closeConnection(){
        try{
            this.conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Ensure the connection is alive, if connection
     * does not exist, reconnect.
     * @return 1 if connection if connected,
     *          0 if not connected.
     */
    private Integer verifyConnection(){
        if (this.conn == null){
            this.conn = this.makeConnection(this.database);
            if (this.conn != null) {
                return 1;
            }else {
                return 0;
            }
        }
        return 1;
    }

    /**
     * Create a table from statement.
     * @param table full formatted sql query for
     *              creating a table with columns
     *              and data types.
     */
    void makeTable(String table){
        Connection conn = this.conn;
        if (this.verifyConnection() == 1){
            try{
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(table);
                stmt.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            this.closeConnection();
        }
    }

    /**
     * Insert into the table, a set of values for a
     * given set of columns.
     * @param insert full formatted sql query for inserting
     *               data into a table.
     */
    void insertIntoTable(String insert){
        Connection conn = this.conn;
        if (this.verifyConnection() == 1){
            try{
                conn.setAutoCommit(false);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(insert);
                stmt.close();
                conn.commit();
            }catch (Exception e){
                e.printStackTrace();
            }
            this.closeConnection();
        }
    }

    /**
     * Select data from a table in the database.
     * @param selectStatement full formatted query to select
     *                        data from a table in the database.
     * @return list of entries reported by the query.
     */
    ArrayList<String[]> selectFromTable(String selectStatement){
        ArrayList<String[]> returnData = new ArrayList<>();
        Connection conn = this.conn;
        if (this.verifyConnection() == 1) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(selectStatement);
                ResultSetMetaData rsmd = rs.getMetaData();
                Integer numCols = rsmd.getColumnCount();
                while( rs.next()){
                    String[] row_data = new String[numCols];
                    for(int i = 1;i<=numCols;i++){
                        row_data[i-1]=(rs.getString(i));
                    }
                    returnData.add(row_data);
                }
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.closeConnection();
            return returnData;
        }
        return returnData;
    }

    /**
     * Update an entry in a given table from this database connection.
     * @param updateStatement full formatted sqlite statement
     *                        for updating an entry in a given table.
     */
    void updateTableEntry(String updateStatement){
        Connection conn = this.conn;
        if (this.verifyConnection() == 1) {
            try {
                conn.setAutoCommit(false);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(updateStatement);
                stmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.closeConnection();
        }
    }
}

