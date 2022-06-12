package io.github.gleysongomes.oauth.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageDTO<T> {

	@NotNull(message = "Infomar número da página.")
	private Integer number;

	@NotNull(message = "Infomar número de itens por página.")
	private Integer size;

	private Long totalElements;

	private List<T> content;

	PageDTO() {
		this.number = 1;
		this.size = 10;
	}

	@JsonIgnore
	public Integer getPageStart() {
		return size * (number - 1);
	}

	@JsonIgnore
	public Integer getPageEnd() {
		return size * number;
	}

}
