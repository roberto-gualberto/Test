package br.com.tcc.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.tcc.model.Pedido;
import br.com.tcc.model.StatusPedido;
import br.com.tcc.model.Usuario;
import br.com.tcc.repository.PedidoRepository;
import br.com.tcc.security.UsuarioLogado;
import br.com.tcc.security.UsuarioSistema;

@Named
@RequestScoped
public class GraficoPedidosEmitidosBean {

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd");
	
	@Inject
	private PedidoRepository pedidoRepository;
	
	private CartesianChartModel model;
	
	private StatusPedido status;
	
	
	@Inject
	@UsuarioLogado
	private UsuarioSistema usuarioLogado;
	

	public void preRender() {
		this.model = new CartesianChartModel();
		
		adicionarSerie("Todos os pedidos",null,null);
		adicionarSerie("Meus pedidos",usuarioLogado.getUsuario(),null);
	    adicionarSerie("Pedidos Cancelados",null,StatusPedido.CANCELADO);
		adicionarSerie("Pedidos Aprovados",null,StatusPedido.APROVADO);
		adicionarSerie("Pedidos Emitidos",null,StatusPedido.EMITIDO);
	}
	
	private void adicionarSerie(String rotulo, Usuario criadoPor, StatusPedido status) {
		Map<Date, Long> itensPorData = this.pedidoRepository.itensTotaisPorData((long) 30, criadoPor, status);
		
		ChartSeries series = new ChartSeries(rotulo);
		
		for (Date data : itensPorData.keySet()){
			series.set(DATE_FORMAT.format(data), itensPorData.get(data));
		}
		
		this.model.addSeries(series);
	}

	public CartesianChartModel getModel() {
		return model;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}
	
	
	
}
