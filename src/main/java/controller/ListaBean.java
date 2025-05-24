package controller;

import dao.ResponsavelDAO;
import dao.TarefaDAO;
import model.Responsavel;
import model.Tarefa;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "listaBean")
@ViewScoped
public class ListaBean implements Serializable {

    private List<Tarefa> tarefas;
    private List<Tarefa> tarefasFiltradas;
    private List<Responsavel> responsaveis;

    private TarefaDAO tarefaDAO = new TarefaDAO();
    private ResponsavelDAO responsavelDAO = new ResponsavelDAO();

    // Campos de filtro
    private String numeroFiltro;
    private String tituloDescricaoFiltro;
    private Long responsavelIdFiltro;
    private String statusFiltro;

    // Construtor
    public ListaBean() {
        // Inicialização segura
        this.tarefas = new ArrayList<>();
        this.tarefasFiltradas = new ArrayList<>();
        this.responsaveis = new ArrayList<>();
    }

    //carregar quando precisar
    public void carregarDados() {
        try {
            if (tarefas.isEmpty()) {
                tarefas = tarefaDAO.listarTodas();
                tarefasFiltradas = tarefas;
            }
            if (responsaveis.isEmpty()) {
                responsaveis = responsavelDAO.listarTodos();
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            // Manter listas vazias em caso de erro
        }
    }

    public void buscarTarefas() {
        try {
            tarefasFiltradas = tarefaDAO.buscarComFiltros(
                    numeroFiltro,
                    tituloDescricaoFiltro,
                    responsavelIdFiltro,
                    statusFiltro
            );
        } catch (Exception e) {
            System.err.println("Erro ao buscar tarefas: " + e.getMessage());
        }
    }

    public void limparFiltros() {
        numeroFiltro = null;
        tituloDescricaoFiltro = null;
        responsavelIdFiltro = null;
        statusFiltro = null;
        try {
            tarefasFiltradas = tarefaDAO.listarTodas();
        } catch (Exception e) {
            System.err.println("Erro ao limpar filtros: " + e.getMessage());
        }
    }

    public void excluir(Long id) {
        try {
            tarefaDAO.remover(id);
            buscarTarefas();
        } catch (Exception e) {
            System.err.println("Erro ao excluir tarefa: " + e.getMessage());
        }
    }

    public String editar(Long id) {
        return "cadastro.xhtml?faces-redirect=true&taskId=" + id;
    }

    public void concluir(Long id) {
        try {
            Tarefa tarefa = tarefaDAO.buscarPorId(id);
            if (tarefa != null && !"Concluído".equals(tarefa.getStatus())) {
                tarefa.setStatus("Concluído");
                tarefaDAO.salvar(tarefa);
                buscarTarefas();
            }
        } catch (Exception e) {
            System.err.println("Erro ao concluir tarefa: " + e.getMessage());
        }
    }

    // Getters e Setters
    public List<Tarefa> getTarefas() {
        carregarDados(); // Carrega dados quando necessário
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public List<Tarefa> getTarefasFiltradas() {
        carregarDados(); // Carrega dados quando necessário
        return tarefasFiltradas;
    }

    public void setTarefasFiltradas(List<Tarefa> tarefasFiltradas) {
        this.tarefasFiltradas = tarefasFiltradas;
    }

    public List<Responsavel> getResponsaveis() {
        carregarDados(); // Carrega dados quando necessário
        return responsaveis;
    }

    public void setResponsaveis(List<Responsavel> responsaveis) {
        this.responsaveis = responsaveis;
    }

    public String getNumeroFiltro() {
        return numeroFiltro;
    }

    public void setNumeroFiltro(String numeroFiltro) {
        this.numeroFiltro = numeroFiltro;
    }

    public String getTituloDescricaoFiltro() {
        return tituloDescricaoFiltro;
    }

    public void setTituloDescricaoFiltro(String tituloDescricaoFiltro) {
        this.tituloDescricaoFiltro = tituloDescricaoFiltro;
    }

    public Long getResponsavelIdFiltro() {
        return responsavelIdFiltro;
    }

    public void setResponsavelIdFiltro(Long responsavelIdFiltro) {
        this.responsavelIdFiltro = responsavelIdFiltro;
    }

    public String getStatusFiltro() {
        return statusFiltro;
    }

    public void setStatusFiltro(String statusFiltro) {
        this.statusFiltro = statusFiltro;
    }
}