package com.progweb.DiarioEscolar.controller;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progweb.DiarioEscolar.builder.aluno.AlunoBuilder;
import com.progweb.DiarioEscolar.builder.aluno.AlunoDTOBuilder;
import com.progweb.DiarioEscolar.domain.Aluno;
import com.progweb.DiarioEscolar.domain.dtos.AlunoDTO;
import com.progweb.DiarioEscolar.mappers.AlunoMapper;
import com.progweb.DiarioEscolar.services.AlunoService;
import com.progweb.DiarioEscolar.services.exceptions.ExistingObjectSameNameException;
import com.progweb.DiarioEscolar.services.exceptions.ObjectNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AlunoControllerTest {
    private static final String ALUNO_API_URL_PATH = "/alunos";
    private static final Long VALID_ALUNO_ID = 1L;
    private static final Long INVALID_ALUNO_ID = 10L;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private AlunoService alunoService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AlunoMapper alunoMapper;
    @InjectMocks
    private AlunoController alunoController;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(alunoController).build();
    }
    @Nested
    class PostMethodsTest {
        @Test
        @DisplayName("Call with Valid Fields")
        void whenPOSTIsCalledWithValidFieldsThenShouldObjectCreated() throws Exception {
            String createAlunoRequest = objectMapper.writeValueAsString(
                    objectMapper.readValue(
                            new File("src/test/resources/Aluno/createAlunoValidRequest.json"),
                            AlunoDTO.class));
            AlunoDTO expectedAlunoDTO = AlunoDTOBuilder.builder().build().toAlunoDTO();
            Aluno expectedAluno = AlunoBuilder.builder().build().toAluno();
            when(alunoMapper.convertFromAlunoDTO(eq(expectedAlunoDTO))).thenReturn(expectedAluno);
            when(alunoService.adicionarAluno(eq(expectedAluno))).thenReturn(expectedAluno);

            mockMvc.perform(post(ALUNO_API_URL_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(String.valueOf(createAlunoRequest)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.nome", is(expectedAluno.getNome())))
                    .andExpect(jsonPath("$.matricula", is(expectedAluno.getMatricula())))
                    .andExpect(jsonPath("$.email", is(expectedAluno.getEmail())))
                    .andExpect(jsonPath("$.senha", is(expectedAluno.getSenha())));
        }

        @Test
        @DisplayName("Call Twice")
        void whenPOSTIsCalledWithValidFieldsTwiceThenBadRequestStatusIsReturned() throws Exception {
            String createAlunoRequest = objectMapper.writeValueAsString(
                    objectMapper.readValue(
                            new File("src/test/resources/Aluno/createAlunoValidRequest.json"),
                            AlunoDTO.class));
            AlunoDTO expectedAlunoDTO = AlunoDTOBuilder.builder().build().toAlunoDTO();
            Aluno expectedAluno = AlunoBuilder.builder().build().toAluno();
            when(alunoMapper.convertFromAlunoDTO(eq(expectedAlunoDTO))).thenReturn(expectedAluno);
            when(alunoService.adicionarAluno(eq(expectedAluno))).thenReturn(expectedAluno);
            doThrow(ExistingObjectSameNameException.class).when(alunoService).adicionarAluno(eq(expectedAluno));
           
            mockMvc.perform(post(ALUNO_API_URL_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(String.valueOf(createAlunoRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Call with Invalid Fields")
        void whenPOSTIsCalledWithInvalidFieldsThenBadRequestStatusIsReturned() throws Exception {
            String createAlunoRequest = objectMapper.writeValueAsString(
                    objectMapper.readValue(
                            new File("src/test/resources/aluno/createAlunoInvalidRequest.json"),
                            AlunoDTO.class));

            mockMvc.perform(post(ALUNO_API_URL_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(String.valueOf(createAlunoRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class PutMethodsTest {

        @Test
        @DisplayName("Call with Valid Fields")
        void whenPUTIsCalledWithValidDataThenOkStatusIsReturned() throws Exception {
            String updateAlunoValidRequest = objectMapper.writeValueAsString(
                    objectMapper.readValue(
                            new File("src/test/resources/aluno/updateAlunoValidRequest.json"),
                            AlunoDTO.class));

            AlunoDTO alunoDTO = AlunoDTOBuilder.builder().nome("Ruan").matricula("192921").email("ruan@gmail.com").senha("213").build().toAlunoDTO();
            Aluno aluno = AlunoBuilder.builder().nome("Ruan").matricula("192921").email("ruan@gmail.com").senha("213").build().toAluno();
            when(alunoMapper.convertFromAlunoDTO(eq(alunoDTO))).thenReturn(aluno);
            when(alunoService.atualizarAluno(eq(VALID_ALUNO_ID), eq(aluno))).thenReturn(aluno);

            mockMvc.perform(put(ALUNO_API_URL_PATH + "/" + VALID_ALUNO_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(String.valueOf(updateAlunoValidRequest)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.nome", is(aluno.getNome())))
                    .andExpect(jsonPath("$.matricula", is(aluno.getMatricula())))
                    .andExpect(jsonPath("$.email", is(aluno.getEmail())))
                    .andExpect(jsonPath("$.senha", is(aluno.getSenha())));
        }

        @Test
        @DisplayName("Call with Invalid Fields")
        void whenPUTIsCalledWithInvalidDataThenBadRequestStatusIsReturned() throws Exception {
            String updateAlunoValidRequest = objectMapper.writeValueAsString(
                    objectMapper.readValue(new File("src/test/resources/aluno/updateAlunoInvalidRequest.json"),AlunoDTO.class));

            mockMvc.perform(put(ALUNO_API_URL_PATH + "/" + VALID_ALUNO_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(String.valueOf(updateAlunoValidRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
        @Test
        @DisplayName("Call with Nonexistent Id")
        void whenPUTIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {

            String updateAlunoValidRequest = objectMapper.writeValueAsString(
                    objectMapper.readValue(
                            new File("src/test/resources/aluno/updateAlunoValidRequest.json"),
                            AlunoDTO.class));
            AlunoDTO alunoDTO = AlunoDTOBuilder.builder().nome("Ruan").matricula("192921").email("ruan@gmail.com").senha("213").build().toAlunoDTO();
            Aluno aluno = AlunoBuilder.builder().nome("Ruan").matricula("192921").email("ruan@gmail.com").senha("213").build().toAluno();

            when(alunoMapper.convertFromAlunoDTO(eq(alunoDTO))).thenReturn(aluno);
            doThrow(ObjectNotFoundException.class).when(alunoService).atualizarAluno(INVALID_ALUNO_ID, aluno);

            mockMvc.perform(put(ALUNO_API_URL_PATH + "/" + INVALID_ALUNO_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(String.valueOf(updateAlunoValidRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }
    @Nested
    class DeleteMethodsTest {
        @Test
        @DisplayName("Call with Valid Id")
        void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
            doNothing().when(alunoService).deletarAluno(VALID_ALUNO_ID);

            mockMvc.perform(delete(ALUNO_API_URL_PATH + "/" + VALID_ALUNO_ID)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Call with Invalid Id")
        void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
            doThrow(ObjectNotFoundException.class).when(alunoService).deletarAluno(INVALID_ALUNO_ID);

            mockMvc.perform(delete(ALUNO_API_URL_PATH + "/" + INVALID_ALUNO_ID)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }
}
