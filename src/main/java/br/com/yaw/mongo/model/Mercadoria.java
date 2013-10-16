package br.com.yaw.mongo.model;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import org.bson.types.ObjectId;

/**
 * Entidade principal do sistema.
 * 
 * @author eder.magalhaes
 */
public class Mercadoria {

	private ObjectId id;
	
	private String nome;
	
	private String descricao;
	
	private Integer quantidade;
	
	private Double preco;
	
	private Date dataCadastro;
	
	public Mercadoria() {
	}
	
	public Mercadoria(ObjectId id, String nome, String descricao, Integer quantidade, Double preco) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.preco = preco;
		this.dataCadastro = new Date();
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public ObjectId getId() {
		return id;
	}
	
	public Double getPreco() {
		return preco;
	}
	
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public boolean isNew() {
		return id == null;
	}
	
	public Date getDataCadastro() {
		return dataCadastro;
	}
	
	protected void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	public String getPrecoFmt() {
		if (this.preco == null) {
			return "";
		}
		return getCurrencyInstance().format(this.preco);
	}
	
	public static NumberFormat getCurrencyInstance() {
		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	}
	
	public static NumberFormat getNumberFormatInstance() {
		return NumberFormat.getInstance();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		
		Mercadoria outro = (Mercadoria) obj;
		boolean equal = (id != null && id.equals(outro.id)) 
				|| (nome != null && nome.equals(outro.nome))
				|| (descricao != null && descricao.equals(outro.descricao));
		return equal;
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		
		hash = (31 * hash) + (id == null ? 0 : id.hashCode());
		hash = (31 * hash) + (nome == null ? 0 : nome.hashCode());
		hash = (31 * hash) + (descricao == null ? 0 : descricao.hashCode());
		
		return hash;
	}
	
}
