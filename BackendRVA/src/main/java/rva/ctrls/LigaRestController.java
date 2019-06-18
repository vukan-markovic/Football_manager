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
import rva.jpa.Liga;
import rva.reps.LigaRepository;

@Api(tags = { "Liga CRUD operacije" })
@RestController
public class LigaRestController {

	@Autowired
	private LigaRepository ligaRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@ApiOperation(value = "Vraća kolekciju svih liga iz baze podataka")
	@GetMapping("liga")
	public Collection<Liga> getLige() {
		return ligaRepository.findAll();
	}

	@ApiOperation(value = "Vraća ligu iz baze podataka čija je id vrednost prosleđena kao path varijabla")
	@GetMapping("liga/{id}")
	public Liga getLiga(@PathVariable("id") Integer id) {
		return ligaRepository.getOne(id);
	}

	@ApiOperation(value = "Vraća kolekciju svih liga iz baze podataka koje u nazivu sadrže string prosleđen kao path varijabla")
	@GetMapping("ligaNaziv/{naziv}")
	public Collection<Liga> getLigaByNaziv(@PathVariable("naziv") String naziv) {
		return ligaRepository.findByNazivContainingIgnoreCase(naziv);
	}

	@ApiOperation(value = "Briše ligu iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	@Transactional
	@CrossOrigin
	@DeleteMapping("liga/{id}")
	public ResponseEntity<Liga> deleteLiga(@PathVariable("id") Integer id) {
		if (!ligaRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		ligaRepository.deleteById(id);
		jdbcTemplate.execute("delete from igrac where tim in (select id from tim where liga=" + id + ")");
		jdbcTemplate.execute("delete from tim where liga = " + id);
		if (id == -100)
			jdbcTemplate.execute(
					"INSERT INTO \"liga\" (\"id\", \"naziv\", \"oznaka\") VALUES (-100, 'Test Naziv', 'Test Oznaka')");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Upisuje ligu u bazu podataka")
	@CrossOrigin
	@PostMapping("liga")
	public ResponseEntity<Liga> insertLiga(@RequestBody Liga Liga) {
		if (!ligaRepository.existsById(Liga.getId())) {
			ligaRepository.save(Liga);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@ApiOperation(value = "Modifikuje postojeću ligu u bazi podataka")
	@CrossOrigin
	@PutMapping("liga")
	public ResponseEntity<Liga> updateLiga(@RequestBody Liga Liga) {
		if (!ligaRepository.existsById(Liga.getId()))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		ligaRepository.save(Liga);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}