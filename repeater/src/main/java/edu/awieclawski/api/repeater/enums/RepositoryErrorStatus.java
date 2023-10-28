package edu.awieclawski.api.repeater.enums;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RepositoryErrorStatus {

	MOVED(301, "Requested repository moved permanently"),
	FORBIDDEN(403, "Requested repository access forbidden"),
	NOT_FOUND(404, "Requested repository can not be found"),
	NOT_KNOWN(0, "Unknown error at GitHub API");

	private int status;
	private String description;

	public static RepositoryErrorStatus getByStatus(int status) {
		for (RepositoryErrorStatus e : RepositoryErrorStatus.values()) {
			if (Objects.equals(e.getStatus(), status))
				return e;
		}
		return NOT_KNOWN;
	}

}
