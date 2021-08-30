package com.heo.jinstargramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;


//NotNull = null값 체크
//notEmpty = 빈값이거나 null체크
//notblank = 빈값이거나 null체크나 빈 공백(space)까지
@Data
public class CommentDto {
	@NotBlank
	private String content;
	@NotNull
	private Integer imageId;
}
