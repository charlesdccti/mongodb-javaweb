package br.com.yaw.mongo.dao;

import org.bson.types.ObjectId;

import br.com.yaw.mongo.model.Mercadoria;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Faz o mapper de <code>BasicDBObject</code> para <code>Mercadoria</code> e
 * o contrario.
 * 
 * @author eder.magalhaes
 */
public class MercadoriaMapper {

	public BasicDBObject toBasicDB(Mercadoria m) {
		if (m == null) {
			return null;
		}
		BasicDBObject doc = new BasicDBObject();
		
		doc.append("nome", m.getNome());
		doc.append("descricao", m.getDescricao());
        doc.append("preco", m.getPreco());
        doc.append("quantidade", m.getQuantidade());
		doc.append("datacadastro", m.getDataCadastro());
		
		return doc;
	}
	
	public Mercadoria toMercadoria(DBObject doc) {
		if (doc == null) {
			return null;
		}
		
		ObjectId id = (ObjectId) doc.get("_id");
    	String nome = (String) doc.get("nome");
    	String descricao = (String) doc.get("descricao");
    	Double preco = doc.get("preco") == null ? null : (Double) doc.get("preco");
    	Integer quantidade = doc.get("quantidade") == null ? null : new Double(doc.get("quantidade").toString()).intValue();
    	
    	return new Mercadoria(id, nome, descricao, quantidade, preco);
	}
}
