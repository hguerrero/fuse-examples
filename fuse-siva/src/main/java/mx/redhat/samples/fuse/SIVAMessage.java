package mx.redhat.samples.fuse;

import java.nio.ByteBuffer;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;

@FixedLengthRecord
public class SIVAMessage 
{
	@DataField(pos=1,length=1)
	private char longitud1;
	
	@DataField(pos=2,length=1)
	private char longitud2;
	
	@DataField(pos=3,length=1)
	private char banderas1;
	
	@DataField(pos=4,length=1)
	private char banderas2;
	
	@DataField(pos=5,length=11)
	private String secuencia;
	
	@DataField(pos=6,length=8)
	private String hora;
	
	@DataField(pos=7,length=2)
	private String tipo;

	@DataField(pos=8,length=6)
	private String contenido;
	
	@DataField(pos=9,length=3)
	private int longitud;
	
	@DataField(pos=10,length=1)
	private char disponible;
	
	@DataField(pos=11,delimiter="|")
	private String data;
	
	private short checksum;

	public char getLongitud1() {
		return longitud1;
	}

	public void setLongitud1(char longitud1) {
		this.longitud1 = longitud1;
	}

	public char getLongitud2() {
		return longitud2;
	}

	public void setLongitud2(char longitud2) {
		this.longitud2 = longitud2;
	}

	public char getBanderas1() {
		return banderas1;
	}

	public void setBanderas1(char banderas1) {
		this.banderas1 = banderas1;
	}

	public char getBanderas2() {
		return banderas2;
	}

	public void setBanderas2(char banderas2) {
		this.banderas2 = banderas2;
	}

	public String getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	public char getDisponible() {
		return disponible;
	}

	public void setDisponible(char disponible) {
		this.disponible = disponible;
	}

	public String getData() {
		return data.substring(0, longitud);
	}

	public void setData(String data) {
		this.data = data;
		try {
			this.checksum = ByteBuffer.wrap(data.getBytes()).getShort(longitud);
		} catch (Exception e) {
			//could not parse checksum
			e.printStackTrace();
		}
	}
	
	public short getChecksum() {
		return checksum;
	}

	public void setChecksum(short checksum) {
		this.checksum = checksum;
	}

	public String toString(){
		return getSecuencia() + getHora() + getTipo() +  getContenido() + 
				String.format("%03d", getLongitud()) + getDisponible() + getData();
	}

}
