package rva.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the tim database table.
 */
@Entity
@NamedQuery(name = "Tim.findAll", query = "SELECT t FROM Tim t")
public class Tim implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TIM_ID_GENERATOR", sequenceName = "TIM_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIM_ID_GENERATOR")
	private Integer id;

	private String naziv;
	private String sediste;

	@Temporal(TemporalType.DATE)
	private Date osnovan;

	// bi-directional one-to-many association to Igrac
	@OneToMany(mappedBy = "tim")
	@JsonIgnore
	private List<Igrac> igraci;

	// bi-directional many-to-one association to Liga
	@ManyToOne
	@JoinColumn(name = "liga")
	private Liga liga;

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

	public Date getOsnovan() {
		return this.osnovan;
	}

	public void setOsnovan(Date osnovan) {
		this.osnovan = osnovan;
	}

	public String getSediste() {
		return this.sediste;
	}

	public void setSediste(String sediste) {
		this.sediste = sediste;
	}

	public List<Igrac> getIgraci() {
		return this.igraci;
	}

	public void setIgraci(List<Igrac> igraci) {
		this.igraci = igraci;
	}

	public Igrac addIgrac(Igrac igrac) {
		getIgraci().add(igrac);
		igrac.setTim(this);
		return igrac;
	}

	public Igrac removeIgrac(Igrac igrac) {
		getIgraci().remove(igrac);
		igrac.setTim(null);
		return igrac;
	}

	public Liga getLiga() {
		return this.liga;
	}

	public void setLiga(Liga liga) {
		this.liga = liga;
	}
}