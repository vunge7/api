package com.dvml.api.controller;

import com.dvml.api.util.TransformReportToPDF;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        new TransformReportToPDF(fileName, hash);

        //caminho do PDF -> busca o ficheiro convertido isto é .pdf
        String filePath = "reports/pdf/" + fileName + ".pdf";
        File pdfFile = new File(filePath);
        if (!pdfFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());

        //retornar o PDF como resposta
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename(fileName).build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }




    @GetMapping("/generico/{fileName}/{id}")
    public ResponseEntity<byte[]> getPdfGenerico(@PathVariable String fileName, @PathVariable long id) throws IOException {

        HashMap<String, Object> hash = new HashMap<>();
        hash.put("id", id);
        new TransformReportToPDF(fileName, hash);

        //caminho do PDF -> busca o ficheiro convertido isto é .pdf
        String filePath = "reports/pdf/" + fileName + ".pdf";
        File pdfFile = new File(filePath);
        if (!pdfFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());

        //retornar o PDF como resposta
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename(fileName).build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/{fileName}/fita/{inscricao_id}")
    public ResponseEntity<byte[]> getFitaPaciente(@PathVariable String fileName, @PathVariable long inscricao_id) {
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("inscricao_id", inscricao_id);
        new TransformReportToPDF(fileName, hash);
        String filePath = "reports/pdf/" + fileName + ".pdf";
        File pdfFile = new File(filePath);
        try {
            byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());
            if (!pdfFile.exists()) {
                return ResponseEntity.notFound().build();
            }
            //retornar o PDF como resposta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("inline").filename(fileName).build());
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }


    }
}
