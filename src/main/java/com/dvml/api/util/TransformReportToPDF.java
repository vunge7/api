package com.dvml.api.util;

import net.sf.jasperreports.engine.*;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

public class TransformReportToPDF {

    private final String templateName;
    private final String pdfFileName;

    private Connection conexao;
    private HashMap<String, Object> parametros;

    public TransformReportToPDF(String templateName, HashMap<String, Object> hash, String pdfFileName) {
        this.conexao = new BDConexao().getConnection();
        this.parametros = hash;
        this.templateName = templateName;
        this.pdfFileName = pdfFileName;
        gerarPDF();
    }

    private void gerarPDF() {
        try {
            // Carrega o arquivo .jrxml do classpath (src/main/resources/reports/jasper)
            InputStream jrxmlStream = getClass().getResourceAsStream("/reports/jasper/" + templateName + ".jrxml");
            if (jrxmlStream == null) {
                throw new JRException("Arquivo .jrxml não encontrado no classpath: /reports/jasper/" + templateName + ".jrxml");
            }

            // Compila o relatório a partir do InputStream
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            // Preenche o relatório com os dados do banco
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexao);

            // Garante que a pasta de saída exista
            File outputDir = new File("reports/pdf/");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Caminho final do PDF
            String pdfFile = "reports/pdf/" + pdfFileName + ".pdf";

            // Exporta o relatório para PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile);

            System.out.println("✅ PDF gerado com sucesso: " + pdfFile);

            conexao.close();
        } catch (Exception e) {
            System.err.println("❌ Erro ao gerar PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
