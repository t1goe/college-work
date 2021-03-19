#lang racket
(define countBrackets(lambda (L) (cond
                                   ((null? L) 0)
                                   ((list? (car L))(+ (countBrackets (car L)) (countBrackets (cdr L)) 2));;First element sublist
                                   (#t(countBrackets (cdr L)));;First element atom
                                   )
                       )
  );;Answer is always 2 less than answer because it doesn't account for the outside brackets