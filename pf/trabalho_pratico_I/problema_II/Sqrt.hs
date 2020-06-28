module Sqrt where

import Prelude(Ord, Floating, Bool(True, False), (*), (-), (/), (<=), (<))
import Bool(cond)


until :: (a -> Bool) -> (a -> a) -> a -> a
until p f x = cond (p x) (x) (until p f (f x))


--Estou considerando que sempre serão passado valores não negativos,
--já que na descrição do problema não falava nada a respeito.
sqrt :: (Floating a, Ord a) => a -> a
sqrt x = until goodEnough improve x where
    goodEnough y = mod((y*y) - x) <= (0.000001)
    improve y = next y x


--Recebe um valor x e c, onde x é um palpite e c é o número a ser calculado a raiz
--Retorna f(x) = x² - c
fx :: (Floating a) => a -> a -> a
fx x y = x*x - y 

--Recebe um valor x, onde x é um palpite
--Retorna f(x)' = 2x, onde f(x)' é a derivada de f(x)
fdx :: (Floating a) => a -> a
fdx x = 2*x

--Retorna o próximo palpite
next :: (Floating a) => a -> a -> a
next x y = x - (fx x y)/(fdx x)

mod :: (Floating a, Ord a) => a -> a
mod x = cond (x < 0) (-x) (x)