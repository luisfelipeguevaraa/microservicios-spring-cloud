package application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import application.dao.CestaDAO;
import application.domain.Cesta;
import application.service.CestaService;

@Service("cestaService")
@Transactional
public class CestaServiceImpl implements CestaService {

	@Autowired
	private CestaDAO cestaDAO;	
		
	public List<Cesta> getCestas() {
		return cestaDAO.findAll();
	}
	
	public Cesta getCestaById(Integer id) {
		return cestaDAO.findOne(id);
	}
	
	public void addCesta(Cesta p) {
		cestaDAO.save(p);
		
	}
	
	public void delCestaById(Integer id) {
		cestaDAO.delete(id);
	}

}
