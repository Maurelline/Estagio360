package br.com.uniesp.estagio360.domain.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entidade, String id) {
        super(entidade + " de ID " + id + " não foi encontrado.");

    }
}
