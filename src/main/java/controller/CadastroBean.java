package controller;

import dao.ResponsavelDAO;
import dao.TarefaDAO;
import model.Responsavel;
import model.Tarefa;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "cadastroBean")
@ViewScoped
public class CadastroBean implements Serializable {

    private Tarefa tarefa;
    private List<Responsavel> responsaveis;
    private Long responsavelId;
    private boolean dadosCarregados = false;

    private TarefaDAO tarefaDAO = new TarefaDAO();
    private ResponsavelDAO responsavelDAO = new ResponsavelDAO();

    // Construtor
    public CadastroBean() {
        this.tarefa = new Tarefa();
        this.responsaveis = new ArrayList<>();
    }

    //carregar quando precisar
    public void carregarDados() {
        if (!dadosCarregados) {
            try {
                Map<String, String> params = FacesContext.getCurrentInstance()
                        .getExternalContext().getRequestParameterMap();
                String idParam = params.get("taskId");

                if (idParam != null) {
                    try {
                        Long id = Long.parseLong(idParam);
                        tarefa = tarefaDAO.buscarPorId(id);
                        if (tarefa != null && tarefa.getResponsavel() != null) {
                            responsavelId = tarefa.getResponsavel().getId();
                        }
                    } catch (NumberFormatException e) {
                        tarefa = new Tarefa();
                    }
                } else {
                    tarefa = new Tarefa();
                }

                responsaveis = responsavelDAO.listarTodos();
                dadosCarregados = true;

            } catch (Exception e) {
                System.err.println("Erro ao carregar dados: " + e.getMessage());
                // Manter valores padrão em caso de erro
                if (tarefa == null) {
                    tarefa = new Tarefa();
                }
            }
        }
    }

    public String salvar() {
        try {
            if (responsavelId != null) {
                Responsavel responsavel = responsavelDAO.buscarPorId(responsavelId);
                tarefa.setResponsavel(responsavel);
            }

            tarefaDAO.salvar(tarefa);
            return "lista.xhtml?faces-redirect=true";
        } catch (Exception e) {
            System.err.println("Erro ao salvar tarefa: " + e.getMessage());
            return null; // Fica na mesma página em caso de erro
        }
    }

    public Tarefa getTarefa() {
        carregarDados(); // Carrega dados quando necessário
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public List<Responsavel> getResponsaveis() {
        carregarDados(); // Carrega dados quando necessário
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