package com.blockchain.blockchain_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class RequestService {

    @Min(value = 1000000000000000L, message = "NIK harus 16 digit")
    @Max(value = 9999999999999999L, message = "NIK harus 16 digit")
    private long nik;

    @NotBlank(message = "Data fullName invalid")
    private String fullName;

    @NotBlank(message = "Data birthPlace invalid")
    private String birthPlace;

    @NotBlank(message = "Data birthDate invalid")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Format tanggal harus yyyy-MM-dd"
    )
    private String birthDate;

    @NotNull(message = "Gender invalid")
    private Gender gender;

    @NotNull(message = "Data religion invalid")
    private Religion religion;

    @NotNull(message = "Marital Status invalid")
    private Status maritalStatus;

    @NotNull(message = "Blood Type invalid")
    private BloodsType bloodType;

    @NotBlank(message = "Data nationality invalid")
    private String nationality;

    @NotBlank(message = "Data fullAddress invalid")
    private String fullAddress;

    @NotBlank(message = "Data validUntil invalid")
    private String validUntil;

    @NotBlank(message = "Data occupation invalid")
    private String occupation;

    private List<String> cid;

}