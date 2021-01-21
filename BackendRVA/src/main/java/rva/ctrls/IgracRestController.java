package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Igrac;
import rva.reps.IgracRepository;
import rva.reps.NacionalnostRepository;
import rva.reps.TimRepository;
import rva.dto.IgracDTO;

@Api(tags = { "Igrač CRUD operacije" })
@RestController
public class IgracRestController {

	@Autowired
	private IgracRepository igracRepository;

	@Autowired
	private TimRepository timRepository;

	@Autowired
	private NacionalnostRepository nacionalnostRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@ApiOperation(value = "Vraća kolekciju svih igrača iz baze podataka")
	@GetMapping("igrac")
	public Collection<Igrac> getIgraci() {
		return igracRepository.findAll();
	}

	@ApiOperation(value = "Vraća igrača iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	@GetMapping("igrac/{id}")
	public Igrac getIgrac(@PathVariable("id") Integer id) {
		return igracRepository.getOne(id);
	}

	@ApiOperation(value = "Vraća kolekciju svih igrača iz baze podataka koji u imenu sadrže string prosleđen kao path varijabla")
	@GetMapping("igracIme/{ime}")
	public Collection<Igrac> getIgracByIme(@PathVariable("ime") String ime) {
		return igracRepository.findByImeContainingIgnoreCase(ime);
	}

	@ApiOperation(value = "Vraća kolekciju svih igrača iz baze podataka koji pripadaju timu sa id vrednosti koja je prosleđena kao path varijabla")
	@GetMapping(value = "igraciZaTimId/{id}")
	public Collection<Igrac> igracPoTimuId(@PathVariable("id") int id) {
		return igracRepository.findByTim(timRepository.getOne(id));
	}

	@ApiOperation(value = "Briše igrača iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	@DeleteMapping("igrac/{id}")
	public ResponseEntity<Igrac> deleteIgrac(@PathVariable("id") Integer id) {
		if (!igracRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		igracRepository.deleteById(id);
		if (id == -100)
			jdbcTemplate.execute(
					"INSERT INTO \"igrac\" (\"id\", \"ime\", \"prezime\", \"broj_reg\", \"datum_rodjenja\", \"nacionalnost\", \"tim\") VALUES (-100, 'Test Ime', 'Test Prezime', '1234', to_date('01.01.2001.', 'dd.mm.yyyy.'), 1, 1)");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Upisuje igrača u bazu podataka")
	@PostMapping("igrac")
	public ResponseEntity<Igrac> insertIgrac(@RequestBody IgracDTO igrac) {
		if (!igracRepository.existsById(igrac.getId())) {
			igracRepository.save(igracDTOtoEntity(igrac));
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@ApiOperation(value = "Modifikuje postojećeg igrača u bazi podataka")
	@PutMapping("igrac")
	public ResponseEntity<Igrac> updateIgrac(@RequestBody IgracDTO igrac) {
		if (!igracRepository.existsById(igrac.getId()))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		igracRepository.save(igracDTOtoEntity(igrac));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private Igrac igracDTOtoEntity(IgracDTO igrac) {
		Igrac persistentIgrac = new Igrac();
		persistentIgrac.setBrojReg(igrac.getBrojReg());
		persistentIgrac.setDatumRodjenja(igrac.getDatumRodjenja());
		persistentIgrac.setId(igrac.getId());
		persistentIgrac.setIme(igrac.getIme());
		persistentIgrac.setNacionalnost(nacionalnostRepository.getOne(igrac.getNacionalnostId()));
		persistentIgrac.setPrezime(igrac.getPrezime());
		persistentIgrac.setTim(timRepository.getOne(igrac.getTimId()));
		return persistentIgrac;
	}
}