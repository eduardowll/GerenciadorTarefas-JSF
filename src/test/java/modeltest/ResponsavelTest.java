package modeltest;

import model.Responsavel;
import model.Tarefa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ResponsavelTest {

    @Test
    public void AtribuirCamposCorretamente() {
        Responsavel responsavel = new Responsavel();
        responsavel.setNome("Amanda");

        Assertions.assertEquals("Amanda", responsavel.getNome());
    }

    @Test
    public void AssociarTarefas() {
        Responsavel responsavel = new Responsavel("Ana");

        Tarefa tarefa1 = new Tarefa();
        tarefa1.setTitulo("Estudar");
        tarefa1.setResponsavel(responsavel);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setTitulo("Revisar");
        tarefa2.setResponsavel(responsavel);

        List<Tarefa> tarefas = new ArrayList<>();
        tarefas.add(tarefa1);
        tarefas.add(tarefa2);
        responsavel.setTarefas(tarefas);

        Assertions.assertEquals(2, responsavel.getTarefas().size());
        Assertions.assertEquals("Estudar", responsavel.getTarefas().get(0).getTitulo());
        Assertions.assertEquals("Ana", responsavel.getTarefas().get(0).getResponsavel().getNome());
    }
}

