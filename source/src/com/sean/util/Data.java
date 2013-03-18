package com.sean.util;

import java.sql.Date;
import java.util.Calendar;

public class Data {
	
	public static String getHoraAtual(String separador) {
		Calendar data = Calendar.getInstance();  
		int hora = data.get(Calendar.HOUR_OF_DAY);   
		int min = data.get(Calendar.MINUTE);  
		int seg = data.get(Calendar.SECOND); 
		return hora + separador + min + separador + seg;
	}
	
	public static Date getDataAtual(){
		Calendar calendar = Calendar.getInstance();
		return new Date(calendar.getTimeInMillis());
	}

	public static String DateToString(Date date, String separador){
		if (date == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		String stringDia = dia < 10 ? "0" + dia : String.valueOf(dia);
		
		int mes = calendar.get(Calendar.MONTH) + 1;
		String stringMes = mes < 10 ? "0" + mes : String.valueOf(mes);
		
		int ano = calendar.get(Calendar.YEAR);
		
		String dataEmString = stringDia + separador + stringMes + separador +  ano;
		return dataEmString;
	}
	
	public static String DateToString(Date date){
		return DateToString(date, "-");
	}
	
	public static String DateToStringSQL(Date date, String separador){
		if (date == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		String stringDia = dia < 10 ? "0" + dia : String.valueOf(dia);
		
		int mes = calendar.get(Calendar.MONTH) + 1;
		String stringMes = mes < 10 ? "0" + mes : String.valueOf(mes);
		
		int ano = calendar.get(Calendar.YEAR);
		
		String dataEmString = ano + separador + stringMes + separador +  stringDia;
		return dataEmString;
	}	
}
