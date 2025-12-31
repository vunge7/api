package com.dvml.api.controller;

import com.dvml.api.entity.Pessoa;
import com.dvml.api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
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
        if (file != null && !file.isEmpty()) {
            final Path uploadDir = Paths.get("uploads/pessoa/fotos");
            try {
                Files.createDirectories(uploadDir);

                String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
                String extensao = "";
                int dotIndex = originalFilename.lastIndexOf('.');
                if (dotIndex > 0 && dotIndex < originalFilename.length() - 1) {
                    extensao = originalFilename.substring(dotIndex);
                }

                String nomeLimpo = service.limparNomeArquivo(originalFilename);
                String filename = System.currentTimeMillis() + "_" + nomeLimpo + extensao;
                Path destino = uploadDir.resolve(filename);

                Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
                pessoa.setNomePhoto(filename);

            } catch (IOException e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Erro de I/O ao salvar arquivo de foto",
                        e
                );
            }
        }

        return service.criar(pessoa);
    }

    @PutMapping("/edit/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pessoa updatePessoa(@PathVariable Long id, @RequestBody @Valid Pessoa pessoa) {
        // garante que o ID usado na atualização é o do path
        pessoa.setId(id);
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