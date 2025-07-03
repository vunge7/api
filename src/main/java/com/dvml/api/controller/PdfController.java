package com.dvml.api.controller;

import com.dvml.api.util.TransformReportToPDF;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @GetMapping("/{fileName}/{id}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String fileName, @PathVariable long id) throws IOException {
        System.out.println("ID: " + id);
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("source_document_id", id);

        // Use o template fixo ou fileName, e gere o PDF com nome dinâmico
        String templateName = fileName; // ou "agendamento" se quiser fixo
        String pdfFileName = fileName + "_" + id + "_" + System.currentTimeMillis();

        new TransformReportToPDF(templateName, hash, pdfFileName);

        String filePath = "reports/pdf/" + pdfFileName + ".pdf";
        File pdfFile = new File(filePath);
        if (!pdfFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename(pdfFileName + ".pdf").build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/generico/{fileName}/{id}")
    public ResponseEntity<byte[]> getPdfGenerico(@PathVariable String fileName, @PathVariable long id) throws IOException {
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("id", id);

        String templateName = fileName;
        String pdfFileName = fileName + "_" + id + "_" + System.currentTimeMillis();

        new TransformReportToPDF(templateName, hash, pdfFileName);

        String filePath = "reports/pdf/" + pdfFileName + ".pdf";
        File pdfFile = new File(filePath);
        if (!pdfFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename(pdfFileName + ".pdf").build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/{fileName}/fita/{inscricao_id}")
    public ResponseEntity<byte[]> getFitaPaciente(@PathVariable String fileName, @PathVariable long inscricao_id) {
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("inscricao_id", inscricao_id);

        String templateName = fileName;
        String pdfFileName = fileName + "_fita_" + inscricao_id + "_" + System.currentTimeMillis();

        new TransformReportToPDF(templateName, hash, pdfFileName);
        String filePath = "reports/pdf/" + pdfFileName + ".pdf";
        File pdfFile = new File(filePath);
        try {
            byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());
            if (!pdfFile.exists()) {
                return ResponseEntity.notFound().build();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("inline").filename(pdfFileName + ".pdf").build());
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}