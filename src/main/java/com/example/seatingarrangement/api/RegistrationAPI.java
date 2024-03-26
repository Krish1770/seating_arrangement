package com.example.seatingarrangement.api;

import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.entity.CompanyDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RequestMapping(value = "${SeatingArrangement}")
public interface RegistrationAPI {

    @PostMapping(value = "${sign-up}")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO);

    @PostMapping(value = "${sign-in}")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO);

    @PostMapping(value = "${companyLogout}")
    public ResponseEntity<ResponseDto> logout(@Valid @RequestParam String accessToken);

}


