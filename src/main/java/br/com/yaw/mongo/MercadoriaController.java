package br.com.yaw.mongo;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.setPort;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.bson.types.ObjectId;

import spark.Request;
import spark.Response;
import spark.Route;
import br.com.yaw.mongo.dao.MercadoriaDAOMongoDB;
import br.com.yaw.mongo.model.Mercadoria;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

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
		
	private final Configuration cfg;
    private final MercadoriaDAOMongoDB mercadoriaDao;
    
    public MercadoriaController(String mongoURI) throws Exception {
        mercadoriaDao = new MercadoriaDAOMongoDB(URL_LOCAL_MONGOBD);
        cfg = createFreemarkerConfiguration();
        setPort(8082);
        
        mapeiaUrlRecursos();
    }
    
    private Configuration createFreemarkerConfiguration() {
        Configuration retVal = new Configuration();
        retVal.setClassForTemplateLoading(MercadoriaController.class, "/freemarker");
        return retVal;
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
    	//url principal da aplicação
    	get(new FreemarkerBasedRoute("/", "lista_template.ftl") {
            @Override
            public void doHandle(Request request, Response response, Writer writer)
            		throws IOException, TemplateException {
                
            	List<Mercadoria> mercadorias = mercadoriaDao.getAll();
            	SimpleHash root = new SimpleHash();
            	
            	root.put("activeLista", true);
                root.put("mercadorias", mercadorias);
                template.process(root, writer);
            }
        });
    	
    	//url da inclusaoo
    	get(new FreemarkerBasedRoute("/new", "mercadoria_template.ftl") {
            @Override
            public void doHandle(Request request, Response response, Writer writer)
            		throws IOException, TemplateException {
            	SimpleHash root = new SimpleHash();
            	root.put("activeIncluir", true);
                root.put("mercadoria", new MercadoriaConverter().toRequest(new Mercadoria()));

                template.process(root, writer);
            }
        });
    	
    	//url da edicao
    	get(new FreemarkerBasedRoute("/edit/:id", "mercadoria_template.ftl") {
            @Override
            public void doHandle(Request request, Response response, Writer writer)
            		throws IOException, TemplateException {
            	String idMercadoria = request.params(":id");
            	
            	Mercadoria mercadoria = null;
            	
            	try {
            		mercadoria = mercadoriaDao.findById(new ObjectId(idMercadoria));
            	} catch (Exception ex) {
            		response.redirect("/");
            		return;
            	}
            	
                SimpleHash root = new SimpleHash();
                root.put("activeIncluir", true);
                root.put("mercadoria", new MercadoriaConverter().toRequest(mercadoria));

                template.process(root, writer);
            }
        });
    	
    	post(new FreemarkerBasedRoute("/save", "mercadoria_template.ftl") {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
            	try {
                	Mercadoria m = new MercadoriaConverter().toObject(request);
                    mercadoriaDao.save(m);
                    
                    response.redirect("/");
            	} catch (Exception ex) {
            		SimpleHash root = new SimpleHash();
            		root.put("error", ex.getMessage());
            		template.process(root, writer);
            	}
            }
        });
    	
    	post(new FreemarkerBasedRoute("/delete/:id", "mercadoria_template.ftl") {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
            	String idMercadoria = request.params(":id");
            	
            	try {
            		Mercadoria mercadoria = mercadoriaDao.findById(new ObjectId(idMercadoria));
            		mercadoriaDao.remove(mercadoria);
            		
            		response.redirect("/");
            	} catch (Exception ex) {
            		SimpleHash root = new SimpleHash();
            		root.put("error", ex.getMessage());
            		template.process(root, writer);
            	}
            }
        });
    	
    	//url sobre
    	get(new FreemarkerBasedRoute("/sobre", "sobre_template.ftl") {
            @Override
            public void doHandle(Request request, Response response, Writer writer)
            		throws IOException, TemplateException { 
            	
            	SimpleHash root = new SimpleHash();
            	root.put("activeSobre", true);
                template.process(root, writer);
            }
    	    
        });
    	
    	//direciona os erros
        get(new FreemarkerBasedRoute("/internal_error", "error_template.ftl") {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                SimpleHash root = new SimpleHash();

                root.put("error", "System has encountered an error.");
                template.process(root, writer);
            }
        });
    }
    
    /**
     * Implementa tratamento basico de exceção.
     */
    private abstract class FreemarkerBasedRoute extends Route {
        final Template template;

        protected FreemarkerBasedRoute(final String path, final String templateName) throws IOException {
            super(path);
            template = cfg.getTemplate(templateName);
        }

        @Override
        public Object handle(Request request, Response response) {
            StringWriter writer = new StringWriter();
            try {
                doHandle(request, response, writer);
            } catch (Exception e) {
                e.printStackTrace();
                response.redirect("/internal_error");
            }
            return writer;
        }

        protected abstract void doHandle(final Request request, final Response response, final Writer writer)
                throws IOException, TemplateException;

    }
    
}
