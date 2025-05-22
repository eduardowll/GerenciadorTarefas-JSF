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
}

