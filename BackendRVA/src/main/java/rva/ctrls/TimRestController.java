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
import rva.jpa.Tim;
import rva.reps.TimRepository;

@Api(tags = { "Tim CRUD operacije" })
@RestController
public class TimRestController {

	@Autowired
	private TimRepository timRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@ApiOperation(value = "Vraća kolekciju svih timova iz baze podataka")
	@GetMapping("tim")
	public Collection<Tim> getTimovi() {
		return timRepository.findAll();
	}

	@ApiOperation(value = "Vraća tim iz baze podataka čija je id vrednost prosleđena kao path varijabla")
	@GetMapping("tim/{id}")
	public Tim getTim(@PathVariable("id") Integer id) {
		return timRepository.getOne(id);
	}

	@ApiOperation(value = "Vraća kolekciju svih timova iz baze podataka koji u nazivu sadrže string prosleđen kao path varijabla")
	@GetMapping("timNaziv/{naziv}")
	public Collection<Tim> getTimByNaziv(@PathVariable("naziv") String naziv) {
		return timRepository.findByNazivContainingIgnoreCase(naziv);
	}

	@ApiOperation(value = "Briše tim iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	@Transactional
	@CrossOrigin
	@DeleteMapping("tim/{id}")
	public ResponseEntity<Tim> deleteTim(@PathVariable("id") Integer id) {
		if (!timRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		timRepository.deleteById(id);
		jdbcTemplate.execute("delete from igrac where tim = " + id);
		if (id == -100)
			jdbcTemplate.execute(
					"INSERT INTO \"tim\" (\"id\", \"naziv\", \"osnovan\", \"sediste\", \"liga\") VALUES (-100, 'Test Naziv', to_date('01.01.1991.', 'dd.mm.yyyy.'), 'Test Sediste', 1)");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Upisuje tim u bazu podataka")
	@CrossOrigin
	@PostMapping("tim")
	public ResponseEntity<Tim> insertTim(@RequestBody Tim tim) {
		if (!timRepository.existsById(tim.getId())) {
			timRepository.save(tim);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@ApiOperation(value = "Modifikuje postojeći tim u bazi podataka")
	@CrossOrigin
	@PutMapping("tim")
	public ResponseEntity<Tim> updateTim(@RequestBody Tim tim) {
		if (!timRepository.existsById(tim.getId()))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		timRepository.save(tim);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}