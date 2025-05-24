package controller;

import dao.ResponsavelDAO;
import dao.TarefaDAO;
import model.Responsavel;
import model.Tarefa;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "cadastroBean")
@ViewScoped
public class CadastroBean implements Serializable {

    private Tarefa tarefa;
    private List<Responsavel> responsaveis;
    private Long responsavelId;

    private TarefaDAO tarefaDAO = new TarefaDAO();
    private ResponsavelDAO responsavelDAO = new ResponsavelDAO();

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        String idParam = params.get("taskId");

        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                tarefa = tarefaDAO.buscarPorId(id);
                if (tarefa.getResponsavel() != null) {
                    responsavelId = tarefa.getResponsavel().getId();
                }
            } catch (NumberFormatException e) {
                tarefa = new Tarefa();
            }
        } else {
            tarefa = new Tarefa();
        }

        responsaveis = responsavelDAO.listarTodos();
    }

    public String salvar() {
        if (responsavelId != null) {
            Responsavel responsavel = responsavelDAO.buscarPorId(responsavelId);
            tarefa.setResponsavel(responsavel);
        }

        tarefaDAO.salvar(tarefa);
        return "lista.xhtml?faces-redirect=true";
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

    public Long getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(Long responsavelId) {
        this.responsavelId = responsavelId;
    }
}
