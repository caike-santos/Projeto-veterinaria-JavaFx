package com.veterinaria.utils;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionSystemException;

@Slf4j
public class Acoes {

    public static void executar(Runnable acao) {
        try {
            acao.run();
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação: {}", e.getMessage());
            Alertas.erro("Atenção", e.getMessage());
        } catch (ConstraintViolationException e) {
            log.warn("Violação de constraint: {}", e.getMessage());
            Alertas.mostrarViolacoes(e);
        } catch (TransactionSystemException e) {
            if (e.getRootCause() instanceof ConstraintViolationException cve) {
                log.warn("Violação de constraint no commit: {}", cve.getMessage());
                Alertas.mostrarViolacoes(cve);
            } else {
                log.error("Erro de transação:", e);
                Alertas.erro("Erro no banco de dados", "Não foi possível salvar os dados.");
            }
        } catch (Exception e) {
            log.error("Erro inesperado:", e);
            Alertas.erro("Erro inesperado", "Ocorreu um erro inesperado.");
        }
    }
}
