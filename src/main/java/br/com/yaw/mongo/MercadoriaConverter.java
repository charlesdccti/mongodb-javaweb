package br.com.yaw.mongo;

import static br.com.yaw.mongo.model.Mercadoria.getCurrencyInstance;
import static br.com.yaw.mongo.model.Mercadoria.getNumberFormatInstance;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;

import br.com.yaw.mongo.model.Mercadoria;
import spark.Request;

/**
 * Responsável por converter os dados <code>Request</code> (Spark) para Mercadoria, e o contrário.
 * 
 * @author eder.magalhaes
 */
public class MercadoriaConverter {

	public Mercadoria toObject(Request request) {
		StringBuilder sb = new StringBuilder();

		ObjectId id = null;
		String nome = null;
		String descricao = null;
		Double preco = null;
		Integer quantidade = null;
		
		try {
			id = toId(request);
		} catch (RuntimeException re) {
			sb.append(re.toString());
		}
		
		try {
			nome = toString("nome", request);
		} catch (RuntimeException re) {
			sb.append(re.toString());
		}
		
		try {
			descricao = toString("descricao", request);
		} catch (RuntimeException re) {
			sb.append(re.toString());
		}
		
		try {
			preco = toCurrency("preco", request);
		} catch (RuntimeException re) {
			sb.append(re.toString());
		}
		
		try {
			quantidade = toInteger("quantidade", request);
		} catch (RuntimeException re) {
			sb.append(re.toString());
		}
		
		if (!sb.toString().isEmpty()) {
			throw new RuntimeException(sb.toString());
		}
		
		return new Mercadoria(id, nome, descricao, quantidade, preco);
	}
	
	public Map<String, String> toRequest(Mercadoria m) {
		HashMap<String, String> properties = new HashMap<>();
		properties.put("id", m.getId() == null ? "" : m.getId().toString());
		properties.put("nome", m.getNome() == null ? "" : m.getNome());
		properties.put("descricao", m.getDescricao() == null ? "" : m.getDescricao());
		properties.put("preco", m.getPreco() == null ? "" : getNumberFormatInstance().format(m.getPreco()));
		properties.put("quantidade", m.getQuantidade() == null ? "" : m.getQuantidade().toString());
		
		return properties;
	}
	
	/**
	 * @param field - campo string
	 * @param request
	 * @return return o conteudo string do campo informado.
	 */
	private static String toString(String field, Request request) {
		return request.queryParams(field);
	}
	
	/**
	 * @param request
	 * @return converte e retorna o atributo _id na request, senão existir retorna null.
	 * @throws RuntimeException se algo falhar
	 */
	private static ObjectId toId(Request request) {
		try {
			String value = request.queryParams("id");
			return value == null || "".equals(value) ? null : new ObjectId(value);
		} catch (Exception ex) {
			throw new RuntimeException("Valor de _id invalido.");
		}
	}
	
	/**
	 * @param field - campo moeda
	 * @param request
	 * @return converte e retorna o campo de moeda da request em Double.
	 * @throws RuntimeException se algo falhar
	 */
	private static Double toCurrency(String field, Request request) {
		try {
			String value = request.queryParams(field);
			String curSymbol = "R$";
			if (!value.contains(curSymbol)) {
				value = curSymbol.concat(" "+value);
			}
			return getCurrencyInstance().parse(value).doubleValue();
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * @param field - campo inteiro
	 * @param request
	 * @return converte e retorna o campo inteiro da request.
	 * @throws RuntimeException se algo falhar 
	 */
	private static Integer toInteger(String field, Request request) {
		try {
			String value = request.queryParams(field);
			return Integer.valueOf(value);
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}
}
