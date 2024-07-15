package com.sec.next.gen.userservice.service.internal.authorization.token;

import com.next.gen.sec.model.GoogleAuthorizedUser;

public record TokenParams(GoogleAuthorizedUser googleAuthorizedUser, Long msDuration) {
}
