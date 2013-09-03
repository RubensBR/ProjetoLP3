package busaojp.servidor;

import java.util.ArrayList;

public class ValidacaoLogin {

	private boolean sucesso = false;

	public boolean isSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}
	
	public void validarLogin(String login, String senha) {
		ArrayList<Usuario> usuarios = UsuarioRepositorio.getUsuarios();
		System.out.println(login);
		System.out.println(senha);
		for (Usuario usuario : usuarios) {
			System.out.println(usuario.getLogin());
			System.out.println(usuario.getSenha());
			if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
				sucesso = true;
				return;
			}
		}
	}	
}
