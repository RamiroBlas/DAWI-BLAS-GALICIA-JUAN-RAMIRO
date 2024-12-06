package pe.edu.cibertec.DAWI_BLAS_GALICIA_JUAN_RAMIRO.dto;

import java.util.Date;

public record FilmCreateDto(String title, String description, Integer releaseYear, Integer languageId, String languageName, Integer rentalDuration, Double rentalRate, Integer length, Double replacementCost, String rating, String specialFeatures, Date lastUpdate
) {
}
