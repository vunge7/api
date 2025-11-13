package com.dvml.api.controller;


import com.dvml.api.entity.Pessoa;
import com.dvml.api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.IOException;
import java.nio.file.*;


import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping ("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping("/all")
    public List<Pessoa> getAllPessoas() {
        return service.listarTodasPessoa();
    }

    @GetMapping("/{id}")
    public Pessoa getAllpessoaById(@PathVariable long id) {
        return service.getPessoaById(id);
    }


    @GetMapping("/nif/{nif}")
    public Pessoa getAllpessoaByNif(@PathVariable String nif) {

        return service.getPessoaByNif(nif);
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pessoa adicionar(
            @Valid @ModelAttribute Pessoa pessoa,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        // Vincula nomePhoto mesmo sem salvar arquivo (opcional)
        if (file != null && !file.isEmpty()) {
            final Path uploadDir = Paths.get("uploads/pessoa/fotos"); // ajuste caminho
            try {
                Files.createDirectories(uploadDir);
                final String original = StringUtils.cleanPath(file.getOriginalFilename());
                final String filename = System.currentTimeMillis() + "_" + original;
                final Path destino = uploadDir.resolve(filename);

                // Sobrescreve se existir; se não quiser, remova REPLACE_EXISTING
                Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

                pessoa.setNomePhoto(filename);
            } catch (IOException e) {
                // Trate IOException de forma controlada
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Erro de I/O ao salvar arquivo de foto",
                        e
                );
            }
        }

        // Salva a pessoa normalmente
        return service.criar(pessoa);
    }
    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pessoa updatePessoa(@RequestBody @Valid Pessoa pessoa) {
        return service.update(pessoa);
    }

    @DeleteMapping("/{id}")
    public void deletePessoa(@PathVariable long id) {
        if (service.getPessoaById(id) != null) {
            service.deletePessoa(id);
        } else {
            System.out.println("ERRO...");
        }

    }
}
