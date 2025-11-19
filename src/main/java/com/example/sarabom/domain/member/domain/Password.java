package com.example.sarabom.domain.member.domain;

import com.example.sarabom.global.exception.member.InvalidPassword;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

import static com.example.sarabom.global.common.ErrorCode.NULL_EMPTY_PASSWORD;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$";

    private static final Pattern PATTERN = Pattern.compile(PASSWORD_PATTERN);

    @Column(name = "password")
    private String value;

    private Password(String value) {
        validate(value);
        this.value = value;
    }

    public static Password of(String rawPassword) {
        return new Password(rawPassword);
    }

    public static Password ofEncoded(String encodedPassword) {
        Password password = new Password();
        password.value = encodedPassword;
        return password;
    }

    private void validate(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new InvalidPassword(NULL_EMPTY_PASSWORD);
        }

        if (!PATTERN.matcher(rawPassword).matches()) {
            throw new InvalidPassword();
        }
    }
}
