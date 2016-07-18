
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.hsqldb.jdbc.JDBCDataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauro.larese
 */
public class Test {
    public static void main(String[] args) {
        Connection conn;
        String db_file_name_prefix = "D:/tmp_desktop/scrigno-gpdati/data/gpdati.db";
        
        try {
               
            JDBCDataSource ds = new JDBCDataSource();
            ds.setDatabase("jdbc:hsqldb:" + db_file_name_prefix);
            conn = ds.getConnection("sa", "");
            QueryRunner qr = new QueryRunner(ds);
            
            String sql = ""
            +"  CREATE TABLE gpdati_dispo (  "
            +"    KEY varchar(100)  PRIMARY KEY " 
            +"  ) ";

            //qr.update(sql);
            
            qr.update("SHUTDOWN");
            DbUtils.close(conn);
            
        } catch (SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}
