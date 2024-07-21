/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GerenciaPetsDados;

import com.sun.source.tree.TryTree;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection; 
/**
 *
 * @author ANDRESSA
 */
public class ModuloConexao {
    public static Connection conectar(){
        java.sql.Connection conexao = null;
    String driver = "com.mysql.cj.jdbc.Driver";
    /* Armazenado informações referentes ao banco*/
    String url = "jdbc:mysql://127.0.0.1:3306/";
    String user = "root";
    String password = "admin";
    
    
        /* Estabelecendo conexão com o banco  */
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } 
        }
    }


