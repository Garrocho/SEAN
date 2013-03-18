package com.sean.util;

public class Constantes {
	
	// Host (Ip do Servidor), PORTA (Porta do Servidor)
	public static String HOST = "192.168.10.102";
	public static int PORT = 7777;

	// Tipo de Mensagem de Status do Monitoramento. Nao alterar.
	public static String EXECUTANDO = "EXECUT";
	public static String PAUSADO = "PAUSAD";

	// Tipos de Requisicoes do Cliente. Nao alterar.
	public static String STATUS = "STATUS";
	public static String LOGAR = "LOGAR_";
	public static String INICIAR = "INICIA";
	public static String PAUSAR = "PAUSAR";
	public static String IMAGEM = "IMAGEM";
	public static String DESLIGAR = "DESLIG";
	public static String CONFIGURAR = "CONFIG";

	// Tipos de Respostas do Servidor. Nao alterar.
	public static String OK_200 = "OK_200";
	public static String NAO_AUTORIZADO_401 = "NO_401";
}
