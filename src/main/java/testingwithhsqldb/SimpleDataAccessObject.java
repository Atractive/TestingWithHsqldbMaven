package testingwithhsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class SimpleDataAccessObject {
	private final DataSource myDataSource;
	
	public SimpleDataAccessObject(DataSource dataSource) {
		myDataSource = dataSource;
	}

	/**
	 * Renvoie le nom d'un client à partir de son ID
	 * @param id la clé du client à chercher
	 * @return le nom du client (LastName) ou null si pas trouvé
	 * @throws SQLException 
	 */
	public String nameOfCustomer(int id) throws SQLException {
		String result = null;
		
		String sql = "SELECT LastName FROM Customer WHERE ID = ?";
		try (Connection myConnection = myDataSource.getConnection(); 
		     PreparedStatement statement = myConnection.prepareStatement(sql)) {
			statement.setInt(1, id); // On fixe le 1° paramètre de la requête
			try ( ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					// est-ce qu'il y a un résultat ? (pas besoin de "while", 
                                        // il y a au plus un enregistrement)
					// On récupère les champs de l'enregistrement courant
					result = resultSet.getString("LastName");
				}
			}
		}
		// dernière ligne : on renvoie le résultat
		return result;
	}
        
        public void insertProduct(int Id, String Nom, int Prix) {
	
            String sql = "INSERT INTO Product VALUES(?,?,?);";
            try (   Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)
                ) {
                    
                    stmt.setInt(1, Id);
                    stmt.setString(2, Nom);
                    stmt.setInt(3, Prix);
			
                    stmt.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(SimpleDataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
            
        public int numberOfProduct() throws SQLException {
            
		int result = 0;
		String sql = "SELECT COUNT(*) AS NUMBER FROM PRODUCT";

		try (   Connection connection = myDataSource.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
                    ) {
                        if (rs.next()) {
                            result = rs.getInt("NUMBER");
                        }
		} 

		return result;
                
	}
                    
        
	
}
