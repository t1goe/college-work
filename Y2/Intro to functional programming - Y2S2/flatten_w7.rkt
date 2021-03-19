#lang racket
(define flatten(lambda(L)(cond
                            ((null? L) '())
                            ((list? (car L))(append (car L) (flatten(cdr L))))
                            (#t (cons (car L)(flatten (cdr L))))
                            )
                          )
  )