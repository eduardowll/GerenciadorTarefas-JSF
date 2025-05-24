package modeltest;

import model.Responsavel;
import model.Tarefa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TarefaTest {

    @Test
    public void AtribuireAcessarCampos() {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Estudar");
        tarefa.setDescricao("Estudar Java");
        tarefa.setPrioridade("Alta");
        tarefa.setStatus("Em andamento");

        Date deadline = new Date();
        tarefa.setDeadline(deadline);

        Assertions.assertEquals("Estudar", tarefa.getTitulo());
        Assertions.assertEquals("Estudar Java e JSF para entrar na ESIG", tarefa.getDescricao());
        Assertions.assertEquals("Alta", tarefa.getPrioridade());
        Assertions.assertEquals("Em andamento", tarefa.getStatus());
        Assertions.assertEquals(deadline, tarefa.getDeadline());
    }

    @Test
    public void CriarTarefaStatusPadrao() {
        Tarefa tarefa = new Tarefa();
        Assertions.assertEquals("Em andamento", tarefa.getStatus());
    }

    @Test
    public void AssociarResponsavel() {
        Tarefa tarefa = new Tarefa();
        Responsavel responsavel = new Responsavel("Eduardo");

        tarefa.setResponsavel(responsavel);

        Assertions.assertEquals("Eduardo", tarefa.getResponsavel().getNome());
    }
}
