package ee.mihkel.rendipood.controller;

import ee.mihkel.rendipood.entity.Film;
import ee.mihkel.rendipood.entity.FilmType;
import ee.mihkel.rendipood.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilmController {

    @Autowired
    FilmRepository filmRepository;

    @PostMapping("films")
    public List<Film> addFilm(@RequestBody Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new RuntimeException("Nimi ei tohi olla puudu");
        }
        if (film.getFilmType() == null) {
            throw new RuntimeException("Tüüp ei tohi olla puudu");
        }
        film.setDays(0);
        filmRepository.save(film);
        return filmRepository.findAll();
    }

    @DeleteMapping("films/{id}")
    public List<Film> deleteFilm(@PathVariable Long id) {
        filmRepository.deleteById(id);
        return filmRepository.findAll();
    }

    @PatchMapping("films")
    public List<Film> changeFilmType(@RequestParam Long id, FilmType newType) {
        Film film = filmRepository.findById(id).orElseThrow();
        film.setFilmType(newType);
        filmRepository.save(film);
        return filmRepository.findAll();
    }

    @GetMapping("films")
    public List<Film> allFilms() {
        return filmRepository.findAll();
    }

    @GetMapping("available-films")
    public List<Film> availableFilms() {
        return filmRepository.findByDays(0);
    }
}
