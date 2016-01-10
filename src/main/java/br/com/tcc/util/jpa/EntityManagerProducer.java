package br.com.tcc.util.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped //evita que se fique inicialando a factory sempre que for preciso
public class EntityManagerProducer {
	
	private EntityManagerFactory factory;
	
	public EntityManagerProducer() {
		factory = Persistence.createEntityManagerFactory("PedidoPU");
	}
	
	@Produces @RequestScoped//produtor de entitymanager, escopo de requisicao
	public EntityManager createEntityManager(){
		return factory.createEntityManager();
	}
	
	public void closeEnttyManager(@Disposes EntityManager manager){ //@Disposses gatilho para finalizar uma requisiscao
		manager.close();
	}

}
