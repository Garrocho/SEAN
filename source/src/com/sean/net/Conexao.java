package com.sean.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import static com.sean.util.Constantes.HOST;
import static com.sean.util.Constantes.PORT;

import org.itadaki.bzip2.BZip2OutputStream;

import android.util.Log;

public class Conexao {

	private OutputStream enviaDados;
	private InputStream recebeDados;
	
	private Socket soquete;
	private int PORTA;
	private String ENDERECO;

	public Conexao() {
		super();
		this.ENDERECO = HOST;
		this.PORTA = PORT;
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
			Log.d("erro", e.getMessage());
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
	
	public static byte[] bz2Compress(String mensagem) {
		try {
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			BZip2OutputStream output = new BZip2OutputStream (byteOutput);
			output.write(mensagem.getBytes());
			output.close();
			return byteOutput.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
