package com.dvml.api.util;
import java.sql.Connection;
import java.sql.DriverManager;

public class BDConexao {
    // Configura a conexão JDBC
    private final String url = "jdbc:mysql://localhost:3307/kitanda_hospitalar_db?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private final String usuario = "root";
    private final String senha = "DoV90x?#";
    public Connection connection = null;

    public BDConexao() {
        try {
             connection = DriverManager.getConnection(url, usuario, senha);
        }
        catch (Exception e ){
            System.out.println("Falha na conexão com o banco de dados");
        }

    }


}
