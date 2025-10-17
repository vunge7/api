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
    private HashMap<String, Object> parametros;

    public TransformReportToPDF(String templateName, HashMap<String, Object> hash, String pdfFileName) {
        conexao = new BDConexao().getConnection();
        this.parametros = hash;
        // Caminho do arquivo .jrxml (design do relatório)
        jrxmlFile = "reports/jasper/" + templateName + ".jrxml";
        jasperFile = "reports/jasper/" + templateName + ".jasper";
        pdfFile = "reports/pdf/" + pdfFileName + ".pdf";
        gerarPDF();
    }

    private void gerarPDF() {
        try {
            // Compila o ficheiro jrxml
            JasperCompileManager.compileReportToFile(jrxmlFile, jasperFile);

            // Preenche o relatório com dados do banco
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile, parametros, conexao);

            // Exporta o relatório para PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile);
            System.out.println("PDF gerado com sucesso: " + pdfFile);

            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}