package br.com.springboot.curso.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.springboot.curso.model.Usuario;
 
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> 
{
	//--Quitamos los espacios q Pueda tener el Nombre en la Tabla--//
	@Query(value = "SELECT u FROM Usuario u WHERE upper(trim(u.name)) LIKE %?1%")
	List<Usuario> buscarPorNome(String name); 
}
