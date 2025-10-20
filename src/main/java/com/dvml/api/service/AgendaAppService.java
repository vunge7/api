package com.dvml.api.service;

import com.dvml.api.dto.AgendaAppDTO;
import com.dvml.api.dto.ConsultaAppDTO;
import com.dvml.api.dto.ProdutoDTO;
import com.dvml.api.entity.*;
import com.dvml.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendaAppService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private LinhaAgendaRepository linhaAgendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public ResponseEntity<?> criarAgendaViaApp(AgendaAppDTO dto) {

        // 🔹 1. Verifica se já existe pessoa com o mesmo NIF
        Pessoa pessoaExistente = pessoaRepository.findByNif(dto.getNif());

        Pessoa pessoa;
        if (pessoaExistente != null) {
            // Já existe -> reutiliza a pessoa existente
            pessoa = pessoaExistente;
        } else {
            // 🔹 2. Cria nova pessoa
            pessoa = new Pessoa();
            pessoa.setNome(dto.getNome());
            pessoa.setApelido(dto.getNome()); // usa o mesmo nome como apelido padrão
            pessoa.setNif(dto.getNif());
            pessoa.setTelefone(dto.getTelefone());
            pessoa.setGenero(dto.getGenero());

            // ✅ Novo campo
            pessoa.setEmpresaId(1l);

            // 🔹 Campos opcionais com "N/A"
            pessoa.setLocalNascimento("N/A");
            pessoa.setEmail("N/A");
            pessoa.setEndereco("N/A");
            pessoa.setBairro("N/A");
            pessoa.setEstadoCivil("N/A");
            pessoa.setPai("N/A");
            pessoa.setMae("N/A");
            pessoa.setNacionalidade("N/A");
            pessoa.setRaca("N/A");
            pessoa.setPaisEndereco("N/A");
            pessoa.setProvinciaEndereco("N/A");
            pessoa.setMunicipioEndereco("N/A");
            pessoa.setPaisNascimento("N/A");
            pessoa.setProvinciaNascimento("N/A");
            pessoa.setMunicipioNascimento("N/A");
            pessoa.setProfissao("N/A");
            pessoa.setHabilitacao("N/A");
            pessoa.setNomePhoto("N/A");
            pessoa.setDataNascimento(null); // opcional, pode ser preenchida depois

            pessoaRepository.save(pessoa);
        }

        // 🔹 3. Cria ou reutiliza o paciente vinculado à pessoa
        Paciente pacienteExistente = pacienteRepository.findByPessoaId(pessoa.getId());
        Paciente paciente;
        if (pacienteExistente != null) {
            paciente = pacienteExistente;
        } else {
            paciente = new Paciente();
            paciente.setPessoaId(pessoa.getId());
            paciente.setDataCadastro(new Date());
            paciente.setDataActualizacao(new Date());
            // ✅ Novo campo
            paciente.setEmpresaId(1l);
            pacienteRepository.save(paciente);
        }

        // 🔹 4. Verifica se o produto (serviço de consulta) existe
        Produto produto = produtoRepository.findById(dto.getProdutoId()).orElse(null);
        if (produto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Serviço (produto) não encontrado com ID: " + dto.getProdutoId());
        }

        // 🔹 5. Cria nova agenda
        Agenda agenda = new Agenda();
        agenda.setDescricao(produto.getProductDescription()); // Usa o nome do produto como descrição
        agenda.setStatus(true);
        // ✅ Novo campo
        agenda.setEmpresaId(1l);
        agendaRepository.save(agenda);

        // 🔹 6. Cria a linha da agenda
        LinhaAgenda linha = new LinhaAgenda();
        linha.setAgendaId(agenda.getId());
        linha.setProdutoId(produto.getId());
        linha.setConsultaId(produto.getId());
        linha.setFuncionarioId(0L); // sem médico atribuído ainda
        linha.setPacienteId(paciente.getId());
        linha.setDataRealizacao(dto.getDataConsulta());
        linha.setConfirmacao(false);
        // ✅ Novo campo
        linha.setEmpresaId(1l);
        linhaAgendaRepository.save(linha);

        // 🔹 7. Retorna sucesso
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("✅ Agendamento criado com sucesso para o serviço: " + produto.getProductDescription());
    }

    public List<ConsultaAppDTO> listarTodasConsultas() {
        List<Produto> produtos = produtoRepository.findAllProdutosPorGrupoId(1);

        if (produtos == null || produtos.isEmpty()) {
            return Collections.emptyList();
        }

        return produtos.stream()
                .map(produto -> {
                    ConsultaAppDTO dto = new ConsultaAppDTO();
                    dto.setId(produto.getId());
                    dto.setDesignacao(produto.getProductDescription());
                    dto.setTipo(produto.getProductType());
                    return dto;
                })
                .collect(Collectors.toList());
    }


}
