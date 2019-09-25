#lang racket
(define remove(lambda (N L) (cond
                                ((null? L) '())
                                ((= N 0) L)
                                (#t (remove (- N 1)(cdr L)))
                                )
                  )
    )