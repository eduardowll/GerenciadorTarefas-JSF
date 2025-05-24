package dao;

import model.Tarefa;

import javax.persistence.*;
import java.util.List;

public class TarefaDAO {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("tarefasPU");

    public void salvar(Tarefa tarefa) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            if (tarefa.getId() == null) {
                em.persist(tarefa);
            } else {
                em.merge(tarefa);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Tarefa tarefa = em.find(Tarefa.class, id);
            if (tarefa != null) {
                em.getTransaction().begin();
                em.remove(tarefa);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    public List<Tarefa> listarTodas() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM Tarefa t", Tarefa.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Tarefa buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Tarefa.class, id);
        } finally {
            em.close();
        }
    }

    public List<Tarefa> buscarComFiltros(String numero, String tituloDescricao,
                                         Long responsavelId, String status) {
        EntityManager em = emf.createEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT t FROM Tarefa t WHERE 1=1");

            // Adiciona filtros dinamicamente
            if (numero != null && !numero.trim().isEmpty()) {
                jpql.append(" AND CAST(t.id AS string) LIKE :numero");
            }

            if (tituloDescricao != null && !tituloDescricao.trim().isEmpty()) {
                jpql.append(" AND (LOWER(t.titulo) LIKE LOWER(:tituloDescricao) OR LOWER(t.descricao) LIKE LOWER(:tituloDescricao))");
            }

            if (responsavelId != null) {
                jpql.append(" AND t.responsavel.id = :responsavelId");
            }

            if (status != null && !status.trim().isEmpty()) {
                jpql.append(" AND t.status = :status");
            }

            TypedQuery<Tarefa> query = em.createQuery(jpql.toString(), Tarefa.class);

            // Define os parâmetros
            if (numero != null && !numero.trim().isEmpty()) {
                query.setParameter("numero", "%" + numero + "%");
            }

            if (tituloDescricao != null && !tituloDescricao.trim().isEmpty()) {
                query.setParameter("tituloDescricao", "%" + tituloDescricao + "%");
            }

            if (responsavelId != null) {
                query.setParameter("responsavelId", responsavelId);
            }

            if (status != null && !status.trim().isEmpty()) {
                query.setParameter("status", status);
            }

            return query.getResultList();

        } finally {
            em.close();
        }
    }
}