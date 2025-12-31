package com.dvml.api.service;

import com.dvml.api.dto.ProdutoArvoreDTO;
import com.dvml.api.dto.ProdutoDTO;
import com.dvml.api.entity.Produto;
import com.dvml.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    // Pasta onde as imagens serão salvas (ajuste se quiser outro caminho)
    private static final Path UPLOAD_DIR = Paths.get("uploads", "produtos");

    // ------------ MÉTODOS AUXILIARES ------------

    private String salvarImagem(MultipartFile imagem) throws IOException {
        if (imagem == null || imagem.isEmpty()) {
            return null;
        }

        // Garante que a pasta exista
        Files.createDirectories(UPLOAD_DIR);

        String nomeOriginal = imagem.getOriginalFilename();
        String extensao = "";
        if (nomeOriginal != null && nomeOriginal.contains(".")) {
            extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
        }

        String nomeArquivo = UUID.randomUUID() + extensao;
        Path destino = UPLOAD_DIR.resolve(nomeArquivo);

        Files.copy(imagem.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        // No banco guardamos só o nome do ficheiro
        return nomeArquivo;
    }

    // ------------ LISTAGEM / CONSULTA ------------

    public List<ProdutoDTO> listarTodosProdutos() {
        return repo.findAllOrderByNomeAsc()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public ProdutoDTO convertEntityToDto(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setProductType(produto.getProductType());
        produtoDTO.setProductGroup(produto.getProductGroup());
        produtoDTO.setProductCode(produto.getProductCode());
        produtoDTO.setTaxIva(produto.getTaxIva());
        produtoDTO.setFinalPrice(produto.getFinalPrice());
        produtoDTO.setProductDescription(produto.getProductDescription());
        produtoDTO.setUnidadeMedidaId(produto.getUnidadeMedidaId());
        produtoDTO.setImagem(produto.getImagem());
        produtoDTO.setUnidadeMedida(produto.getUnidadeMedida());
        produtoDTO.setStatus(produto.getStatus());
        produtoDTO.setProdutoPaiId(produto.getProdutoPaiId());
        produtoDTO.setProductGroupId(produto.getProductGroupId());
        produtoDTO.setProductTypeId(produto.getProductTypeId());
        produtoDTO.setIntervaloReferencia(produto.getIntervaloReferencia());
        produtoDTO.setEmpresaId(produto.getEmpresaId());
        return produtoDTO;
    }

    public Produto getProdutoById(long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    // ------------ CRIAR PRODUTO ------------

    // Versão antiga (somente JSON, sem imagem) – mantida por compatibilidade
    public ResponseEntity<String> criar(ProdutoDTO produtoDTO) {
        return criar(produtoDTO, null);
    }

    // Nova versão: criar produto com imagem (usada pelo controller multipart)
    public ResponseEntity<String> criar(ProdutoDTO produtoDTO, MultipartFile imagem) {
        // Log para depuração
        System.out.println("productTypeId recebido no ProdutoDTO (criar): " + produtoDTO.getProductTypeId());

        Optional<Produto> produtoExistente = repo.findByProductDescription(produtoDTO.getProductDescription());
        if (produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um produto com a descrição: " + produtoDTO.getProductDescription());
        }

        Produto produto = new Produto();
        produto.setProductType(produtoDTO.getProductType());
        produto.setProductCode(produtoDTO.getProductCode());
        produto.setProductGroup(produtoDTO.getProductGroup());
        produto.setProductDescription(produtoDTO.getProductDescription());
        produto.setUnidadeMedida(produtoDTO.getUnidadeMedida());
        produto.setPreco(produtoDTO.getPreco());
        produto.setTaxIva(produtoDTO.getTaxIva());
        produto.setFinalPrice(produtoDTO.getFinalPrice());
        produto.setProductGroupId(produtoDTO.getProductGroupId());
        produto.setProductTypeId(produtoDTO.getProductTypeId());
        produto.setUnidadeMedidaId(produtoDTO.getUnidadeMedidaId());
        produto.setStatus(produtoDTO.isStatus());
        produto.setProdutoPaiId(produtoDTO.getProdutoPaiId());
        produto.setIntervaloReferencia(produtoDTO.getIntervaloReferencia());
        produto.setEmpresaId(produtoDTO.getEmpresaId());

        // Trata imagem (se enviada)
        if (imagem != null && !imagem.isEmpty()) {
            try {
                String nomeArquivo = salvarImagem(imagem);
                produto.setImagem(nomeArquivo);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Falha ao salvar a imagem do produto: " + e.getMessage());
            }
        } else {
            // Se não veio arquivo, mantém o que veio do DTO (se vier base64 ou nome)
            produto.setImagem(produtoDTO.getImagem());
        }

        // Log para verificar o valor antes de salvar
        System.out.println("productTypeId na entidade Produto antes de salvar: " + produto.getProductTypeId());

        Produto salvo = repo.save(produto);
        if (Objects.nonNull(salvo)) {
            // Log para verificar o valor salvo
            System.out.println("productTypeId salvo no banco: " + salvo.getProductTypeId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Produto criado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar o produto.");
    }

    // ------------ EDITAR PRODUTO ------------

    // Versão antiga (somente JSON, sem imagem) – mantida por compatibilidade
    public ResponseEntity<String> update(long id, ProdutoDTO produtoDTO) {
        return update(id, produtoDTO, null);
    }

    // Nova versão: editar produto com imagem (usada pelo controller multipart)
    public ResponseEntity<String> update(long id, ProdutoDTO produtoDTO, MultipartFile imagem) {
        // Log para depuração
        System.out.println("productTypeId recebido no ProdutoDTO (update): " + produtoDTO.getProductTypeId());

        Optional<Produto> produtoExistente = repo.findById(id);
        if (!produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produto não encontrado com ID: " + id);
        }

        Produto produto = produtoExistente.get();
        produto.setProductType(produtoDTO.getProductType());
        produto.setProductCode(produtoDTO.getProductCode());
        produto.setProductGroup(produtoDTO.getProductGroup());
        produto.setProductDescription(produtoDTO.getProductDescription());
        produto.setUnidadeMedida(produtoDTO.getUnidadeMedida());
        produto.setPreco(produtoDTO.getPreco());
        produto.setTaxIva(produtoDTO.getTaxIva());
        produto.setFinalPrice(produtoDTO.getFinalPrice());
        produto.setProductGroupId(produtoDTO.getProductGroupId());
        produto.setProductTypeId(produtoDTO.getProductTypeId());
        produto.setUnidadeMedidaId(produtoDTO.getUnidadeMedidaId());
        produto.setStatus(produtoDTO.isStatus());
        produto.setProdutoPaiId(produtoDTO.getProdutoPaiId());
        produto.setIntervaloReferencia(produtoDTO.getIntervaloReferencia());
        if (produtoDTO.getEmpresaId() != null) {
            produto.setEmpresaId(produtoDTO.getEmpresaId());
        }
// se for null, mantém o que já estava no produto

        // Trata imagem (se enviada)
        if (imagem != null && !imagem.isEmpty()) {
            try {
                String nomeArquivo = salvarImagem(imagem);
                produto.setImagem(nomeArquivo);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Falha ao salvar a imagem do produto: " + e.getMessage());
            }
        } else {
            // Se não veio imagem nova, mantém a que já estava
            produto.setImagem(produto.getImagem());
        }

        // Log para verificar o valor antes de salvar
        System.out.println("productTypeId na entidade Produto antes de salvar (update): " + produto.getProductTypeId());

        Produto salvo = repo.save(produto);
        if (Objects.nonNull(salvo)) {
            // Log para verificar o valor salvo
            System.out.println("productTypeId salvo no banco (update): " + salvo.getProductTypeId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto editado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar o produto.");
    }

    // ------------ OUTROS MÉTODOS (SEM MUDANÇA) ------------

    public ResponseEntity<String> deleteProduct(long id, boolean status) {
        Optional<Produto> produtoExistente = repo.findById(id);
        if (!produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produto não encontrado com ID: " + id);
        }
        Produto produtoToUpdate = produtoExistente.get();
        produtoToUpdate.setStatus(status);
        if (Objects.nonNull(repo.save(produtoToUpdate))) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto desativo com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao deletar o produto.");
    }

    public ResponseEntity<String> activateProduct(long id) {
        Optional<Produto> produtoExistente = repo.findById(id);
        if (!produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produto não encontrado com ID: " + id);
        }
        Produto produto = produtoExistente.get();

        if (produto.getStatus()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Produto já está ativo.");
        }

        System.out.println("Ativando produto com ID: " + id);

        produto.setStatus(true);
        Produto salvo = repo.save(produto);

        if (Objects.nonNull(salvo)) {
            System.out.println("Produto com ID " + id + " ativado com sucesso.");
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto ativado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao ativar o produto.");
    }

    public ResponseEntity<String> deactivateProduct(long id) {
        Optional<Produto> produtoExistente = repo.findById(id);
        if (!produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produto não encontrado com ID: " + id);
        }
        Produto produto = produtoExistente.get();

        if (!produto.getStatus()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Produto já está desativado.");
        }

        System.out.println("Desativando produto com ID: " + id);

        produto.setStatus(false);
        Produto salvo = repo.save(produto);

        if (Objects.nonNull(salvo)) {
            System.out.println("Produto com ID " + id + " desativado com sucesso.");
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto desativado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao desativar o produto.");
    }

    public ResponseEntity<String> deletePermanently(long id) {
        Optional<Produto> produtoExistente = repo.findById(id);
        if (!produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produto não encontrado com ID: " + id);
        }

        List<Produto> filhos = repo.findByProdutoPaiId(id);
        if (!filhos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Não é possível excluir o produto porque ele possui produtos filhos associados.");
        }

        try {
            repo.deleteById(id);
            System.out.println("Produto com ID " + id + " excluído permanentemente com sucesso.");
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto excluído permanentemente com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao excluir produto com ID " + id + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao excluir o produto: " + e.getMessage());
        }
    }

    public List<Produto> listarProdutosPorGrupo(long grupoId) {
        return repo.findAllProdutosPorGrupoId(grupoId);
    }

    public List<Produto> listarProdutosPorTipo(Long tipoId) {
        return repo.findAllByProductTypeId(tipoId);
    }

    public ProdutoArvoreDTO montarArvoreProduto(Long produtoId) {
        Produto produto = repo.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ProdutoArvoreDTO dto = new ProdutoArvoreDTO();
        dto.setId(produto.getId());
        dto.setProductCode(produto.getProductCode());
        dto.setProductDescription(produto.getProductDescription());

        List<Produto> filhos = repo.findByProdutoPaiId(produtoId);
        List<ProdutoArvoreDTO> filhosDto = filhos.stream()
                .map(filho -> montarArvoreProduto(filho.getId()))
                .collect(Collectors.toList());

        dto.setFilhos(filhosDto);
        return dto;
    }
}