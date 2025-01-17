package com.example.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class User {

    @Size(min = 5, max = 45, message = "Tên phải lớn hơn 5 kí tự và bé hơn 45 kí tự")
    private String firstname;
    @Size(min = 5, max = 45, message = "Tên phải lớn hơn 5 kí tự và bé hơn 45 kí tự")
    private String lastname;

    @Pattern(regexp = "(0)[0-9]{9,10}", message = "Số điện thoại phải bắt đầu là 0 và có 10 hoặc 11 kí tự số")
    private String phone;

    @NotNull(message = "Hãy nhập số vào")
    @Min(value = 18, message = "Bạn chưa đủ tuổi để đăng kí")
    @Max(value = 120, message = "Số tuổi của bạn thật thần kì")
    private Integer age;

    @Email(message = "Email không đúng định dạng ")
    private String email;

}