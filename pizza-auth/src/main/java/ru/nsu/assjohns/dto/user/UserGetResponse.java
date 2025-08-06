package ru.nsu.assjohns.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGetResponse {

    private long id;

    private String name;

    private String email;

    private List<String> roles;
}
