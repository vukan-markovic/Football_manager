package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Nacionalnost;
import rva.reps.NacionalnostRepository;

@Api(tags = { "Nacionalnost CRUD operacije" })
@RestController
public class NacionalnostRestController {

	@Autowired
	private NacionalnostRepository nacionalnostRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@ApiOperation(value = "Vraća kolekciju svih nacionalnosti iz baze podataka")
	@GetMapping("nacionalnost")
	public Collection<Nacionalnost> getNacionalnosti() {
		return nacionalnostRepository.findAll();
	}

	@ApiOperation(value = "Vraća nacionalnost iz baze podataka čija je id vrednost prosleđena kao path varijabla")
	@GetMapping("nacionalnost/{id}")
	public Nacionalnost getNacionalnost(@PathVariable("id") Integer id) {
		return nacionalnostRepository.getOne(id);
	}

	@ApiOperation(value = "Vraća kolekciju svih nacionalnosti iz baze podataka koje u nazivu sadrže string prosleđen kao path varijabla")
	@GetMapping("nacionalnostNaziv/{naziv}")
	public Collection<Nacionalnost> getNacionalnostByNaziv(@PathVariable("naziv") String naziv) {
		return nacionalnostRepository.findByNazivContainingIgnoreCase(naziv);
	}

	@ApiOperation(value = "Briše nacionalnost iz baze podataka čija je id vrednost prosleđena kao path varijabla")
	@Transactional
	@CrossOrigin
	@DeleteMapping("nacionalnost/{id}")
	public ResponseEntity<Nacionalnost> deleteNacionalnost(@PathVariable("id") Integer id) {
		if (!nacionalnostRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		nacionalnostRepository.deleteById(id);
		jdbcTemplate.execute("delete from igrac where nacionalnost = " + id);
		if (id == -100)
			jdbcTemplate.execute(
					"INSERT INTO \"nacionalnost\" (\"id\", \"naziv\", \"skracenica\") VALUES (-100, 'Test Naziv', 'Test Skracenica')");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Upisuje nacionalnost u bazu podataka")
	@CrossOrigin
	@PostMapping("nacionalnost")
	public ResponseEntity<Nacionalnost> insertNacionalnost(@RequestBody Nacionalnost Nacionalnost) {
		if (!nacionalnostRepository.existsById(Nacionalnost.getId())) {
			nacionalnostRepository.save(Nacionalnost);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@ApiOperation(value = "Modifikuje postojeću nacionalnost u bazi podataka")
	@CrossOrigin
	@PutMapping("nacionalnost")
	public ResponseEntity<Nacionalnost> updateNacionalnost(@RequestBody Nacionalnost Nacionalnost) {
		if (!nacionalnostRepository.existsById(Nacionalnost.getId()))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		nacionalnostRepository.save(Nacionalnost);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}