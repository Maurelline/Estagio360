package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.CheckinEntity;
import br.com.uniesp.estagio360.domain.model.RodizioAlunoEntity;
import br.com.uniesp.estagio360.domain.ports.CheckinRepository;
import br.com.uniesp.estagio360.domain.ports.RodizioAlunoRepository;
import br.com.uniesp.estagio360.domain.request.CheckinRequest;
import br.com.uniesp.estagio360.domain.request.CheckoutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckinUseCase {

    private final CheckinRepository checkinRepository;
    private final RodizioAlunoRepository rodizioAlunoRepository;

    public CheckinEntity checkin(CheckinRequest request) {
        RodizioAlunoEntity rodizioAluno = rodizioAlunoRepository.findById(request.getRodizioAlunoId())
                .orElseThrow(() -> new NotFoundException("RodizioAluno", request.getRodizioAlunoId().toString()));

        CheckinEntity entity = new CheckinEntity();
        entity.setRodizioAluno(rodizioAluno);
        entity.setDataHoraCheckin(LocalDateTime.now());
        entity.setLatitude(request.getLatitude());
        entity.setLongitude(request.getLongitude());
        return checkinRepository.save(entity);
    }

    public CheckinEntity checkout(CheckoutRequest request) {
        CheckinEntity entity = checkinRepository.findById(request.getCheckinId())
                .orElseThrow(() -> new NotFoundException("Checkin", request.getCheckinId().toString()));
        entity.setDataHoraCheckout(LocalDateTime.now());
        return checkinRepository.save(entity);
    }
}