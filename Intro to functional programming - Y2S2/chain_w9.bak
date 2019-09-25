#lang racket
(define power (lambda L (cond
                          ((null? L) 1)
                          (#t (expt (car L) (apply power (cdr L))))
                          )
                )
  )