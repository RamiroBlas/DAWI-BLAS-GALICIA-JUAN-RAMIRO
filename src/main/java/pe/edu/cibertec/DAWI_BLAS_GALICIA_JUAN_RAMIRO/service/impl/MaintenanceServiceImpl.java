package pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.dto.FilmCreateDto;
import pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.dto.FilmDetailDto;
import pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.dto.FilmDto;
import pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.entity.Film;
import pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.entity.Language;
import pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.repository.FilmRepository;
import pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.repository.LanguageRepository;
import pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.service.MaintenanceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;


    @Override
    @Cacheable(value = "films")
    public List<FilmDto> findAllFilms() {

        List<FilmDto> films = new ArrayList<FilmDto>();
        Iterable<Film> iterable = filmRepository.findAll();
        iterable.forEach(film -> {
            FilmDto filmDto = new FilmDto(film.getFilmId(),
                    film.getTitle(),
                    film.getLanguage().getName(),
                    film.getRentalDuration(),
                    film.getRentalRate());
            films.add(filmDto);
        });
        return films;

    }



    @Override
    public FilmDetailDto findFilmById(int id) {

        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(film -> new FilmDetailDto(film.getFilmId(),
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getLanguage().getLanguageId(),
                film.getLanguage().getName(),
                film.getRentalDuration(),
                film.getRentalRate(),
                film.getLength(),
                film.getReplacementCost(),
                film.getRating(),
                film.getSpecialFeatures(),
                film.getLastUpdate())
        ).orElse(null);

    }




    @Override
    @CacheEvict(value = "films", allEntries = true)
    public Boolean updateFilm(FilmDetailDto filmDetailDto) {
        Optional<Film> optional = filmRepository.findById(filmDetailDto.filmId());
        return optional.map(
                film -> {
                    film.setTitle(filmDetailDto.title());
                    film.setDescription(filmDetailDto.description());
                    film.setReleaseYear(filmDetailDto.releaseYear());
                    film.setRentalDuration(filmDetailDto.rentalDuration());
                    film.setRentalRate(filmDetailDto.rentalRate());
                    film.setLength(filmDetailDto.length());
                    film.setReplacementCost(filmDetailDto.replacementCost());
                    film.setRating(filmDetailDto.rating());
                    film.setSpecialFeatures(filmDetailDto.specialFeatures());
                    film.setLastUpdate(new Date());
                    filmRepository.save(film);
                    return true;
                }
        ).orElse(false);
    }




    @Override
    @CacheEvict(value = "films", allEntries = true)
    public Boolean addFilm(FilmCreateDto filmCreateDto) {

        Film film = new Film();

        film.setTitle(filmCreateDto.title());
        film.setDescription(filmCreateDto.description());
        film.setReleaseYear(filmCreateDto.releaseYear());
        film.setRentalDuration(filmCreateDto.rentalDuration());
        film.setRentalRate(filmCreateDto.rentalRate());
        film.setLength(filmCreateDto.length());
        film.setReplacementCost(filmCreateDto.replacementCost());
        film.setRating(filmCreateDto.rating());
        film.setSpecialFeatures(filmCreateDto.specialFeatures());
        film.setLastUpdate(new Date());

        Language language = languageRepository.findById(filmCreateDto.languageId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Language ID"));

        film.setLanguage(language);

        filmRepository.save(film);

        return true;
    }


    @Override
    @CacheEvict(value = "films", allEntries = true)
    @Transactional
    public Boolean deleteFilmById(int id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
            return true;
        }
        return false;
    }


}


