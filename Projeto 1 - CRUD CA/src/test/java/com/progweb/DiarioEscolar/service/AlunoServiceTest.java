package com.progweb.DiarioEscolar.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.progweb.DiarioEscolar.builder.aluno.AlunoBuilder;
import com.progweb.DiarioEscolar.domain.Aluno;
import com.progweb.DiarioEscolar.repositories.AlunoRepository;
import com.progweb.DiarioEscolar.services.AlunoService;
import com.progweb.DiarioEscolar.services.exceptions.ExistingObjectSameNameException;
import com.progweb.DiarioEscolar.services.exceptions.ObjectNotFoundException;

public class AlunoServiceTest {
    private static final long VALID_ALUNO_ID = 1L;
    private static final long INVALID_ALUNO_ID = 10L;

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class CreateTests {
        @Test
        void whenNewValidAlunoInformedThenItShouldBeCreated() throws ExistingObjectSameNameException {
            // given
            Aluno expectedSavedAluno = AlunoBuilder.builder().build().toAluno();

            // when
            when(alunoRepository.save(expectedSavedAluno)).thenReturn(expectedSavedAluno);

            // then
            Aluno createdAluno = alunoService.adicionarAluno(expectedSavedAluno);

            assertThat(createdAluno.getId(), is(equalTo(expectedSavedAluno.getId())));
            assertThat(createdAluno.getNome(), is(equalTo(expectedSavedAluno.getNome())));
        }

        @Test
        void whenAlreadyRegisteredAlunoInformedThenAnExceptionShouldBeThrown() {
            // given
            Aluno expectedAluno = AlunoBuilder.builder().build().toAluno();
            Aluno duplicatedAluno = expectedAluno;

            // when
            when(alunoRepository.findByNome(expectedAluno.getNome()))
                    .thenReturn(Optional.of(duplicatedAluno));

            // then
            assertThrows(ExistingObjectSameNameException.class, () -> alunoService.adicionarAluno(expectedAluno));
        }
    }

    @Nested
    class UpdateTests {
        @Test
        void whenValidAlunoIsGivenThenReturnAnUpdatedAluno() throws ObjectNotFoundException {
            // given
            Aluno expectedFoundAluno = AlunoBuilder.builder().build().toAluno();
            Aluno expectedUpdatedAluno = AlunoBuilder.builder().nome("Grapes").build().toAluno();

            // when
            when(alunoRepository.findById(VALID_ALUNO_ID)).thenReturn(Optional.of(expectedFoundAluno));
            when(alunoRepository.save(expectedUpdatedAluno)).thenReturn(expectedUpdatedAluno);

            // then
            Aluno updatedAluno = alunoService.atualizarAluno(VALID_ALUNO_ID, expectedUpdatedAluno);

            assertThat(updatedAluno, is(equalTo(expectedUpdatedAluno)));
        }

        @Test
        void whenInvalidAlunoIsGivenThenThrowAnException() {
            // given
            Aluno expectedFoundAluno = AlunoBuilder.builder().build().toAluno();

            // when
            when(alunoRepository.findById(INVALID_ALUNO_ID)).thenReturn(Optional.empty());

            // then
            assertThrows(ObjectNotFoundException.class, () -> alunoService.atualizarAluno(INVALID_ALUNO_ID, expectedFoundAluno));
        }
    }

    @Nested
    class DeleteTests {
        @Test
        void whenExclusionIsCalledWithValidIdThenAAlunoShouldBeDeleted() throws ObjectNotFoundException {
            // given
            Aluno expectedDeletedAluno = AlunoBuilder.builder().build().toAluno();

            // when
            when(alunoRepository.findById(expectedDeletedAluno.getId()))
                    .thenReturn(Optional.of(expectedDeletedAluno));
            doNothing().when(alunoRepository).deleteById(expectedDeletedAluno.getId());

            // then
            alunoService.deletarAluno(expectedDeletedAluno.getId());

            verify(alunoRepository, times(1)).findById(expectedDeletedAluno.getId());
            verify(alunoRepository, times(1)).delete(expectedDeletedAluno);
        }
    }

}
