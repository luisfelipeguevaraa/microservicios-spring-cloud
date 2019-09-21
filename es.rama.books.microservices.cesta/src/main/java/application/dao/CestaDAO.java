package application.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.domain.Cesta;
import application.domain.Producto;

@Repository
public interface CestaDAO extends JpaRepository<Cesta, Integer> {
	
}
