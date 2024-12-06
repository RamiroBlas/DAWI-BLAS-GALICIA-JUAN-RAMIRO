package pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.service;

import pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.dto.FilmCreateDto;
import pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.dto.FilmDetailDto;
import pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.dto.FilmDto;

import java.util.List;

public interface MaintenanceService {

    List<FilmDto> findAllFilms();

    FilmDetailDto findFilmById(int id);

    Boolean updateFilm(FilmDetailDto filmDetailDto);

    Boolean addFilm(FilmCreateDto filmCreateDto);

    Boolean deleteFilmById(int id);
}
