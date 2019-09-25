#lang racket
(define cumulative(lambda (L) (cond
                      ((null? L) '())
                      (#t(helper 0 L))
                      )
          )
  )

(define helper(lambda (C L)(cond
                             ((null? L)'())
                             (#t(cons (+ C (car L))(helper (+ C (car L)) (cdr L))))
                             )
                )
  )