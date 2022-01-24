package com.parchenegar.capsule.dto.base;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse
{
    String name;
    String uri;
    String type;
    long size;
}
