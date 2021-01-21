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
 * The persistent class for the nacionalnost database table.
 */
@Entity
@NamedQuery(name = "Nacionalnost.findAll", query = "SELECT n FROM Nacionalnost n")
public class Nacionalnost implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "NACIONALNOST_ID_GENERATOR", sequenceName = "NACIONALNOST_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NACIONALNOST_ID_GENERATOR")
	private Integer id;

	private String naziv;
	private String skracenica;

	// bi-directional many-to-one association to Igrac
	@OneToMany(mappedBy = "nacionalnost")
	@JsonIgnore
	private List<Igrac> igraci;

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

	public String getSkracenica() {
		return this.skracenica;
	}

	public void setSkracenica(String skracenica) {
		this.skracenica = skracenica;
	}

	public List<Igrac> getIgraci() {
		return this.igraci;
	}

	public void setIgraci(List<Igrac> igraci) {
		this.igraci = igraci;
	}

	public Igrac addIgrac(Igrac igrac) {
		getIgraci().add(igrac);
		igrac.setNacionalnost(this);
		return igrac;
	}

	public Igrac removeIgrac(Igrac igrac) {
		getIgraci().remove(igrac);
		igrac.setNacionalnost(null);
		return igrac;
	}
}