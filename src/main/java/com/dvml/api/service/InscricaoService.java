package com.dvml.api.service;

import com.dvml.api.dto.InscricaoDTO;
import com.dvml.api.dto.InscricaoFullDTO;
import com.dvml.api.dto.PacienteDTO;
import com.dvml.api.entity.Inscricao;
import com.dvml.api.entity.Paciente;
import com.dvml.api.repository.InscricaoRepository;
import com.dvml.api.repository.PacienteRepository;
import com.dvml.api.util.CondicaoInscricao;
import com.dvml.api.util.Constantes;
import com.dvml.api.util.EncaminhamentoInscricao;
import com.dvml.api.util.EstadoInscricao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository repo;

    @Autowired
    private PacienteRepository repoPaciente;

    @Autowired
    private PacienteService pacienteService;





    public InscricaoFullDTO convertEntityToDto2(Inscricao inscricao) {
         PacienteDTO pacienteDTO =  pacienteService.getPacienteById( inscricao.getPacienteId());

        /**
         * id
         * cor_triagem_manchester
         * data_actualizacao
         * data_criacao
         * encaminhamento
         * estado
         * minuto_espera_triagem_manchester
         * obs_triagem_manchester
         * paciente_id
         */

        InscricaoFullDTO inscricaoDTO = new InscricaoFullDTO();
        inscricaoDTO.setInscricaoId(inscricao.getId());
        inscricaoDTO.setCorTriagemManchester("");
        inscricaoDTO.setDataCriacao(inscricao.getDataCriacao());
        inscricaoDTO.setEstado(inscricao.getEstadoInscricao());
        inscricaoDTO.setEncaminhamento(inscricao.getEncaminhamento().toString());
        inscricaoDTO.setDataActualizacao(inscricao.getDataActualizacao());
        inscricaoDTO.setNome(pacienteDTO.getNome());
        inscricaoDTO.setApelido(pacienteDTO.getApelido());
        inscricaoDTO.setNomeCompleto(inscricaoDTO.getNome() + " " +pacienteDTO.getApelido());
        inscricaoDTO.setPacienteId(inscricao.getPacienteId());
        inscricaoDTO.setCondicaoInscricao(
                inscricao.getCondicaoInscricao().equals(CondicaoInscricao.ABERTO) ? "ABERTO" :
                        (inscricao.getCondicaoInscricao().equals(CondicaoInscricao.FECHADO) ? "FECHADO" : "CANCELADO")
        );
        return inscricaoDTO;
    }





    public InscricaoDTO convertEntityToDto1(Inscricao inscricao) {
        InscricaoDTO inscricaoDTO = new InscricaoDTO();
        inscricaoDTO.setInscricaoId(inscricao.getId());
        inscricaoDTO.setDataCriacao(inscricao.getDataCriacao());
        inscricaoDTO.setDataActualizacao(inscricao.getDataActualizacao());
        inscricaoDTO.setPaciente(pacienteService.convertEntityToDto( repoPaciente.getReferenceById(inscricao.getPacienteId())   ));
        return inscricaoDTO;
    }


    public List<InscricaoDTO> listarTodos() {
        return repo.findAll()
                .stream()
                .map(this::convertEntityToDto1)
                .collect(Collectors.toList());
    }

    public List<InscricaoFullDTO> listarTodosFull() {
        return repo.findAll()
                .stream()
                .map(this::convertEntityToDto2)
                .collect(Collectors.toList());
    }

    public List<InscricaoFullDTO> listarTodosFullByEstado(String estado) {
        System.out.println("Estado: " + estado);


        return repo.getAllInscricaoNaoTriados()
                .stream()
                .map(this::convertEntityToDto2)
                .collect(Collectors.toList());


    }


    public List<InscricaoFullDTO> listarInscricaoNaoTriados() {
        return repo.getAllInscricaoNaoTriados()
                .stream()
                .map(this::convertEntityToDto2)
                .collect(Collectors.toList());
    }


    public List<InscricaoFullDTO> listarInscricaoByEncameninhamentoConsulta() {
        return repo.getAllInscricaoEncaminhadoConsulta()
                .stream()
                .map(this::convertEntityToDto2)
                .collect(Collectors.toList());
    }

    public Inscricao salvar(Inscricao inscricao) {
        return repo.save(inscricao);
    }

    public void update(long id, String estado, String encaminhamento) {
        Inscricao inscricao = repo.findById(id).get();
        inscricao.setEstadoInscricao((estado.equals("TRIADO")) ? EstadoInscricao.TRIADO : EstadoInscricao.NAO_TRIADO);

      System.out.println("ENCAMINHAMENTO: " +getEncaminhamento(encaminhamento));
        inscricao.setEncaminhamento(getEncaminhamento(encaminhamento));
        repo.save(inscricao);
    }

    public ResponseEntity updateEstadoTriagemManchester(long id, String cor, long minuto) {

        Inscricao inscricao = repo.findById(id).get();
        inscricao.setCorTriagemManchester(cor);
        inscricao.setMinutoEsperaTriagemManchester(minuto);
        inscricao.setObsTriagemManchester(Constantes.getObsTriagemManchester(cor));
        try {
            repo.save(inscricao);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public InscricaoFullDTO getInscricaoById(long id){
       Inscricao inscricao = repo.findById(id).get();
       return convertEntityToDto2(inscricao);

    }

    private EncaminhamentoInscricao getEncaminhamento(String value) {
        if (value.equals("CONSULTORIO")) return EncaminhamentoInscricao.CONSULTORIO;
        else if (value.equals("SO")) return EncaminhamentoInscricao.SO;
        else if (value.equals("CADEIRA")) return EncaminhamentoInscricao.CADEIRA;
        else return EncaminhamentoInscricao.EM_ESPERA;
    }

}
