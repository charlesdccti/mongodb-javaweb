package br.com.yaw.mongo;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import br.com.yaw.mongo.dao.MercadoriaDAOMongoDB;
import br.com.yaw.mongo.model.Mercadoria;
import freemarker.template.SimpleHash;

/**
 * Classe executável da aplicação. Define a controller do Spark e as urls de recursos da aplicação.
 * 
 * <p>Execute a classe via: Run as -> Java Application</p>
 * <p>Atenção: por default a porta utilizada é 8082. </p>
 * 
 * @author eder.magalhaes
 */
public class MercadoriaController {

	public static void main(String[] args){
		try {
			new MercadoriaController(URL_LOCAL_MONGOBD);	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	//Atenção: essa é a url para acessar o MongoDB, modifique se necessário
	private static final String URL_LOCAL_MONGOBD = "mongodb://localhost";
		
	private final MercadoriaDAOMongoDB mercadoriaDao;
    
    public MercadoriaController(String mongoURI) throws Exception {
        mercadoriaDao = new MercadoriaDAOMongoDB(URL_LOCAL_MONGOBD);
        port(8082);
        mapeiaUrlRecursos();
    }
    
    /**
     * Faz o mapeamento de urls da aplicação:
     * <ul>
	 *   <li>GET / : Exibe a lista de mercadorias;</li>
	 *   <li>GET /new : Exibe página de inclusão da mercadoria;</li>
	 *   <li>GET /edit/:id - Exibe a página de edição da mercadoria (by id);</li>
	 *   <li>POST /save - Envia os dados do cadastro (novo ou edição) da mercadoria;</li>
	 *   <li>GET /sobre - Exibe a página com destalhes sobre esse projeto;</li>
	 * </ul>
     * @throws IOException - se algum problema ocorrer durante o processamento
     */
    private void mapeiaUrlRecursos() throws IOException {
    	//root - list page
    	get("/", (request, response) -> {
    		List<Mercadoria> mercadorias = mercadoriaDao.getAll();
        	SimpleHash root = new SimpleHash();
        	
        	root.put("activeLista", true);
            root.put("mercadorias", mercadorias);
            
            if (request.params(":error") != null) {
            	root.put("error", request.params("error"));
            }
            
            return new ModelAndView(root, "lista_template.ftl");
    	}, new CustomFreeMarkerEngine());
    	
    	//new form
    	get("/new", (request, response) -> {
    		SimpleHash root = new SimpleHash();
        	root.put("activeIncluir", true);
            root.put("mercadoria", new MercadoriaConverter().toRequest(new Mercadoria()));
            
            return new ModelAndView(root, "mercadoria_template.ftl");
    	}, new CustomFreeMarkerEngine());
    	
    	//update form
    	get("/edit/:id", (request, response) -> {
    		String idMercadoria = request.params(":id");
        	
        	Mercadoria mercadoria = null;
        	
        	try {
        		mercadoria = mercadoriaDao.findById(new ObjectId(idMercadoria));
        	} catch (Exception ex) {
        		response.redirect("/?error=Registro não econtrado!");
        		return null;
        	}
        	
            SimpleHash root = new SimpleHash();
            root.put("activeIncluir", true);
            root.put("mercadoria", new MercadoriaConverter().toRequest(mercadoria));
            
            return new ModelAndView(root, "mercadoria_template.ftl");
    	}, new CustomFreeMarkerEngine());
    	
    	//persist controller (save and update)
    	post("/save", (request, response) -> {
    		Mercadoria mercadoria = new MercadoriaConverter().toObject(request);
    		try {
    			mercadoriaDao.save(mercadoria);
                response.redirect("/");
                return null;
        	} catch (Exception ex) {
        		SimpleHash root = new SimpleHash();
        		root.put("mercadoria", new MercadoriaConverter().toRequest(mercadoria));
        		root.put("error", ex.getMessage());
        		return new ModelAndView(root, "mercadoria_template.ftl");
        	}
    	}, new CustomFreeMarkerEngine());
    	
    	//delete controller
    	post("/delete/:id", (request, response) -> {
    		String idMercadoria = request.params(":id");
    		
    		Mercadoria mercadoria;
    		
    		try {
    			mercadoria = mercadoriaDao.findById(new ObjectId(idMercadoria));
    		} catch (Exception ex) {
    			response.redirect("/?error=Registro não econtrado!");
        		return null;
    		}
    		
    		try {
    			mercadoriaDao.remove(mercadoria);
                response.redirect("/");
                return null;
        	} catch (Exception ex) {
        		SimpleHash root = new SimpleHash();
        		root.put("mercadoria", new MercadoriaConverter().toRequest(mercadoria));
        		root.put("error", ex.getMessage());
        		return new ModelAndView(root, "mercadoria_template.ftl");
        	}
    	}, new CustomFreeMarkerEngine());
    	
    	//about page
    	get("/about", (request, response) -> {
    		SimpleHash root = new SimpleHash();
        	root.put("activeAbout", true);
            
            return new ModelAndView(root, "sobre_template.ftl");
    	}, new CustomFreeMarkerEngine());
    	
    	//exception handle
    	exception(RuntimeException.class, (e, request, response) -> {
    	    response.redirect("/internal_error");
    	});
    	
    	get("/internal_error", (request, response) -> {
    		SimpleHash root = new SimpleHash();
        	root.put("error", "System has encountered an error.");
            
            return new ModelAndView(root, "error_template.ftl");
    	}, new CustomFreeMarkerEngine());
    }
    
    public class CustomFreeMarkerEngine extends FreeMarkerEngine {
    	
    	@Override
    	public String render(ModelAndView modelAndView) {
    		if (modelAndView == null) {
    			return ""; //workaround for response.redirect
    		}
    		return super.render(modelAndView);
    	}
    	
    }
    
}
