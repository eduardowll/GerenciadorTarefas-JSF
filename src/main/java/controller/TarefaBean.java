package controller;

import dao.ResponsavelDAO;
import dao.TarefaDAO;
import model.Responsavel;
import model.Tarefa;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class TarefaBean implements Serializable {

    private Tarefa tarefa = new Tarefa();
    private List<Responsavel> responsaveis;

    private TarefaDAO tarefaDAO = new TarefaDAO();
    private ResponsavelDAO responsavelDAO = new ResponsavelDAO();

    @PostConstruct
    public void init() {
        responsaveis = responsavelDAO.listarTodos();
    }

    public String salvar() {
        tarefaDAO.salvar(tarefa);
        tarefa = new Tarefa(); // limpa o formulário
        return "index.xhtml?faces-redirect=true"; // redireciona para listagem
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public List<Responsavel> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(List<Responsavel> responsaveis) {
        this.responsaveis = responsaveis;
    }
}

