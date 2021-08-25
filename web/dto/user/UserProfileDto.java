package com.heo.jinstargramstart.web.dto.user;

import com.heo.jinstargramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
	private int imageCount;
	private boolean pageOwnerState;
	private User user;
	private boolean subscribeState;
	private int subscribeCount;
	private int subscribeDo;
}
