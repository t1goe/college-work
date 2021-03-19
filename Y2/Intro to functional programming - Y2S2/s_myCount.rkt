#lang racket
(define myCount(lambda (A L Fn)(cond
                                 ((null? L) 0)
                                 ((Fn A (car L))(+ 1 (myCount A (cdr L) Fn)));;First element is valid for Fn
                                 (#t(myCount A (cdr L) Fn));;First element is not true and an atom
                                 )
                 )
  )