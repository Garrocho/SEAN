package com.semon.recursos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Conexao {

	private OutputStream enviaDados;
	private InputStream recebeDados;

	private Socket soquete;
	private int PORTA;
	private String ENDERECO;

	public Conexao() {
		super();
		this.ENDERECO = "10.3.1.53";
		this.PORTA = 8888;
	}

	public Conexao(String endereco, int porta) {
		super();
		this.ENDERECO = endereco.substring(endereco.indexOf("/") + 1 );
		this.PORTA = porta;
	}

	public boolean conectaServidor() {

		try {
			InetAddress endereco = InetAddress.getByName(ENDERECO);
			soquete = new Socket(endereco, PORTA);
			enviaDados = soquete.getOutputStream();
			recebeDados = soquete.getInputStream();
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
	
	public String byteParaString(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int i; (i = in.read(b)) != -1;) {
            out.append(new String(b, 0, i));
        }
        return out.toString();
    }

	public OutputStream getEnviaDados() {
		return enviaDados;
	}

	public InputStream getRecebeDados() {
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
