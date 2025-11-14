package com.dvml.api.service;

import com.dvml.api.entity.Pessoa;
import com.dvml.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repo;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> listarTodasPessoa() {
        return pessoaRepository.findAllOrderByNomeAsc();
    }

    public Pessoa getPessoaById(long id) {
        return repo.findById(id).get();
    }

    public Pessoa getPessoaByNif(String nif) {
        return repo.findByNif(nif);
    }

    public Pessoa criar(Pessoa pessoa) {
        return repo.save(pessoa);
    }

    public Pessoa update(Pessoa pessoa) {
        Pessoa pessoaToUpdate = repo.findById(pessoa.getId()).get();

        pessoaToUpdate.setId(pessoa.getId());
        pessoaToUpdate.setEmail(pessoa.getEmail());
        pessoaToUpdate.setNome(pessoa.getNome());
        pessoaToUpdate.setGenero(pessoa.getGenero());
        pessoaToUpdate.setTelefone(pessoa.getTelefone());
        pessoaToUpdate.setEndereco(pessoa.getEndereco());
        pessoaToUpdate.setDataNascimento(pessoa.getDataNascimento());
        pessoaToUpdate.setApelido(pessoa.getApelido());
        pessoaToUpdate.setBairro(pessoa.getBairro());
        pessoaToUpdate.setNif(pessoa.getNif());
        pessoaToUpdate.setMae(pessoa.getMae());
        pessoaToUpdate.setPai(pessoa.getPai());
        pessoaToUpdate.setNacionalidade(pessoa.getNacionalidade());
        pessoaToUpdate.setLocalNascimento(pessoa.getLocalNascimento());
        pessoaToUpdate.setMunicipioEndereco(pessoa.getMunicipioEndereco());
        pessoaToUpdate.setMunicipioNascimento(pessoa.getMunicipioNascimento());
        pessoaToUpdate.setPaisEndereco(pessoa.getPaisEndereco());
        pessoaToUpdate.setPaisNascimento(pessoa.getPaisNascimento());
        pessoaToUpdate.setProvinciaEndereco(pessoa.getProvinciaEndereco());
        pessoaToUpdate.setProvinciaNascimento(pessoa.getProvinciaNascimento());
        pessoaToUpdate.setRaca(pessoa.getRaca());
        pessoaToUpdate.setHabilitacao(pessoa.getHabilitacao());
        pessoaToUpdate.setEstadoCivil(pessoa.getEstadoCivil());
        pessoaToUpdate.setProfissao(pessoa.getProfissao());
        pessoaToUpdate.setEmpresaId(pessoa.getEmpresaId());


        return repo.save(pessoaToUpdate);
    }

    public Pessoa updatePhoto(String nomePhoto, long pessoId) {
        Pessoa pessoaToUpdate = repo.findById(pessoId).get();
        pessoaToUpdate.setNomePhoto(nomePhoto);
        return repo.save(pessoaToUpdate);
    }

    public ResponseEntity<String> deletePessoa(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Pessoa deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao deletar Pessoa.");
        }
    }
    public String limparNomeArquivo(String nomeOriginal) {
        if (nomeOriginal == null || nomeOriginal.isEmpty()) {
            return "arquivo";
        }

        // Normaliza para decompor acentos (ex: ç → c, ã → a)
        String normalizado = Normalizer.normalize(nomeOriginal, Normalizer.Form.NFD);

        // Remove todos os caracteres que não são letras, números, hífen ou underscore
        String limpo = normalizado
                .replaceAll("[^\\p{ASCII}]", "")           // Remove acentos e caracteres não-ASCII
                .replaceAll("[^a-zA-Z0-9._-]", "_")        // Substitui tudo estranho por _
                .replaceAll("\\s+", "_")                   // Espaços → _
                .replaceAll("_+", "_")                     // Remove underscores duplicados
                .replaceAll("^_+|_+$", "");                // Remove _ no início/fim

        // Extrai apenas o nome sem extensão para limpeza
        int dotIndex = limpo.lastIndexOf('.');
        String nomeSemExt = dotIndex > 0 ? limpo.substring(0, dotIndex) : limpo;
        String extensao = dotIndex > 0 ? limpo.substring(dotIndex) : "";

        // Garante que o nome não fique vazio
        if (nomeSemExt.isEmpty()) {
            nomeSemExt = "foto";
        }

        return nomeSemExt + extensao.toLowerCase();
    }
}
