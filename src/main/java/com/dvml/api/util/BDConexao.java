package com.dvml.api.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConexao {

    // Configurações da conexão PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/kitanda_hospitalar_db";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "DM.2016#321"; // <-- ajusta conforme a tua senha
    private Connection connection = null;

    public BDConexao() {
        try {
            // Registra o driver PostgreSQL (opcional no Java 17+, mas seguro para compatibilidade)
            Class.forName("org.postgresql.Driver");

            // Estabelece a conexão
            connection = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("✅ Conexão com PostgreSQL estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver PostgreSQL não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Falha na conexão com o banco de dados PostgreSQL:");
            System.err.println("Código SQL: " + e.getSQLState());
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void fecharConexao() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔒 Conexão com PostgreSQL encerrada.");
            } catch (SQLException e) {
                System.err.println("❌ Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
