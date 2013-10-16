package br.com.yaw.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;

import br.com.yaw.mongo.model.Mercadoria;

/**
 * Contrato de persistência para a entidade <code>Mercadoria</code>. 
 * 
 * @author eder.magalhaes
 */
public interface MercadoriaDAO {

	/**
     * Faz a inserção ou atualização da mercadoria na base de dados.
     * @param mercadoria
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    void save(Mercadoria mercadoria);
    
    /**
     * @return Lista com todas as mercadorias cadastradas no banco de dados.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    List<Mercadoria> getAll();
    
    /**
     * Exclui o registro da mercadoria na base de dados 
     * @param mercadoria
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    void remove(Mercadoria mercadoria);
    
    /**
     * @param id filtro da pesquisa.
     * @return Mercadoria com filtro no id, caso nao exista retorna <code>null</code>.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    Mercadoria findById(ObjectId id);
    
}
