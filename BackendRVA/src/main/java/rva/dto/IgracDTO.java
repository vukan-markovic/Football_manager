package rva.dto;

import java.util.Date;

public class IgracDTO {
	private Integer id;
	private String ime;
	private String prezime;
	private String brojReg;
	private Date datumRodjenja;
	private Integer nacionalnostId;
	private Integer timId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getBrojReg() {
		return brojReg;
	}

	public void setBrojReg(String brojReg) {
		this.brojReg = brojReg;
	}

	public Date getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public Integer getNacionalnostId() {
		return nacionalnostId;
	}

	public void setNacionalnostId(Integer nacionalnostId) {
		this.nacionalnostId = nacionalnostId;
	}

	public Integer getTimId() {
		return timId;
	}

	public void setTimId(Integer timId) {
		this.timId = timId;
	}
}