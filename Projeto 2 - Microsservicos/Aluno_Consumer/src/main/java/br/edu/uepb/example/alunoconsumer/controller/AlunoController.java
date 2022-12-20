package br.edu.uepb.example.alunoconsumer.controller;

import java.util.List;
import java.util.Optional;

import br.edu.uepb.example.alunoconsumer.repository.AlunoRepository;
import br.edu.uepb.example.alunoconsumer.domain.Aluno;
import br.edu.uepb.example.alunoconsumer.dto.EmailDTO;

import org.ietf.jgss.MessageProp;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping(value = "/alunos")
public class AlunoController {
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.exchange}")
    public String EXCHANGE_NAME;

    @GetMapping
    public List<Aluno> getAlunos() {
        return alunoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Aluno> getAlunoById(@PathVariable Long id) {
        return alunoRepository.findById(id);
    }

    @PostMapping(consumes = {"*/*"})
    public Aluno createAluno(@RequestBody Aluno aluno) {

        try {
            EmailDTO emailDTO = new EmailDTO("luanfellipe@gmail.com", "Criado", "Aluno foi criado com sucesso");
            String json = new ObjectMapper().writeValueAsString(emailDTO);
            Message message = MessageBuilder.withBody(json.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        return alunoRepository.save(aluno);
    }
    
    @PutMapping("/{id}")
    public Aluno updateAluno(@PathVariable("id") Long id, @RequestBody Aluno aluno) {

        try {
            EmailDTO emailDTO = new EmailDTO("luanfellipe@gmail.com", "Editado", "O aluno foi editado com sucesso");
             String json = new ObjectMapper().writeValueAsString(emailDTO);
             Message message = MessageBuilder.withBody(json.getBytes())
                     .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                     .build();
             rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", message);
         } catch (JsonProcessingException e) {
             e.printStackTrace();
         }
 
         aluno.setId(id);
         return alunoRepository.save(aluno);
    }
    
    
    @DeleteMapping("/{id}")
    public void deleteAluno(@PathVariable Long id) {
        alunoRepository.delete(alunoRepository.findById(id).get());
    }

    
}
