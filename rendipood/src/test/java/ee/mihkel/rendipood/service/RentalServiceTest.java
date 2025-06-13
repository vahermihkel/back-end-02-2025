package ee.mihkel.rendipood.service;

import ee.mihkel.rendipood.dto.FilmDTO;
import ee.mihkel.rendipood.entity.Customer;
import ee.mihkel.rendipood.entity.Film;
import ee.mihkel.rendipood.entity.FilmType;
import ee.mihkel.rendipood.entity.Rental;
import ee.mihkel.rendipood.repository.CustomerRepository;
import ee.mihkel.rendipood.repository.FilmRepository;
import ee.mihkel.rendipood.repository.RentalRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class RentalServiceTest {

    @Mock
    FilmRepository filmRepository;

    @Mock
    RentalRepository rentalRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    RentalService rentalService;

    List<FilmDTO> films = new ArrayList<>();
    FilmDTO filmDTO = new FilmDTO();
    Film film = new Film();
    Customer customer = new Customer();
    Rental dbRental = new Rental();
    Film rentalFilm = new Film();

    @BeforeEach
    void setUp() {
        filmDTO.setId(1L);
        films.add(filmDTO);

        when(filmRepository.findById(1L)).thenReturn(Optional.of(film));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        when(rentalRepository.save(any())).thenReturn(dbRental);
        when(rentalRepository.findById(any())).thenReturn(Optional.of(dbRental));

        rentalFilm.setId(2L);
        rentalFilm.setDays(5);
        List<Film> rentalFilms = new ArrayList<>();
        rentalFilms.add(rentalFilm);
        when(filmRepository.findById(2L)).thenReturn(Optional.of(rentalFilm));

        dbRental.setId(9L);
        dbRental.setFilms(rentalFilms);
    }

    @Test
    void givenFilmIsNewAndRentedFor5Days_whenRentalIsStarted_thenFilmCosts20() {
        filmDTO.setDays(5);
        film.setFilmType(FilmType.NEW);

        Rental rental = rentalService.startRental(films, 1L, 0);

        assertEquals(20, rental.getInitialFee());
    }

    @Test
    void givenFilmIsOldAndRentedFor10Days_whenRentalIsStarted_thenFilmCosts20() {
        filmDTO.setDays(10);
        film.setFilmType(FilmType.OLD);

        Rental rental = rentalService.startRental(films, 1L, 0);

        assertEquals(18, rental.getInitialFee());
    }

    @Test
    void givenFilmIsOldAndRentedFor4Days_whenFeeIsCalculated_thenFilmCosts3() {
        filmDTO.setDays(4);
        film.setFilmType(FilmType.OLD);

        Rental rental = rentalService.startRental(films, 1L, 0);

        assertEquals(3, rental.getInitialFee());
    }

    @Test
    void givenFilmIsRegularAndRentedFor5Days_whenRentalIsStarted_thenFilmCosts9() {
        filmDTO.setDays(5);
        film.setFilmType(FilmType.REGULAR);

        Rental rental = rentalService.startRental(films, 1L, 0);

        assertEquals(9, rental.getInitialFee());
    }

    @Test
    void givenFilmIsOldAndRentedFor7Days_whenRentalIsStarted_thenFilmCosts9() {
        filmDTO.setDays(7);
        film.setFilmType(FilmType.OLD);

        Rental rental = rentalService.startRental(films, 1L, 0);

        assertEquals(9, rental.getInitialFee());
    }

    @Test
    void givenBonusPointsIs0AndFilmIsNew_whenRentalIsStarted_thenCustomerPointsAre2() {
//        filmDTO.setDays(4);
        film.setFilmType(FilmType.NEW);

        rentalService.startRental(films, 1L, 0);

        assertEquals(2, customer.getBonusPoints());
    }

    @Test
    void givenBonusPointsIs50AndFilmIsNew_whenRentalIsStarted_thenCustomerPointsAre2() {
        filmDTO.setDays(4);
        film.setFilmType(FilmType.NEW);
        customer.setBonusPoints(25 * 4);

        rentalService.startRental(films, 1L, 2);

        assertEquals(52, customer.getBonusPoints());
    }

    @Test
    void givenBonusDaysAre2AndFilmIsNewAndIsTakenFor7Days_whenRentalIsStarted_thenFilmCosts20() {
        filmDTO.setDays(7);
        film.setFilmType(FilmType.NEW);
        customer.setBonusPoints(25 * 2);

        Rental rental = rentalService.startRental(films, 1L, 2);

        assertEquals(20, rental.getInitialFee());
    }

    @Test
    void givenBonusDaysAre10AndFilmIsNewAndIsTakenFor7Days_whenRentalIsStarted_thenFilmCosts0() {
        filmDTO.setDays(7);
        film.setFilmType(FilmType.NEW);
        customer.setBonusPoints(25 * 10);

        Rental rental = rentalService.startRental(films, 1L, 10);

        assertEquals(0, rental.getInitialFee());
    }

    @Test
    void givenBonusDaysAre2AndFilmIsOldAndIsTakenFor7Days_whenRentalIsStarted_thenFilmCosts9() {
        filmDTO.setDays(7);
        film.setFilmType(FilmType.OLD);
        customer.setBonusPoints(25 * 2);

        Rental rental = rentalService.startRental(films, 1L, 2);

        assertEquals(9, rental.getInitialFee());
    }

    @Test
    void givenFilmIsAlreadyRented_whenRentalIsStarted_thenExceptionIsThrown() {
        film.setDays(1);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> rentalService.startRental(films, 1L, 2));

        assertEquals("ERROR_FILM_ALREADY_IN_RENTAL", exception.getMessage());
    }

    @Test
    void givenNotEnoughBonusPoints_whenRentalIsStarted_thenExceptionIsThrown() {
        int bonusDays = 2;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> rentalService.startRental(films, 1L, bonusDays));

        assertEquals("ERROR_NOT_ENOUGH_BONUS_POINTS", exception.getMessage());
    }

    @Test
    void givenAllFilmsAreNotInRental_whenRentalIsEnded_thenExceptionIsThrown() {
        rentalFilm.setId(2L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> rentalService.endRental(films, 9L));

        assertEquals("ERROR_ALL_FILMS_NOT_IN_PROVIDED_RENTAL", exception.getMessage());
    }

    @Test
    void givenNoLateFilms_whenRentalIsEnded_thenLateFeeIs0() {
        filmDTO.setId(2L);
        filmDTO.setDays(5);

        Rental rental = rentalService.endRental(films, 9L);

        assertEquals(0, rental.getLateFee());
    }

    @Test
    void givenNewFilmIsLateOneDay_whenRentalIsEnded_thenLateFeeIs4() {
        rentalFilm.setFilmType(FilmType.NEW);
        filmDTO.setId(2L);
        filmDTO.setDays(6);

        Rental rental = rentalService.endRental(films, 9L);

        assertEquals(4, rental.getLateFee());
    }

    @Test
    void givenRegularFilmIsLateThreeDays_whenRentalIsEnded_thenLateFeeIs9() {
        rentalFilm.setFilmType(FilmType.REGULAR);
        filmDTO.setId(2L);
        filmDTO.setDays(8);

        Rental rental = rentalService.endRental(films, 9L);

        assertEquals(9, rental.getLateFee());
    }
}