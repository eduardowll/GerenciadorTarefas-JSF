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

    @PostConstruct
    public void init() {
        tarefas = tarefaDAO.listarTodas();
        tarefasFiltradas = tarefas; // inicialmente mostra todas
        responsaveis = responsavelDAO.listarTodos();
    }

    public void buscarTarefas() {
        tarefasFiltradas = tarefaDAO.buscarComFiltros(
                numeroFiltro,
                tituloDescricaoFiltro,
                responsavelIdFiltro,
                statusFiltro
        );
    }

    public void limparFiltros() {
        numeroFiltro = null;
        tituloDescricaoFiltro = null;
        responsavelIdFiltro = null;
        statusFiltro = null;
        tarefasFiltradas = tarefaDAO.listarTodas();
    }

    public void excluir(Long id) {
        tarefaDAO.remover(id);
        // Reaplica os filtros após excluir
        buscarTarefas();
    }

    public String editar(Long id) {
        return "cadastro.xhtml?faces-redirect=true&taskId=" + id;
    }

    public void concluir(Long id) {
        Tarefa tarefa = tarefaDAO.buscarPorId(id);
        if (tarefa != null && !"Concluído".equals(tarefa.getStatus())) {
            tarefa.setStatus("Concluído");
            tarefaDAO.salvar(tarefa);
            // Reaplica os filtros após concluir
            buscarTarefas();
        }
    }

    // Getters e Setters
    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public List<Tarefa> getTarefasFiltradas() {
        return tarefasFiltradas;
    }

    public void setTarefasFiltradas(List<Tarefa> tarefasFiltradas) {
        this.tarefasFiltradas = tarefasFiltradas;
    }

    public List<Responsavel> getResponsaveis() {
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