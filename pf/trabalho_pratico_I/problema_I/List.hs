module List where

import Prelude (
    Eq, Num, Int, Bool(True, False),
    (+), (-), (*), (<=), (==), error
    )

import Bool
import Functions
import Maybe

-- Aula 1

length :: [a] -> Int
length = foldl f 0 where
    f acc _ = acc + 1

null :: [a] -> Bool
null [] = True
null (_:_) = False

head :: [a] -> a
head [] = error "head: empty list"
head (x:_) = x

tail :: [a] -> [a]
tail [] = error "tail: empty list"
tail (_:xs) = xs

safeHead :: [a] -> Maybe a
safeHead [] = Nothing
safeHead (x:_) = Just x

safeTail :: [a] -> Maybe [a]
safeTail [] = Nothing
safeTail (_:xs) = Just xs

last :: [a] -> a
last [] = error "last: empty list"
last (x:[]) = x
last (_:xs) = last xs

init :: [a] -> [a]
init [] = error "init: empty list"
init (_:[]) = []
init (x:xs) = x : init xs

(++) :: [a] -> [a] -> [a]
[] ++ ys = ys
xs ++ [] = xs
(x:xs) ++ ys = x : xs ++ ys

concat :: [[a]] -> [a]
concat [] = []
concat (xs:xss) = xs ++ concat xss

reverse :: [a] -> [a]
reverse xs = f xs [] where
    f [] acc = acc
    f (x:xs) acc = f xs (x:acc)

-- Aula 2

take :: Int -> [a] -> [a]
take 0 _ = []
take _ [] = []
take n (x:xs) = x : take (n - 1) xs

drop :: Int -> [a] -> [a]
drop _ [] = []
drop n xs = cond (n <= 0) xs (drop (n - 1) (tail xs))

splitAt :: Int -> [a] -> ([a], [a])
splitAt 0 xs = ([], xs)
splitAt _ [] = ([], [])
splitAt n (x:xs) = (x:ps, qs) where
    (ps, qs) = splitAt (n - 1) xs

isPrefixOf :: Eq a => [a] -> [a] -> Bool
isPrefixOf [] _ = True
isPrefixOf (_:_) [] = False
isPrefixOf (x:xs) (y:ys) = cond (x == y) (isPrefixOf xs ys) False 

zip :: [a] -> [b] -> [(a, b)]
zip = zipWith (,)

-- Aula 3

-- takeWhile esta definido mais a frente

dropWhile :: (a -> Bool) -> [a] -> [a]
dropWhile _ [] = []
dropWhile p xs = cond (p (head xs)) (dropWhile p (tail xs)) xs

span :: (a -> Bool) -> [a] -> ([a], [a])
span _ [] = ([], [])
span p l@(x:xs) = cond (p x) (x:ps, qs) ([], l) where
    (ps, qs) = span p xs

break :: (a -> Bool) -> [a] -> ([a], [a])
break p xs = span (not . p) xs

-- filter esta definido mais a frente

foldl :: (b -> a -> b) -> b -> [a] -> b
foldl _ acc [] = acc
foldl f acc (x:xs) = foldl f (f acc x) xs

foldr :: (a -> b -> b) -> b -> [a] -> b
foldr _ acc [] = acc
foldr f acc (x:xs) = f x (foldr f acc xs)

sum :: Num a => [a] -> a
sum = foldl (+) 0

prod :: Num a => [a] -> a
prod = foldl (*) 1

filter :: (a -> Bool) -> [a] -> [a]
filter p xs = foldr f [] xs where
    f x ys = cond (p x) (x:ys) ys

takeWhile :: (a -> Bool) -> [a] -> [a]
takeWhile p xs = foldr f [] xs where
    f x ys = cond (p x) (x:ys) []

(!!) :: [a] -> Int -> a
[] !! _ = error "(!!): empty list"
(x:_) !! 1 = x
(_:xs) !! k = xs !! (k - 1)
