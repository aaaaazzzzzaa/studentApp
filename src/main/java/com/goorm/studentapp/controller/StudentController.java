package com.goorm.studentapp.controller;

import com.goorm.studentapp.entity.ApiResponse;
import com.goorm.studentapp.entity.ErrorCode;
import com.goorm.studentapp.entity.Student;
import com.goorm.studentapp.exception.CustomException;
import com.goorm.studentapp.exception.InputRestriction;
import com.goorm.studentapp.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/student")
    public ApiResponse<Object> add(
            @RequestParam("name") String name,
            @RequestParam("grade") int grade
    ) {
        if (grade >= 6) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "grade는 6 이상을 입력할 수 없습니다.", new InputRestriction(6));
        }

        return makeResponse(studentService.addStudent(name, grade));
    }

    @GetMapping("/students")
    public ApiResponse<Student> getAll() {
        return makeResponse(studentService.getAll());
    }

    @GetMapping("/students/{grade}")
    public ApiResponse<Student> getGradeStudent(
            @PathVariable("grade") int grade
    ) {
        return makeResponse(studentService.get(grade));
    }


    public <T> ApiResponse<T> makeResponse(List<T> results) {
        return new ApiResponse<>(results);
    }

    public <T> ApiResponse<T> makeResponse(T result) {
        return new ApiResponse<>(List.of(result));
    }

    @ExceptionHandler(CustomException.class)
    public ApiResponse<Student> customExceptionHandle(CustomException customException) {
        return new ApiResponse<>(customException.getErrorCode().getCode(), customException.getErrorCode().getMessage(), customException.getData());
    }
}
