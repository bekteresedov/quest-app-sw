package com.questApp.data.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCreate {


    Long id;
    String text;
    String title;
    Long userId;
}
