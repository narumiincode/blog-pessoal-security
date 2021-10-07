package blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blogpessoal.Service.UsuarioService;
import blogpessoal.model.UserLogin;
import blogpessoal.model.Usuario;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins= "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll() {
		return ResponseEntity.ok(usuarioService.listarUsuarios());
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UserLogin> login(@RequestBody Optional<UserLogin> user) {
		return usuarioService.autenticarUsuario(user)
			.map(respostaAutenticacao -> ResponseEntity.ok(respostaAutenticacao))
			.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario usuario) {

		return usuarioService.cadastrarUsuario(usuario)
			.map(respostaCadastro -> ResponseEntity.status(HttpStatus.CREATED).body(respostaCadastro))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
}
