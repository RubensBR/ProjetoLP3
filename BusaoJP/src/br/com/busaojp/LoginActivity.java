package br.com.busaojp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import br.com.busaojp.onibus.OnibusDAOJSON;
import br.com.busaojp.utils.ActivityUtil;

public class LoginActivity extends Activity {
	
	private EditText mLogin;
	private EditText mSenha;
	private RadioGroup mRGroup;
	private ProgressDialog mProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		mLogin = (EditText) findViewById(R.id.edit_login);
		mSenha = (EditText) findViewById(R.id.edit_senha);
		mRGroup = (RadioGroup) findViewById(R.id.radiobutton_login);
		
	}

	public void enviar(View v) {
		String login = mLogin.getText().toString();
		String senha = mSenha.getText().toString();
		new EnviarTask(login, senha).execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferencias, menu);
		return true;
	}
	
	private class EnviarTask extends AsyncTask<String, String, Boolean> {
		
		private String login;
		private String senha;
		private int opcao;
		private String mensagem;
		
		public EnviarTask(String login, String senha) {
			this.login = login;
			this.senha = senha;
			opcao = mRGroup.getCheckedRadioButtonId();
		}
		
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(LoginActivity.this, "Aguarde", "Enviando requisição", true);
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			OnibusDAOJSON dao = new OnibusDAOJSON(); 
			if (opcao == R.id.login_radioButton) {
				int res = dao.FazerLogin(login, senha);
				if (res == 0) {
					return true;
				} else if (res == 1) {
					mensagem = "Login ou Senha inválido.";
					return false;
				} else {
					mensagem = "Erro ao conectar. Verifique sua conexão";
					return false;
				}
			} else {
				int res = dao.CadastrarUsuario(login, senha);
				if (res == 0) {
					return true;
				} else if (res == 1) {
					mensagem = "Este usuário já existe";
					return false;
				} else {
					mensagem = "Erro ao conectar. Verifique sua conexão";
					return false;
				}
			}
		}
		
		@Override
		protected void onPostExecute(Boolean sucesso) {
			mProgress.cancel();
			if (sucesso) {
				ActivityUtil.mudarActivity(LoginActivity.this, MainActivity.class);
				finish();
			} else {
				Toast.makeText(LoginActivity.this, mensagem, Toast.LENGTH_SHORT).show();
			}
		}
	}

}
