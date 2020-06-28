module Functions where

import Prelude (Int, (-))

comp :: (b -> c) -> (a -> b) -> a -> c
comp f g x = f (g x)

(.) :: (b -> c) -> (a -> b) -> a -> c
f . g = comp f g

uncurry :: (a -> b -> c) -> (a, b) -> c
uncurry f (x, y) = f x y

map :: (a -> b) -> [a] -> [b]
map _ [] = []
map f (x:xs) = f x : map f xs

zipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith _ [] _ = []
zipWith _ _ [] = []
zipWith f (x:xs) (y:ys) = f x y : zipWith f xs ys

($) :: (a -> b) -> a -> b
f $ x = f x

fix :: (a -> a) -> a
fix f = f (fix f)

range :: Int -> [Int]
range 0 = [0]
range x = x : range (x - 1)
