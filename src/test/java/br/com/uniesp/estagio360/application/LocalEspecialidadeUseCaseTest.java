package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.EspecialidadeEntity;
import br.com.uniesp.estagio360.domain.model.LocalEspecialidadeEntity;
import br.com.uniesp.estagio360.domain.model.LocalParceiroEntity;
import br.com.uniesp.estagio360.domain.ports.EspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.LocalEspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.LocalParceiroRepository;
import br.com.uniesp.estagio360.domain.request.LocalEspecialidadeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocalEspecialidadeUseCaseTest {

    @Mock
    private LocalEspecialidadeRepository localEspecialidadeRepository;
    @Mock
    private LocalParceiroRepository localParceiroRepository;
    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @InjectMocks
    private LocalEspecialidadeUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criar_deveRetornarEntidadeSalva_quandoLocalEEspecialidadeExistem() {
        Long localParceiroId = 1L;
        Long especialidadeId = 2L;
        int quantidadeVagas = 5;

        LocalEspecialidadeRequest request = mock(LocalEspecialidadeRequest.class);
        when(request.getLocalParceiroId()).thenReturn(localParceiroId);
        when(request.getEspecialidadeId()).thenReturn(especialidadeId);
        when(request.getQuantidadeVagas()).thenReturn(quantidadeVagas);

        LocalParceiroEntity localParceiro = new LocalParceiroEntity();
        EspecialidadeEntity especialidade = new EspecialidadeEntity();
        LocalEspecialidadeEntity entityToSave = new LocalEspecialidadeEntity();
        entityToSave.setLocalParceiro(localParceiro);
        entityToSave.setEspecialidade(especialidade);
        entityToSave.setQuantidadeVagas(quantidadeVagas);

        LocalEspecialidadeEntity savedEntity = new LocalEspecialidadeEntity();
        when(localParceiroRepository.findById(localParceiroId)).thenReturn(Optional.of(localParceiro));
        when(especialidadeRepository.findById(especialidadeId)).thenReturn(Optional.of(especialidade));
        when(localEspecialidadeRepository.save(any(LocalEspecialidadeEntity.class))).thenReturn(savedEntity);

        LocalEspecialidadeEntity result = useCase.criar(request);

        assertEquals(savedEntity, result);
        verify(localParceiroRepository).findById(localParceiroId);
        verify(especialidadeRepository).findById(especialidadeId);
        verify(localEspecialidadeRepository).save(any(LocalEspecialidadeEntity.class));
    }

    @Test
    void criar_deveLancarNotFoundException_quandoLocalParceiroNaoExiste() {
        Long localParceiroId = 1L;
        LocalEspecialidadeRequest request = mock(LocalEspecialidadeRequest.class);
        when(request.getLocalParceiroId()).thenReturn(localParceiroId);

        when(localParceiroRepository.findById(localParceiroId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> useCase.criar(request));
        assertTrue(ex.getMessage().contains("LocalParceiro"));
        verify(localParceiroRepository).findById(localParceiroId);
        verifyNoMoreInteractions(especialidadeRepository, localEspecialidadeRepository);
    }

    @Test
    void criar_deveLancarNotFoundException_quandoEspecialidadeNaoExiste() {
        Long localParceiroId = 1L;
        Long especialidadeId = 2L;
        LocalEspecialidadeRequest request = mock(LocalEspecialidadeRequest.class);
        when(request.getLocalParceiroId()).thenReturn(localParceiroId);
        when(request.getEspecialidadeId()).thenReturn(especialidadeId);

        LocalParceiroEntity localParceiro = new LocalParceiroEntity();
        when(localParceiroRepository.findById(localParceiroId)).thenReturn(Optional.of(localParceiro));
        when(especialidadeRepository.findById(especialidadeId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> useCase.criar(request));
        assertTrue(ex.getMessage().contains("Especialidade"));
        verify(localParceiroRepository).findById(localParceiroId);
        verify(especialidadeRepository).findById(especialidadeId);
        verifyNoMoreInteractions(localEspecialidadeRepository);
    }

    @Test
    void listar_deveRetornarListaDeLocalEspecialidadeEntity() {
        List<LocalEspecialidadeEntity> lista = Arrays.asList(new LocalEspecialidadeEntity(), new LocalEspecialidadeEntity());
        when(localEspecialidadeRepository.findAll()).thenReturn(lista);

        List<LocalEspecialidadeEntity> result = useCase.listar();

        assertEquals(lista, result);
        verify(localEspecialidadeRepository).findAll();
    }
}