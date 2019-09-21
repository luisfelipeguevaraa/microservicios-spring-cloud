package application.service;

import java.util.List;

import application.domain.Cesta;

public interface CestaService {

	public List<Cesta> getCestas();
	public Cesta getCestaById(Integer id);
	public void addCesta(Cesta p);
	public void delCestaById(Integer id);
}
