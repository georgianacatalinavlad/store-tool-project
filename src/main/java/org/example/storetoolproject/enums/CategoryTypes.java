package org.example.storetoolproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CategoryTypes {
    FOOD("Food"),
    MAKEUP("Make-Up");

    private String value;

}
