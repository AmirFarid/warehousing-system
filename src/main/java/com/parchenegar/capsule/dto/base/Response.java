package com.parchenegar.capsule.dto.base;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Response
{
    Object body;
    String description;
    String message;
    Boolean success;

    public ResponseEntity with(HttpStatus httpStatus)
    {
        return ResponseEntity.status(httpStatus).body(this);
    }

    public ResponseEntity with(int httpStatus)
    {
        return ResponseEntity.status(httpStatus).body(this);
    }


    public ResponseEntity ok()
    {
        this.success = true;
        return ResponseEntity.status(HttpStatus.OK).body(this);
    }

    public ResponseEntity notFound()
    {
        this.success = false;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this);
    }

    public ResponseEntity accepted()
    {
        this.success = true;
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this);
    }

    public ResponseEntity serverError()
    {
        this.success = false;
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(this);
    }

    public ResponseEntity badRequest()
    {
        this.success = false;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this);
    }

    public ResponseEntity created()
    {
        this.success = true;
        return ResponseEntity.status(HttpStatus.CREATED).body(this);
    }

    public ResponseEntity forbidden()
    {
        this.success = false;
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(this);
    }
}
