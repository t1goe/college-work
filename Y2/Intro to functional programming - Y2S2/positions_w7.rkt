#lang racket
(define positions(lambda (N A L)(cond
                                  ((null? L) '())
                                  ((equal? (car L) A)(cons N (positions (+ 1 N) A (cdr L))))
                                  (#t (positions (+ 1 N) A (cdr L)))
                                  )
                   )
  )