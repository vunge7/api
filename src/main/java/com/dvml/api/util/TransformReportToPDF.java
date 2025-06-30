package com.dvml.api.util;

import net.sf.jasperreports.engine.*;

import java.sql.Connection;
import java.util.HashMap;

public class TransformReportToPDF {

    private String jrxmlFile;
    private String jasperFile;
    private String pdfFile;

    // Configura a conexão JDBC
    Connection conexao;
    private  HashMap<String, Object> parametros;


    public TransformReportToPDF(String fileName,  HashMap<String, Object> hash) {
        conexao = new BDConexao().connection;
        this.parametros = hash;
        // Caminho do arquivo .jrxml (design do relatório)
        jrxmlFile = "reports/jasper/" + fileName + ".jrxml";
        jasperFile = "reports/jasper/" + fileName + ".jasper";
        pdfFile = "reports/pdf/" + fileName + ".pdf";
        gerarPDF();
    }


    private void gerarPDF() {

        try {
            //compila o ficheiro jrxml
            JasperCompileManager.compileReportToFile(jrxmlFile, jasperFile);


            // parametros.put("titulo", "Relatório de Usuários");

            // Preenche o relatório com dados do banco
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile, parametros, conexao);

            // Exporta o relatório para PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile);
            System.out.println("PDF gerado com sucesso: " + pdfFile);

            // Fecha a conexão
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}