package com.github.andregpereira.resilientshop.userapi.cross.validations;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Pais;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.PaisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class PaisValidationImpl implements PaisValidation {

    private final PaisRepository repository;

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
