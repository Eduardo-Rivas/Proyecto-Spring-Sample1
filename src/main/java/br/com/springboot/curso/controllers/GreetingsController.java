package br.com.springboot.curso.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso.model.Usuario;
import br.com.springboot.curso.repositories.UsuarioRepository;

@RestController
public class GreetingsController {

	//--Hacemos la Inyeccion de Dependencia--//
	@Autowired
	private UsuarioRepository usuarioRepository;
	
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring-Bott :" + name + "...!";
    }
    
    
    @RequestMapping(value = "/holamundo/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retirnaHolaMundo(@PathVariable String name)
    {
    	//--Instanciamos la Clase Usuario--//
    	Usuario usuario = new Usuario();
    	
    	usuario.setName(name);
    	usuario.setEdad(54);
    	
    	//--Guardamos los Datos en la Tabla--//
    	usuarioRepository.save(usuario);

    	return "Hola Mundo Es :"+name +" y Tengo :"+usuario.getEdad() +" de Edad..!";
    }
    
    //--Primer API--//
    @GetMapping(value = "listartodos")
    @ResponseBody
    public ResponseEntity<List<Usuario>> listaAll()
    {
    	List<Usuario> usuarios = usuarioRepository.findAll();
    	
    	//--Retorna la Lista en formato Json--//
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }

    @PostMapping(value = "salvar")
    @ResponseBody
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario)
    {
    	//--Guarda los Datos--//
    	Usuario user = usuarioRepository.save(usuario);
    	
    	//--Retorna un Json--//
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    //--Actualizar Un Usuario--//
    @PutMapping(value = "actualizar")
    @ResponseBody
    public ResponseEntity<?> actualizar(@RequestBody Usuario usuario)
    {
    	if(usuario.getId() == null)
    	{
    		return new ResponseEntity<String>("Id No Especificado, para ser Actualizado...!", HttpStatus.OK);
    	}
    	else
    	{
        	Usuario user = usuarioRepository.saveAndFlush(usuario);
        	
        	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    	}
    	
    }
    
    
    @DeleteMapping(value = "delete")
    @ResponseBody
    public ResponseEntity<String> deleteById(@RequestParam Long userid)
    {
    	usuarioRepository.deleteById(userid);
    	
    	//--Retorno de Mensaje--//
    	return new ResponseEntity<String>("Usuario Nro.: "+userid+" Eliminado con Susseso...!", HttpStatus.OK);
    }
    
    //--Busqueda de Usuario por Id--//
    @GetMapping(value = "buscaruserid")
    @ResponseBody
    public ResponseEntity<Usuario> buscarById(@RequestParam(name = "userid") Long userid)
    {
    	Usuario user =  usuarioRepository.findById(userid).get();
    	
    	//--Retorna el Usuario en Fromato Json--//
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK); 
    }
    
    //--Busqueda de Usuario por Nombre--//
    @GetMapping(value = "buscarPorNombre")
    @ResponseBody
    public ResponseEntity<List<Usuario>> buscarPorNombre(@RequestParam(name = "name") String name)
    {
    	//--Quitamos los Espacios q pueda Tener el Nombre en el PostMan--//
    	List<Usuario> usuarios =  usuarioRepository.buscarPorNome(name.trim().toUpperCase()); 
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }
    
    
}
