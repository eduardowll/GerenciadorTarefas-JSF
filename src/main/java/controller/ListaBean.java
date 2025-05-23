package controller;

import dao.TarefaDAO;
import model.Tarefa;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class ListaBean implements Serializable {

    private List<Tarefa> tarefas;
    private TarefaDAO tarefaDAO = new TarefaDAO();

    @PostConstruct
    public void init() {
        tarefas = tarefaDAO.listarTodas();
    }

    public void excluir(Long id) {
        tarefaDAO.remover(id);
        tarefas = tarefaDAO.listarTodas();
    }

    public String editar(Long id) {
        return "cadastro.xhtml?faces-redirect=true&taskId=" + id;
    }

    public void concluir(Long id) {
        Tarefa tarefa = tarefaDAO.buscarPorId(id);
        if (tarefa != null && !"Concluído".equals(tarefa.getStatus())) {
            tarefa.setStatus("Concluído");
            tarefaDAO.salvar(tarefa); // usa o merge
            tarefas = tarefaDAO.listarTodas(); // atualiza a lista
        }
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
}
