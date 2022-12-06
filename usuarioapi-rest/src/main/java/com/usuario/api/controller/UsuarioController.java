package com.usuario.api.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.api.model.Usuario;
import com.usuario.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario adicionar(@RequestBody Usuario usuario) {

		return usuarioRepository.save(usuario);
	}
	
	@PostMapping("/listaUsers")
	@ResponseStatus(HttpStatus.CREATED)
	public List<Usuario> adicionarListaUsuarios(@RequestBody List<Usuario> usuarios) {

		return usuarioRepository.saveAll(usuarios);
	}

	@GetMapping("/{id}")
	public Optional<Usuario> buscarPorId(@PathVariable Long id) {
		return usuarioRepository.findById(id);
	}

	
	 @PutMapping("/{id}") 
	 public ResponseEntity<Object> atualizaUsuario(@RequestBody Usuario novoUsuario, @PathVariable Long id) {
		 Optional<Usuario> oldUser = usuarioRepository.findById(id);
	        if(oldUser.isPresent()){
	            Usuario usuario = oldUser.get();
	            if(!usuario.getCpf().equals(novoUsuario.getCpf()))
	            	return new ResponseEntity<Object>("Não é possivel atualizar o CPF", HttpStatus.OK);
	            usuario.setNome(novoUsuario.getNome());
	            usuario.setDtNasc(novoUsuario.getDtNasc());
	            usuario.setUsuario(novoUsuario.getUsuario());
	            usuario.setSenha(novoUsuario.getSenha());
	            usuarioRepository.save(usuario);
	            return new ResponseEntity<Object>(usuario, HttpStatus.OK);
	        }
	        else
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	 }
	 

	@DeleteMapping("/{id}")
	public void excluirUsuario(@PathVariable Long id) {
		usuarioRepository.deleteById(id);
	}
}
