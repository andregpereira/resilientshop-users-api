package com.github.andregpereira.resilientshop.userapi.cross.validations;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Pais;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.PaisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de validação de país.
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Transactional
public class PaisValidationImpl implements PaisValidation {

    /**
     * Injeção da dependência {@link PaisRepository} para realizar operações de
     * consulta e manutenção na tabela de países no banco de dados.
     */
    private final PaisRepository repository;

    /**
     * O {@linkplain Pais país} passado como argumento é validado. Se existir,
     * retorna o país do banco de dados, senão o país é criado.
     *
     * @param pais o país a ser validado.
     *
     * @return um {@code Pais} existente ou criado, caso não seja encontrado no banco de dados.
     */
    @Override
    public Pais validarPais(Pais pais) {
        log.info("Procurando país {}...", pais.getNome());
        return repository.findByNomeOrCodigo(pais.getNome(), pais.getCodigo()).map(p -> {
            log.info("Retornando país {}", p.getNome());
            return p;
        }).orElseGet(() -> {
            log.info("País {} não encontrado. Criando país...", pais.getNome());
            repository.save(pais);
            log.info("País criado com sucesso");
            return pais;
        });
    }

}
