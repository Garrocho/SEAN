package com.semon.recursos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Conexao {

	public static void main(String args[]) throws IOException {
		Conexao conexao = new Conexao();
		if (conexao.conectaServidor()) {
			System.out.println("Estabeleceu Conexão");
			conexao.getEnviaDados().writeObject("LOGAR");
			conexao.getEnviaDados().flush();
			conexao.getEnviaDados().writeObject("charles");
			conexao.getEnviaDados().flush();
		}
		else {
			System.out.println("erro");
		}
	}

	private ObjectOutputStream enviaDados;
	private ObjectInputStream recebeDados;

	private Socket soquete;
	private int PORTA;
	private String ENDERECO;

	public Conexao() {
		super();
		this.ENDERECO = "localhost";
		this.PORTA = 8888; 
	}

	public Conexao(String endereco, int porta) {
		super();
		this.ENDERECO = endereco.substring(endereco.indexOf("/") + 1 );
		this.PORTA = porta;
	}

	public Boolean conectaServidor() {

		try {
			InetAddress endereco = InetAddress.getByName(ENDERECO);
			soquete = new Socket(endereco, PORTA);
			enviaDados = new ObjectOutputStream(soquete.getOutputStream());
			recebeDados = new ObjectInputStream(soquete.getInputStream());
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public void desconectaServidor() throws IOException {

		recebeDados.close();
		enviaDados.close();
		soquete.close();
	}

	public ObjectOutputStream getEnviaDados() {
		return enviaDados;
	}

	public ObjectInputStream getRecebeDados() {
		return recebeDados;
	}

	public Socket getSoquete() {
		return soquete;
	}

	public int getPORTA() {
		return PORTA;
	}

	public String getENDERECO() {
		return ENDERECO;
	}

}
