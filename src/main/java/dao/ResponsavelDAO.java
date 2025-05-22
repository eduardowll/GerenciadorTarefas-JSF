package dao;

import model.Responsavel;

import javax.persistence.*;
import java.util.List;

public class ResponsavelDAO {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("tarefasPU");

    public void salvar(Responsavel responsavel) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            if (responsavel.getId() == null) {
                em.persist(responsavel);
            } else {
                em.merge(responsavel);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Responsavel buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Responsavel.class, id);
        } finally {
            em.close();
        }
    }

    public List<Responsavel> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Responsavel r", Responsavel.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
