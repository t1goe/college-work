#lang racket
(define labelNum
  (lambda (N L)
    (cond ((null? L) '())
          ((not (list? (car L)))(cons (car L) (labelNum N (cdr L))))
          (#t (cons (cons (+ N 1)(labelNum(+ N 1)(car L)))(labelNum N (cdr L))))
          )
    )
  )

(define label
  (lambda (L)
    (cons 0 (labelNum 0 L))
    )
  )