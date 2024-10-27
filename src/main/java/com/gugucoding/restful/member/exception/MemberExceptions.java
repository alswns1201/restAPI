package com.gugucoding.restful.member.exception;

public enum MemberExceptions {

    NOT_FOUND("NOT_FOUND",400),
    DUPLICATE("DUPLICATE",409),
    INVALID("INVALID",400),
    BAD_CREDENTIALS("BAD_CREDENTIALS",401); // 잘못된 인증 정보 .

    private MemberTaskException memberTaskException;

    MemberExceptions(String msg, int code){
        memberTaskException = new MemberTaskException(msg,code);
    }

    public MemberTaskException get(){
        return memberTaskException;
    }

}
