package rva.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the liga database table.d
 */
@Entity
@NamedQuery(name = "Liga.findAll", query = "SELECT l FROM Liga l")
public class Liga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "LIGA_ID_GENERATOR", sequenceName = "LIGA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIGA_ID_GENERATOR")
	private Integer id;

	private String naziv;
	private String oznaka;

	// bi-directional many-to-one association to Tim
	@OneToMany(mappedBy = "liga")
	@JsonIgnore
	private List<Tim> timovi;

	public Liga() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOznaka() {
		return this.oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public List<Tim> getTimovi() {
		return this.timovi;
	}

	public void setTimovi(List<Tim> timovi) {
		this.timovi = timovi;
	}

	public Tim addTim(Tim tim) {
		getTimovi().add(tim);
		tim.setLiga(this);
		return tim;
	}

	public Tim removeTim(Tim tim) {
		getTimovi().remove(tim);
		tim.setLiga(null);
		return tim;
	}
}