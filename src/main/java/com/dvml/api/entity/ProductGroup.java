package com.dvml.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_group")
@Setter
@Getter
@NoArgsConstructor
public class ProductGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "designacao_produto", length = 50, unique = true)
    @NotNull(message = "O grupo do produto é obrigatório.")
    @Size(min = 1, max = 50, message = "A designação do produto deve ter entre 3 e 50 caracteres.")
    private String designacaoProduto;
}