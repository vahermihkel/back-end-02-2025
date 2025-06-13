package ee.mihkel.rendipood.service;

import ee.mihkel.rendipood.dto.FilmDTO;
import ee.mihkel.rendipood.entity.Customer;
import ee.mihkel.rendipood.entity.Film;
import ee.mihkel.rendipood.entity.FilmType;
import ee.mihkel.rendipood.entity.Rental;
import ee.mihkel.rendipood.repository.CustomerRepository;
import ee.mihkel.rendipood.repository.FilmRepository;
import ee.mihkel.rendipood.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalService {
    @Autowired
    FilmRepository filmRepository;

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    CustomerRepository customerRepository;

    private final double PREMIUM_PRICE = 4;
    private final double BASIC_PRICE = 3;

    public Rental startRental(List<FilmDTO> films, Long customerId, int bonusDays) {
        for (FilmDTO f: films) {
            Film film = filmRepository.findById(f.getId()).orElseThrow();
            if (film.getDays() > 0) {
                throw new RuntimeException("ERROR_FILM_ALREADY_IN_RENTAL");
            }
        }

        Customer customer = customerRepository.findById(customerId).orElseThrow();
        if (customer.getBonusPoints() / 25 < bonusDays) {
            throw new RuntimeException("ERROR_NOT_ENOUGH_BONUS_POINTS");
        }

        double sum = 0;
        List<Film> rentalFilms = new ArrayList<>();
        Rental rental = new Rental();
        for (FilmDTO f: films) {
            Film film = filmRepository.findById(f.getId()).orElseThrow();
            sum += calculateFilmFee(film.getFilmType(), f.getDays(), customer, bonusDays, rental);
            film.setDays(f.getDays());
            filmRepository.save(film);
            rentalFilms.add(film);
        }
        rental.setInitialFee(sum);
        rental.setFilms(rentalFilms);
        rental.setCustomer(customer);
        Rental rentalWithId = rentalRepository.save(rental);
        rental.setId(rentalWithId.getId());
        return rental;
    }

    // LIHTNE: 4 päeva boonust -> NEW 4 päeva
    // JUHTUM nr 1: 4 päeva boonus -> NEW 3 päeva
    // JUHTUM nr 2: 4 päeva boonus -> NEW 3 päeva + NEW 3 päeva

    private double calculateFilmFee(FilmType filmType, int days, Customer customer, int bonusDays, Rental rental) {
        switch (filmType) {
            case NEW -> {
                addBonusPointsToCustomer(customer, 2);
                bonusDays = bonusDays - rental.getBonusDaysUsed();
                if (bonusDays > days) {
                    bonusDays = days;
                }
                rental.setBonusDaysUsed(bonusDays);
                customer.setBonusPoints(customer.getBonusPoints() - bonusDays * 25);
                return (days - bonusDays) * PREMIUM_PRICE;
            }
            case REGULAR -> {
                addBonusPointsToCustomer(customer, 1);
                if (days <= 3) {
                    return BASIC_PRICE;
                }
                return BASIC_PRICE + (days-3) * BASIC_PRICE;
            }
            case OLD -> {
                addBonusPointsToCustomer(customer, 1);
                if (days <= 5) {
                    return BASIC_PRICE;
                }
                return BASIC_PRICE + (days-5) * BASIC_PRICE;
            }
            case null, default -> {
                return 0;
            }
        }
    }

    private void addBonusPointsToCustomer(Customer customer, int points) {
        int bonusPoints = customer.getBonusPoints() + points;
        customer.setBonusPoints(bonusPoints);
        customerRepository.save(customer);
    }

    public Rental endRental(List<FilmDTO> films, Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow();
        List<Film> rentalFilms = rental.getFilms();
        //rentalFilms.stream().map(e -> e.getId())
        List<Long> rentalFilmsIds = rentalFilms.stream().map(Film::getId).toList();
        List<Long> dtoFilmsIds = films.stream().map(FilmDTO::getId).toList();
        if (!rentalFilmsIds.containsAll(dtoFilmsIds)) {
            throw new RuntimeException("ERROR_ALL_FILMS_NOT_IN_PROVIDED_RENTAL");
        }

        double sum = rental.getLateFee();
        for (FilmDTO filmDTO: films) {

            Film dbFilm = filmRepository.findById(filmDTO.getId()).orElseThrow();
            //f.getDays(); // tegelikult mitu päeva oli rendis
            //film.getDays(); // mis lubati, et ta on rendis
            int lateDays = filmDTO.getDays() - dbFilm.getDays();
            if (lateDays > 0) {
                sum += calculateLateFee(dbFilm.getFilmType(), lateDays);
            }
            dbFilm.setDays(0); // available
            filmRepository.save(dbFilm);

        }
        rental.setLateFee(sum);
        return rentalRepository.save(rental);
    }

    private double calculateLateFee(FilmType filmType, int lateDays) {
        switch (filmType) {
            case NEW -> {
                return lateDays * PREMIUM_PRICE;
            }
            case REGULAR, OLD -> {
                return lateDays * BASIC_PRICE;
            }
            case null, default -> {
                return 0;
            }
        }
    }
}
