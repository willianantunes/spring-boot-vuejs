package br.com.willianantunes.controllers.excep;

import org.zalando.problem.AbstractThrowableProblem;

import static org.zalando.problem.Status.BAD_REQUEST;

public class BadRequestException extends AbstractThrowableProblem {

    public BadRequestException() {

        super(null, null, BAD_REQUEST);
    }
}