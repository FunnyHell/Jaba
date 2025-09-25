
package com.example.messenger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author FunnyHell
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
    @NotBlank
    public String accessToken;
    @NotBlank
    public String rerfreshToken;
}
