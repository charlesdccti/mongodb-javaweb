package br.com.yaw.mongo.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import br.com.yaw.mongo.model.Mercadoria;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Implementa o contrato de persistência, utilizando o MongoDB.
 * 
 * @author eder.magalhaes
 */
public class MercadoriaDAOMongoDB implements MercadoriaDAO {

	private DBCollection mercadorias;

	public MercadoriaDAOMongoDB(String mongoURI) throws Exception {
		final MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoURI));
        final DB database = mongoClient.getDB("appjava");
		mercadorias = database.getCollection("mercadorias");
	}

	public void save(Mercadoria m) {
		MercadoriaMapper mapper = new MercadoriaMapper();
		BasicDBObject doc = mapper.toBasicDB(m);
		
		if (doc == null) {
			return;
		}

		try {
			if (m.isNew()) {
				mercadorias.insert(doc);
				// atualiza o id
				ObjectId id = (ObjectId) doc.get("_id");
				m.setId(id);
			} else {
				mercadorias.update(new BasicDBObject("_id", m.getId()), doc);
			}
		} catch (Exception ex) {
			throw new RuntimeException("Não salvou a mercadoria: "+ex.getMessage());
		}
	}

	public void remove(Mercadoria m) {
		if (m == null) {
			return;
		}
		BasicDBObject doc = new BasicDBObject("_id", m.getId());
		try {
			mercadorias.remove(doc);
		} catch (Exception ex) {
			throw new RuntimeException("Não removeu a mercadoria: "+ex.getMessage());
		}
	}

	public List<Mercadoria> getAll() {
		List<Mercadoria> lista = new ArrayList<>();

		// retorna os documentos pela ordem de cadastro
		DBCursor cursor = mercadorias.find().sort(new BasicDBObject().append("datacadastro", 1));

		try {
			List<DBObject> docs = cursor.toArray();
			for (DBObject doc : docs) {
				lista.add(new MercadoriaMapper().toMercadoria(doc));
			}
		} finally {
			cursor.close();
		}

		return lista;
	}

	public Mercadoria findById(ObjectId id) {
		BasicDBObject _id = new BasicDBObject("_id", id);
		DBObject doc = mercadorias.findOne(_id);
		
		return new MercadoriaMapper().toMercadoria(doc);
	}

}
