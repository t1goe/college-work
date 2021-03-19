#lang racket
(define diffs (lambda (L) (cond
                            ((null? (cdr L)) '())
                            (#t (cons (- (car L) (car (cdr L))) (diffs (cdr L)) ))
                            )
                )
  );;takes the difference of the 1st and 2nd, then the 2nd and 3rd etc