package com.gugucoding.restful.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberTaskException extends RuntimeException{

   private String msg;
    private int code;



}
