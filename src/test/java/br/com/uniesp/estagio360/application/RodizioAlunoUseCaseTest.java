package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.RodizioAlunoEntity;
import br.com.uniesp.estagio360.domain.model.RodizioEntity;
import br.com.uniesp.estagio360.domain.model.UsuarioEntity;
import br.com.uniesp.estagio360.domain.ports.RodizioAlunoRepository;
import br.com.uniesp.estagio360.domain.ports.RodizioRepository;
import br.com.uniesp.estagio360.domain.ports.UsuarioRepository;
import br.com.uniesp.estagio360.domain.request.RodizioAlocarAlunosRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RodizioAlunoUseCaseTest {

    @Mock
    private RodizioAlunoRepository rodizioAlunoRepository;
    @Mock
    private RodizioRepository rodizioRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private RodizioAlunoUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAllocateAlunosSuccessfully() {
        Long rodizioId = 1L;
        Long alunoId1 = 10L;
        Long alunoId2 = 20L;
        RodizioEntity rodizio = new RodizioEntity();
        rodizio.setId(rodizioId);
        rodizio.setVagas(2);

        RodizioAlocarAlunosRequest request = new RodizioAlocarAlunosRequest();
        request.setRodizioId(rodizioId);
        request.setAlunosIds(Arrays.asList(alunoId1, alunoId2));

        when(rodizioRepository.findById(rodizioId)).thenReturn(Optional.of(rodizio));
        when(rodizioAlunoRepository.countByRodizioId(rodizioId)).thenReturn(0L);
        when(rodizioAlunoRepository.existsByRodizioIdAndAlunoId(rodizioId, alunoId1)).thenReturn(false);
        when(rodizioAlunoRepository.existsByRodizioIdAndAlunoId(rodizioId, alunoId2)).thenReturn(false);

        UsuarioEntity aluno1 = new UsuarioEntity();
        aluno1.setId(alunoId1);
        UsuarioEntity aluno2 = new UsuarioEntity();
        aluno2.setId(alunoId2);

        when(usuarioRepository.findById(alunoId1)).thenReturn(Optional.of(aluno1));
        when(usuarioRepository.findById(alunoId2)).thenReturn(Optional.of(aluno2));

        when(rodizioAlunoRepository.save(any(RodizioAlunoEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        List<RodizioAlunoEntity> result = useCase.alocarAlunos(request);

        assertEquals(2, result.size());
        assertEquals(aluno1, result.get(0).getAluno());
        assertEquals(aluno2, result.get(1).getAluno());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenRodizioNotFound() {
        Long rodizioId = 1L;
        RodizioAlocarAlunosRequest request = new RodizioAlocarAlunosRequest();
        request.setRodizioId(rodizioId);
        request.setAlunosIds(Collections.singletonList(10L));

        when(rodizioRepository.findById(rodizioId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.alocarAlunos(request));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenAlunoNotFound() {
        Long rodizioId = 1L;
        Long alunoId = 10L;
        RodizioEntity rodizio = new RodizioEntity();
        rodizio.setId(rodizioId);
        rodizio.setVagas(1);

        RodizioAlocarAlunosRequest request = new RodizioAlocarAlunosRequest();
        request.setRodizioId(rodizioId);
        request.setAlunosIds(Collections.singletonList(alunoId));

        when(rodizioRepository.findById(rodizioId)).thenReturn(Optional.of(rodizio));
        when(rodizioAlunoRepository.countByRodizioId(rodizioId)).thenReturn(0L);
        when(rodizioAlunoRepository.existsByRodizioIdAndAlunoId(rodizioId, alunoId)).thenReturn(false);
        when(usuarioRepository.findById(alunoId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.alocarAlunos(request));
    }

    @Test
    void shouldNotAllocateAlreadyAllocatedAluno() {
        Long rodizioId = 1L;
        Long alunoId = 10L;
        RodizioEntity rodizio = new RodizioEntity();
        rodizio.setId(rodizioId);
        rodizio.setVagas(1);

        RodizioAlocarAlunosRequest request = new RodizioAlocarAlunosRequest();
        request.setRodizioId(rodizioId);
        request.setAlunosIds(Collections.singletonList(alunoId));

        when(rodizioRepository.findById(rodizioId)).thenReturn(Optional.of(rodizio));
        when(rodizioAlunoRepository.countByRodizioId(rodizioId)).thenReturn(0L);
        when(rodizioAlunoRepository.existsByRodizioIdAndAlunoId(rodizioId, alunoId)).thenReturn(true);

        List<RodizioAlunoEntity> result = useCase.alocarAlunos(request);

        assertTrue(result.isEmpty());
        verify(usuarioRepository, never()).findById(any());
        verify(rodizioAlunoRepository, never()).save(any());
    }

    @Test
    void shouldNotAllocateWhenNoVagasDisponiveis() {
        Long rodizioId = 1L;
        Long alunoId = 10L;
        RodizioEntity rodizio = new RodizioEntity();
        rodizio.setId(rodizioId);
        rodizio.setVagas(0);

        RodizioAlocarAlunosRequest request = new RodizioAlocarAlunosRequest();
        request.setRodizioId(rodizioId);
        request.setAlunosIds(Collections.singletonList(alunoId));

        when(rodizioRepository.findById(rodizioId)).thenReturn(Optional.of(rodizio));
        when(rodizioAlunoRepository.countByRodizioId(rodizioId)).thenReturn(0L);

        List<RodizioAlunoEntity> result = useCase.alocarAlunos(request);

        assertTrue(result.isEmpty());
        verify(rodizioAlunoRepository, never()).existsByRodizioIdAndAlunoId(anyLong(), anyLong());
        verify(usuarioRepository, never()).findById(any());
        verify(rodizioAlunoRepository, never()).save(any());
    }
}