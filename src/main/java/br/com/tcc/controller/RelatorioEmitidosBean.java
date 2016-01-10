package br.com.tcc.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import br.com.tcc.util.jsf.FacesUtil;
import br.com.tcc.util.report.ExecutorRelatorio;

@Named
@RequestScoped
public class RelatorioEmitidosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	private Date dataFim;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		Date date = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");  
		String data = formatador.format(date);
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);

		ExecutorRelatorio executor = new ExecutorRelatorio(
				"/relatorios/emitidos.jasper", this.response,
				parametros, "pedidosemitidos"+data+".pdf");

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil
					.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	@NotNull
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@NotNull
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
