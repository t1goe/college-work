#lang racket
(define chain (lambda L (cond
                          ((null? (cdr L))(car L))
                          (#t ((car L) (apply chain (cdr L))))
                          )
                )
  )