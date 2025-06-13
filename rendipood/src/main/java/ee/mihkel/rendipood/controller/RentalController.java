package ee.mihkel.rendipood.controller;

import ee.mihkel.rendipood.dto.FilmDTO;
import ee.mihkel.rendipood.entity.Film;
import ee.mihkel.rendipood.entity.FilmType;
import ee.mihkel.rendipood.entity.Rental;
import ee.mihkel.rendipood.repository.FilmRepository;
import ee.mihkel.rendipood.repository.RentalRepository;
import ee.mihkel.rendipood.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RentalController {

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    RentalService rentalService;

    @GetMapping("rentals")
    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    @PostMapping("start-rental")
    public Rental startRental(@RequestBody List<FilmDTO> films, @RequestParam Long customerId, int bonusDays) {
        return rentalService.startRental(films, customerId, bonusDays);
    }

    @PostMapping("end-rental")
    public Rental endRental(@RequestBody List<FilmDTO> films, @RequestParam Long rentalId) {
        return rentalService.endRental(films, rentalId);
    }
}
