package rva.dto;

import java.util.Date;

public class TimDTO {
	private Integer id;
	private String naziv;
	private String sediste;
	private Date osnovan;
	private Integer ligaId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getSediste() {
		return sediste;
	}

	public void setSediste(String sediste) {
		this.sediste = sediste;
	}

	public Date getOsnovan() {
		return osnovan;
	}

	public void setOsnovan(Date osnovan) {
		this.osnovan = osnovan;
	}

	public Integer getLigaId() {
		return ligaId;
	}

	public void setLigaID(Integer ligaId) {
		this.ligaId = ligaId;
	}
}